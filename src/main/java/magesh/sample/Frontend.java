package magesh.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@RestController
public class Frontend {
    @Value("${backend.url:http://localhost:9091}")
    private String backendBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String callBackend() {
        return restTemplate.getForObject(backendBaseUrl + "/api", String.class);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Frontend.class,
                              "--spring.application.name=frontend",
                              "--server.port=9090"
                             );
    }
}
