/*
 * Copyright 2018 Expedia, Inc.
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 *
 */
package com.expedia.haystack.opentracing.spring.example;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.Calendar;
import java.util.PrimitiveIterator;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
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
        long currentTime = (System.currentTimeMillis() / 1000);
        long modulo = currentTime % 900;

        if( modulo >= 0 && modulo < 60) {
            // mocking some random slow down and failures every 15th minute
            Thread.sleep(random.nextLong());
            throw new RandomException("Anomalous Failure");
        } else if(currentTime % 60 == 1) {
            // and some trickle failure every minute
            throw new RandomException("Random Failure");
        }

        metricRegistry.counter("hello").increment();
        return "Hello, It's " + Calendar.getInstance().getTime().toString();
    }

    private final class RandomException extends RuntimeException {
        RandomException(String message) {
            super(message);
        }
    }
}
