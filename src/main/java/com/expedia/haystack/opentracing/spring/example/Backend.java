package com.expedia.haystack.opentracing.spring.example;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.Calendar;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class Backend {

    private final MeterRegistry metricRegistry;
    private final Random random = new Random(System.currentTimeMillis());

    @Autowired
    public Backend(MeterRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @GetMapping("/api/hello")
    public String sayHello() throws InterruptedException {
        Thread.sleep(Math.abs(random.nextLong()) % 1000);
        metricRegistry.counter("hello").increment();
        return "Hello, It's " + Calendar.getInstance().getTime().toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Backend.class,
                              "--spring.application.name=backend",
                              "--server.port=9091"
                             );
    }
}
