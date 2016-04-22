package com.example.timer;

import org.apache.commons.lang.ObjectUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Created by michaelmainguy on 2/20/16.
 */
@XmlRootElement
public class TimerEvent implements Comparable, Serializable{
    public UUID eventId;

    public ZonedDateTime triggerTime;
    public Status status;


    @Override
    public boolean equals(Object other) {
        if (other == null ||
                !(other instanceof TimerEvent)) {
            return false;
        } else {
            TimerEvent ote = (TimerEvent)other;
            return
                    ObjectUtils.equals(this.eventId, ote.eventId) &&
                            ObjectUtils.equals(this.triggerTime, ote.triggerTime) &&
                            ObjectUtils.equals(this.status, ote.status);
        }
    }

    @Override
    public int hashCode() {
        return
                ObjectUtils.hashCode(eventId) |
                        ObjectUtils.hashCode(status) |
                        ObjectUtils.hashCode(triggerTime);
    }

    @Override
    public int compareTo(Object other) {
        if (other==null || !(other instanceof TimerEvent)) {
            return 1;
        }
        TimerEvent ote = (TimerEvent)other;
        int compare = ObjectUtils.compare(this.triggerTime, ote.triggerTime);
        if (compare != 0) {
            return compare;
        }
        compare = ObjectUtils.compare(this.eventId, ote.eventId);
        if (compare != 0) {
            return compare;
        }

        compare = ObjectUtils.compare(this.status, ote.status);

        return compare;



    }


    @Override
    public String toString() {
        DateFormatter df = new DateFormatter();
        String triggerTimeFormatted = "";
        String currentTimeFormatted = "";
        try {
            triggerTimeFormatted = df.marshal(triggerTime);
            currentTimeFormatted = df.marshal(ZonedDateTime.now());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "TimerEvent: eventId=" + eventId.toString() + ", triggerTime=" + triggerTimeFormatted + ", currentTime=" + currentTimeFormatted;
    }


}
