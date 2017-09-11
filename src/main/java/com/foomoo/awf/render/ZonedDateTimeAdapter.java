package com.foomoo.awf.render;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public ZonedDateTime unmarshal(final String v) throws Exception {
        return ZonedDateTime.parse(v, DateTimeFormatter.ofPattern(FORMAT));
    }

    @Override
    public String marshal(final ZonedDateTime v) throws Exception {
        return v.format(DateTimeFormatter.ofPattern(FORMAT));
    }
}
