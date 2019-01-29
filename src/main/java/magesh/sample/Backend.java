package magesh.sample;

import com.expedia.haystack.opentracing.spring.starter.support.TracerCustomizer;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
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

    @GetMapping("/api")
    public String sayHello() throws InterruptedException {
        Thread.sleep(Math.abs(random.nextLong()) % 1000);
        metricRegistry.counter("hello").increment();
        return "Hello World!";
    }

    @Bean
    public TracerCustomizer tracerCustomizer() {
        return new TraceHeaderCustomizer();
    }

    public static void main(String[] args) {
        SpringApplication.run(Backend.class,
                              "--spring.application.name=backend",
                              "--server.port=9091"
                             );
    }
}
