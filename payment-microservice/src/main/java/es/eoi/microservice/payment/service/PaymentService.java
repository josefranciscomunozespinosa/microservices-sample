package es.eoi.microservice.payment.service;

import es.eoi.common.dto.StudentModel;
import es.eoi.microservice.payment.entity.payment.PaymentEntity;

import java.util.List;

public interface PaymentService {

    List<PaymentEntity> getAllPendingPayments();

    PaymentEntity generatePaymentData(StudentModel studentModel);

    PaymentEntity findById(Long id);

    PaymentEntity getFirstByReference(String reference);

    PaymentEntity pay(String reference);
}
