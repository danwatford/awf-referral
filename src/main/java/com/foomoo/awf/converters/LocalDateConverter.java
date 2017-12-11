package com.foomoo.awf.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Converter of Strings to LocalDates, using the non-default base date for two-digit years.
 * The default base date (century) used for two-digit years is 2000, which is not suitable to applications where dates of birth are commonly entered
 * using two-digit years. This converter will ensure two-digit years fall in the range of 1940 to 2039..
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    private static final LocalDate BASE_DATE = LocalDate.of(1940, 1, 1);

    private static final DateTimeFormatter FORMATTER =
            new DateTimeFormatterBuilder().appendPattern("d/M/")
                                          .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 4, BASE_DATE)
                                          .toFormatter();

    @Override
    public LocalDate convert(final String source) {
        if (StringUtils.isNotBlank(source)) {
            return LocalDate.parse(source, FORMATTER);
        } else {
            return null;
        }
    }
}
