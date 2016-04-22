package com.example.timer;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import com.hazelcast.core.TransactionalQueue;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;

import java.time.ZonedDateTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by michaelmainguy on 2/22/16.
 */
public class HazelcastTimerServiceImpl implements TimerService {
    HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    TransactionOptions options = new TransactionOptions()
            .setTransactionType(TransactionOptions.TransactionType.ONE_PHASE);





    private Consumer eventConsumer = new Consumer();
    private Thread consumerThread = null;

    public HazelcastTimerServiceImpl() {
        while (!hz.getLifecycleService().isRunning()) {
            try {
                Thread.sleep(100);
            } catch(Exception e) {

            }
        }
        consumerThread = new Thread(eventConsumer);
        consumerThread.start();



    }
    public void stop() {
        eventConsumer.stop();

        while (consumerThread.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

        }
        hz.getLifecycleService().shutdown();
    }

    public int size() {
        int size = 0;

        TransactionContext context = hz.newTransactionContext(options);

        context.beginTransaction();
        TransactionalQueue<TimerEvent> queue = context.getQueue("timerqueue");
        size = queue.size();
        context.commitTransaction();
        return size;
    }

    @Override
    public TimerEvent addTimer(TimerEvent newEvent) {

        TransactionContext context = hz.newTransactionContext(options);

        context.beginTransaction();
        TransactionalQueue<TimerEvent> queue = context.getQueue("timerqueue");
        queue.offer(newEvent);
        context.commitTransaction();

        return newEvent;
    }


    class Consumer implements Runnable {
        private boolean running = true;
        Consumer() {
            System.out.println("Consumer: " + ZonedDateTime.now().toString());
        }
        public void stop() {
            this.running = false;
        }

        @Override
        public void run() {
            while (running && hz.getLifecycleService().isRunning()) {

                TransactionContext context = hz.newTransactionContext(options);
                try {


                    context.beginTransaction();
                    TransactionalQueue<TimerEvent> queue = context.getQueue("timerqueue");
                    TimerEvent te = queue.poll(1, TimeUnit.SECONDS);

                    if (te != null && te.triggerTime.isBefore(ZonedDateTime.now())) {

                        HttpCallback hc = new HttpCallback();
                        hc.call(te);
                        context.commitTransaction();
                    } else {
                        context.rollbackTransaction();
                    }


                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    context.rollbackTransaction();
                }

            }
        }
    }
}
