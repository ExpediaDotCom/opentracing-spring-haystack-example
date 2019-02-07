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

    private static final int DIVIDER = 50;
    private final MeterRegistry metricRegistry;
    private final Random random = new Random(System.currentTimeMillis());
    private long count = 1;

    @Autowired
    public Backend(MeterRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @GetMapping("/api/hello")
    public String sayHello() throws InterruptedException {
        if((count % DIVIDER) == 0) {
            // Creating anomalous sleep for anomaly detection
            Thread.sleep(60000);
        } else {
            Thread.sleep(Math.abs(random.nextLong()) % 1000);
        }
        count++;
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
