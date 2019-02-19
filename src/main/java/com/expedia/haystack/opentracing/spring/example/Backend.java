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
            .longs(30000, 60000)
            .iterator();

    @Autowired
    public Backend(MeterRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @GetMapping("/api/hello")
    public String sayHello() throws InterruptedException {
        // Creating sleep for anomalous response every 5th clock minute
        if((System.currentTimeMillis() / 1000) % 300 == 0) {
            Thread.sleep(random.nextLong());
        }
        metricRegistry.counter("hello").increment();
        return "Hello, It's " + Calendar.getInstance().getTime().toString();
    }
}
