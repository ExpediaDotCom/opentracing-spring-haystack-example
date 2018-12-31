package magesh.sample;

import com.expedia.haystack.opentracing.spring.starter.support.TracerCustomizer;
import com.expedia.www.haystack.client.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurer {
//    @Bean
//    public Dispatcher dispatcher(MetricsRegistry metricsRegistry) {
//        return new InMemoryDispatcher.Builder(metricsRegistry).withLimit(10).build();
//    }

    @Bean
    public TracerCustomizer tracerBuilderCustomizer() {
        return Tracer.Builder::withDualSpanMode;
    }
}
