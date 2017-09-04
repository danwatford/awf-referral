package com.foomoo.awf.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@FacesConverter(value="localDateConverter")
public class LocalDateConverter implements Converter {
    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (final DateTimeParseException dtpe) {
            try {
                return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/M/yyyy"));
            } catch (final DateTimeParseException dtpe2) {
                throw new ConverterException(new FacesMessage("Cannot convert String "+ value + " to date."));
            }
        }
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        final LocalDate localDate = (LocalDate) value;
        return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
