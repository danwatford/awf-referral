package com.foomoo.awf.converters;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * {@link LocalDateConverter} tests.
 */
class LocalDateConverterTest {

    /**
     * Ensures that two digit years are mapped to the correct century.
     */
    @Test
    public void twoDigitYearConversionTest() {
        final LocalDateConverter converter = new LocalDateConverter();

        assertThat(converter.convert("31/12/39"), equalTo(LocalDate.of(2039, 12, 31)));
        assertThat(converter.convert("01/01/40"), equalTo(LocalDate.of(1940, 1, 1)));
    }

    /**
     * Ensure that four digit years are not affected by default century mapping.
     */
    @Test
    public void fourDigitYearConversionTest() {
        final LocalDateConverter converter = new LocalDateConverter();

        assertThat(converter.convert("31/12/1939"), equalTo(LocalDate.of(1939, 12, 31)));
        assertThat(converter.convert("01/01/1940"), equalTo(LocalDate.of(1940, 1, 1)));
        assertThat(converter.convert("31/12/2039"), equalTo(LocalDate.of(2039, 12, 31)));
        assertThat(converter.convert("01/01/2040"), equalTo(LocalDate.of(2040, 1, 1)));
    }
}