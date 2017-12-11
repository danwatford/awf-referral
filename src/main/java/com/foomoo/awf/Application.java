package com.foomoo.awf;

import com.foomoo.awf.converters.LocalDateConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableMongoHttpSession
public class Application extends WebMvcConfigurerAdapter {
    public static void main(final String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter());
    }
}
