package com.expedia.haystack.opentracing.spring.example;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.Calendar;
import java.util.PrimitiveIterator;
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
    private final PrimitiveIterator.OfLong random = new Random(System.currentTimeMillis())
            .longs(1000, 1500)
            .iterator();

    @Autowired
    public Backend(MeterRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @GetMapping("/api/hello")
    public String sayHello() throws InterruptedException {
        // Creating sleep for anomalous response every 5th clock minute
        long currentTime = (System.currentTimeMillis() / 1000);
        long modulo = currentTime % 900;
        if( modulo >= 0 && modulo < 60) {
            System.out.println("Random Sleep");
            Thread.sleep(random.nextLong());
            throw new RuntimeException("Anomalous Failure");
        } else if(currentTime % 60 == 1) {
            throw new RuntimeException("Random Failure");
        }
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
