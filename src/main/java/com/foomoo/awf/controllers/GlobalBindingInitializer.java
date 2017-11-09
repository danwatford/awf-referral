package com.foomoo.awf.controllers;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller advice to influence bindings on all controllers in the application.
 */
@ControllerAdvice
public class GlobalBindingInitializer {

    /**
     * Initializer for the {@link WebDataBinder} to register custom editors for use in the application.
     * <p>
     * Registers the {@link StringTrimmerEditor} on the String class, configured to convert empty strings to null.
     *
     * @param binder Binder to bind data between {@link WebRequest}s and objects.
     */
    @InitBinder
    public void registerCustomEditors(final WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
