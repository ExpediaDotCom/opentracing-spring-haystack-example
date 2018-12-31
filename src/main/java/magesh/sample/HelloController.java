package magesh.sample;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final MeterRegistry metricRegistry;
    private final String appName;

    @Autowired
    public HelloController(MeterRegistry metricRegistry,
                          @Value("${spring.application.name}") String appName) {
        this.metricRegistry = metricRegistry;
        this.appName = appName;
    }

    @GetMapping("/hello")
    public String hello() {
        metricRegistry.counter("hello").increment();
        return "hello " + appName;
    }
}
