package com.foomoo.awf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@SpringBootApplication
@EnableMongoHttpSession
public class Application {
    public static void main(final String args[]) {
        SpringApplication.run(Application.class, args);
    }
}
