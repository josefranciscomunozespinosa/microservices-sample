package es.eoi.microservice.university.external;

import es.eoi.common.dto.StudentModel;
import es.eoi.common.dto.payment.PaymentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class PaymentService {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final WebClient paymentClient;

    @Autowired
    public PaymentService(WebClient paymentClient) {
        this.paymentClient = paymentClient;
    }


    public PaymentModel createPaymentApi(StudentModel studentModel) {
        return paymentClient
                .post()
                .uri("/api/payments/")
                .bodyValue(studentModel)
                .retrieve()
                .bodyToMono(PaymentModel.class)
                .block(REQUEST_TIMEOUT);
    }

    public PaymentModel pay(String reference) {
        return paymentClient
                .put()
                .uri("/api/payments/reference/{reference}", reference)
                .retrieve()
                .bodyToMono(PaymentModel.class)
                .block(REQUEST_TIMEOUT);
    }
}
