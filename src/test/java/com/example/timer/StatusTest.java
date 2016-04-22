package com.example.timer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michaelmainguy on 2/20/16.
 */
public class StatusTest {
    @Test
    public void testScheduledStatus() {
        assertEquals(Status.SCHEDULED, Status.fromString("SCHEDULED"));
    }

    @Test
    public void testCancelledStatus() {
        assertEquals(Status.CANCELLED, Status.fromString("CANCELLED"));
    }

    @Test
    public void testTriggeredStatus() {
        assertEquals(Status.TRIGGERED, Status.fromString("TRIGGERED"));
    }

    @Test
    public void testUnknownStatus() {
        assertEquals(Status.UNKNOWN, Status.fromString("s"));
    }




}
