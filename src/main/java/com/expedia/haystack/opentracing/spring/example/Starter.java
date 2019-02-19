package com.expedia.haystack.opentracing.spring.example;

import org.springframework.boot.SpringApplication;

public class Starter {
    public static void main(String[] args) {
        String name = args.length > 0 ? args[0].toLowerCase() : "";
        Class application;
        int port;
        
        if (name.equalsIgnoreCase("frontend")) {
            application = Frontend.class;
            port = 9090;
        }
        else {
            name = "backend";
            application = Backend.class;
            port = 9091;
        }

        SpringApplication.run(application,
                              "--spring.application.name=" + name,
                              "--server.port=" + port
                             );
    }
}
