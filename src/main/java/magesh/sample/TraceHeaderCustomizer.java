package magesh.sample;

import com.expedia.haystack.opentracing.spring.starter.support.TracerCustomizer;
import com.expedia.www.haystack.client.Tracer;
import com.expedia.www.haystack.client.propagation.Extractor;
import com.expedia.www.haystack.client.propagation.Injector;
import com.expedia.www.haystack.client.propagation.KeyConvention;
import com.expedia.www.haystack.client.propagation.TextMapPropagator;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class TraceHeaderCustomizer implements TracerCustomizer {
    @Override
    public void customize(Tracer.Builder builder) {
        TextMapPropagator httpPropagator = new TextMapPropagator
                .Builder()
                .withURLCodex()
                .withKeyConvention(new TracerKeyConvention())
                .build();
        builder.withFormat(Format.Builtin.HTTP_HEADERS, (Injector<TextMap>) httpPropagator);
        builder.withFormat(Format.Builtin.HTTP_HEADERS, (Extractor<TextMap>) httpPropagator);
    }

    public class TracerKeyConvention implements KeyConvention {

        private static final String BAGGAGE_PREFIX = "baggage-";

        private static final String TRACE_ID = "trace-id";

        private static final String SPAN_ID = "span-id";

        private static final String PARENT_ID = "parent-id";

        @Override
        public String baggagePrefix() {
            return BAGGAGE_PREFIX;
        }

        @Override
        public String parentIdKey() {
            return PARENT_ID;
        }

        @Override
        public Collection<String> parentIdKeyAliases() {
            return Collections.unmodifiableCollection(Arrays.asList(PARENT_ID));
        }


        @Override
        public String traceIdKey() {
            return TRACE_ID;
        }

        @Override
        public Collection<String> traceIdKeyAliases() {
            return Collections.unmodifiableCollection(Arrays.asList(TRACE_ID));
        }

        @Override
        public String spanIdKey() {
            return SPAN_ID;
        }

        @Override
        public Collection<String> spanIdKeyAliases() {
            return Collections.unmodifiableCollection(Arrays.asList(SPAN_ID));
        }
    }
}
