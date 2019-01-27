package magesh.sample;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final MeterRegistry metricRegistry;
    private final String appName;
    private final Random random = new Random(System.currentTimeMillis());

    @Autowired
    public HelloController(MeterRegistry metricRegistry,
                          @Value("${spring.application.name}") String appName) {
        this.metricRegistry = metricRegistry;
        this.appName = appName;
    }

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(Math.abs(random.nextLong()) % 1000);
        metricRegistry.counter("hello").increment();
        return "hello " + appName;
    }
}
