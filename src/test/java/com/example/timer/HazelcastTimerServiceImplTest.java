package com.example.timer;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import org.junit.Test;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by michaelmainguy on 2/22/16.
 */
public class HazelcastTimerServiceImplTest {
    @Mocked
    HttpCallback callback;

    @Test
    public void test5SecondTimeout() {
        HazelcastTimerServiceImpl timer = new HazelcastTimerServiceImpl();

        TimerEvent te = new TimerEvent();
        te.status = Status.UNKNOWN;
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ZonedDateTime startTime = ZonedDateTime.now();
        te.triggerTime = ZonedDateTime.now().plusSeconds(5);

        te.eventId = UUID.randomUUID();


        new NonStrictExpectations() {
            {
                callback.call(te);

            }
        };
        System.out.println(ZonedDateTime.now().toString());
        timer.addTimer(te);
        System.out.println(ZonedDateTime.now().toString());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Verifications() {{
            TimerEvent captured;
            callback.call(captured = withCapture());
            System.out.println(captured.toString());

            ZonedDateTime now = ZonedDateTime.now();
            long millis = Duration.between(ZonedDateTime.now(), captured.triggerTime).toMillis();

            assertEquals(0, millis / 1000);
            assertEquals(5, Duration.between(startTime, captured.triggerTime).toMillis() / 1000);
        }};
        timer.stop();
    }


}
