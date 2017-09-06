package com.foomoo.awf.render;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final String FORMAT = "yyyy-MM-dd";

    @Override
    public LocalDate unmarshal(final String v) throws Exception {
        return LocalDate.parse(v, DateTimeFormatter.ofPattern(FORMAT));
    }

    @Override
    public String marshal(final LocalDate v) throws Exception {
        return v.format(DateTimeFormatter.ofPattern(FORMAT));
    }
}
