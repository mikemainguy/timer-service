package com.example.timer;

import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michaelmainguy on 2/20/16.
 */
public class TimerEventTest {
    @Test
    public void testMarshall() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();
        TimerEvent te = new TimerEvent();
        UUID uuid = UUID.randomUUID();


        te.eventId = uuid;
        te.status = Status.SCHEDULED;

        ZonedDateTime dt = ZonedDateTime.now();
        DateFormatter df = new DateFormatter();



        te.triggerTime = dt;
        Marshaller marshaller = JAXBContext.newInstance(te.getClass()).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(te, sw);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<timerEvent>\n" +
                "    <eventId>" + te.eventId.toString() + "</eventId>\n" +
                "    <triggerTime>" + df.marshal(dt) + "</triggerTime>\n" +
                "    <status>SCHEDULED</status>\n" +
                "</timerEvent>\n", sw.toString());
        System.out.println(sw.toString());

    }

    @Test
    public void testUnMarshall() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();


        Unmarshaller unmarshaller = JAXBContext.newInstance(TimerEvent.class).createUnmarshaller();

        String inputData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<timerEvent>\n" +
                "    <eventId>57565d69-9c94-4600-8b20-a3db23e98d17</eventId>\n" +
                "    <triggerTime>2016-02-20T17:28:29.478Z</triggerTime>\n" +
                "    <status>TRIGGERED</status>\n" +
                "</timerEvent>";
        ByteArrayInputStream bis = new ByteArrayInputStream(inputData.getBytes("UTF-8"));
        TimerEvent te = (TimerEvent)unmarshaller.unmarshal(bis);
        assertEquals(Status.TRIGGERED, te.status);
        assertEquals("57565d69-9c94-4600-8b20-a3db23e98d17", te.eventId.toString());
        DateFormatter df = new DateFormatter();

        assertEquals(df.unmarshal("2016-02-20T17:28:29.478Z"), te.triggerTime);

    }

    @Test
    public void testEquals() {
        TimerEvent firstTe = new TimerEvent();
        assertNotEquals(firstTe, null);
        assertEquals(1, firstTe.compareTo(null));

        assertNotEquals(firstTe, "abc");
        assertEquals(1, firstTe.compareTo("abc"));
        TimerEvent secondTe = new TimerEvent();

        ZonedDateTime dt = ZonedDateTime.now();

        firstTe.triggerTime = dt;
        secondTe.triggerTime = dt;

        assertEquals(firstTe, secondTe);
        assertEquals(firstTe.hashCode(), secondTe.hashCode());
        assertEquals(0, firstTe.compareTo(secondTe));

        firstTe.triggerTime = null;
        assertNotEquals(firstTe, secondTe);
        assertNotEquals(firstTe.hashCode(), secondTe.hashCode());
        assertEquals(-1, firstTe.compareTo(secondTe));

        firstTe.triggerTime = ZonedDateTime.now();
        assertNotEquals(firstTe, secondTe);
        assertNotEquals(firstTe.hashCode(), secondTe.hashCode());
        assertTrue(0 < firstTe.compareTo(secondTe));

        firstTe.triggerTime=dt;
        firstTe.status = Status.CANCELLED;
        assertNotEquals(firstTe, secondTe);
        assertNotEquals(firstTe.hashCode(), secondTe.hashCode());
        assertEquals(1, firstTe.compareTo(secondTe));

        secondTe.status = Status.CANCELLED;
        assertEquals(firstTe, secondTe);
        assertEquals(firstTe.hashCode(), secondTe.hashCode());
        assertEquals(0, firstTe.compareTo(secondTe));



        secondTe.status = Status.TRIGGERED;
        assertNotEquals(firstTe, secondTe);
        assertNotEquals(firstTe.hashCode(), secondTe.hashCode());
        assertEquals(-1, firstTe.compareTo(secondTe));

        secondTe.status = Status.CANCELLED;
        UUID id = UUID.randomUUID();

        secondTe.eventId = id;
        assertNotEquals(firstTe, secondTe);
        assertNotEquals(firstTe.hashCode(), secondTe.hashCode());
        assertEquals(-1, firstTe.compareTo(secondTe));

        firstTe.eventId = id;
        assertEquals(firstTe, secondTe);
        assertEquals(firstTe.hashCode(), secondTe.hashCode());
        assertEquals(0, firstTe.compareTo(secondTe));

    }

}
