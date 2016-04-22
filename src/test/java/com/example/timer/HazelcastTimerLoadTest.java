package com.example.timer;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by michaelmainguy on 2/25/16.
 */
public class HazelcastTimerLoadTest {
    @Test
    public void testLoad() {
        HazelcastTimerServiceImpl timer = new HazelcastTimerServiceImpl();
        List<TimerEvent> schedullist = new ArrayList();
        for (int j = 1; j<10; j++) {
            for (int i = 1; i < 100; i++) {
                ZonedDateTime expire = ZonedDateTime.now().plusSeconds(10);
                TimerEvent te = new TimerEvent();
                te.eventId = UUID.randomUUID();
                te.status = Status.SCHEDULED;
                te.triggerTime = expire;
                timer.addTimer(te);
                try {
                    Thread.sleep(500);
                } catch( InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            try {
                Thread.sleep(10000);
            } catch( InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        try {
            while (timer.size()>0) {
                System.out.println("Queue size: " + timer.size());
                Thread.sleep(1000);
            }

        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        timer.stop();


    }
}
