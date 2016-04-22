package com.example.timer;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by michaelmainguy on 2/22/16.
 */
public class DateFormatterTest {
    DateFormatter df = new DateFormatter();
    @Test
    public void testMarshall() throws Exception {
        ZonedDateTime now = ZonedDateTime.of(2011,1,1,20,15,23,123, ZoneId.ofOffset("GMT", ZoneOffset.UTC));
        assertEquals("2011-01-01T20:15:23.000000123Z", df.marshal(now));

    }
    @Test
    public void testUnMarshall() throws Exception {

        ZonedDateTime now = ZonedDateTime.of(2011,1,1,20,15,23,123, ZoneId.ofOffset("GMT", ZoneOffset.UTC));
        assertEquals(now, df.unmarshal("2011-01-01T20:15:23.000000123Z"));

    }
}
