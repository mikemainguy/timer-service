package com.example.timer;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import java.time.format.DateTimeFormatter;


/**
 * Created by michaelmainguy on 2/20/16.
 */
public class DateFormatter extends XmlAdapter<String, ZonedDateTime> {
    private DateTimeFormatter format;

    public DateFormatter() {
        format = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));

    }

    @Override
    public String marshal(ZonedDateTime d) throws Exception {

        try {
            return format.format(d);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ZonedDateTime unmarshal(String d) throws Exception {

        if (d == null) {
            return null;
        }

        return ZonedDateTime.parse(d, format);

    }
}
