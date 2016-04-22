package com.example.timer;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Created by michaelmainguy on 2/22/16.
 */
public class HttpCallback  {

    public void call(TimerEvent te) {
        DateFormatter df = new DateFormatter();
        try {
            String scheduleTime = df.marshal(te.triggerTime);
            ZonedDateTime now = ZonedDateTime.now();
            String currentTime = df.marshal(now);
            long delay = Duration.between(ZonedDateTime.now(), te.triggerTime).toMillis()/1000;
            if (delay > 0) {
                System.out.println("Warning!! Triggered " + te.eventId + " for: " + scheduleTime + " running at: " + currentTime);
            } else {
                System.out.println("Triggered " + te.eventId + " for: " + scheduleTime + " running at: " + currentTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
