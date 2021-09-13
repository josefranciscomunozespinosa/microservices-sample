package es.eoi.microservice.university.external.client;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PaymentClient {

    @Bean
    public WebClient localApiPaymentClient() {
        return WebClient.create("http://localhost:8081/");
    }
}
