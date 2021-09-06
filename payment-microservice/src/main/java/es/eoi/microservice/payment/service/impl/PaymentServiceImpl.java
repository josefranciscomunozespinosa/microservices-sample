package es.eoi.microservice.payment.service.impl;

import es.eoi.common.dto.StudentModel;
import es.eoi.common.exceptions.ResourceNotFoundException;
import es.eoi.microservice.payment.entity.payment.PaymentEntity;
import es.eoi.microservice.payment.repository.PaymentRepository;
import es.eoi.microservice.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Double TAX = 370.57d;

    @Autowired
    private PaymentRepository paymentRepository;



    @Override
    public List<PaymentEntity> getAllPendingPayments() {
        return paymentRepository.getAllByPayedIsFalse();
    }

    @Override
    public PaymentEntity findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Payment with id %s not found", id)));
    }

    @Override
    public PaymentEntity getFirstByReference(String reference) {
        return paymentRepository.getFirstByReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Payment with reference %s not found", reference)));
    }

    @Override
    public PaymentEntity pay(String reference) {

        PaymentEntity paymentEntity = getFirstByReference(reference);

        paymentEntity.setPayed(Boolean.TRUE);

        paymentRepository.save(paymentEntity);

        log.info("Pay done for student {} with payment reference {}", paymentEntity.getName(), paymentEntity.getReference());
        return paymentEntity;
    }

    @Override
    public PaymentEntity generatePaymentData(StudentModel studentModel) {
        log.info("Generating payment data init for student {}", studentModel.getName());

        PaymentEntity paymentEntity = calculatePaymentData(studentModel);

        paymentEntity = paymentRepository.save(paymentEntity);

        log.info("Payment data created for student {} with payment reference {}", studentModel.getName(), paymentEntity.getReference());

        return paymentEntity;
    }

    private PaymentEntity calculatePaymentData(StudentModel studentModel) {
        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setPayed(Boolean.FALSE);
        paymentEntity.setName(studentModel.getName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        paymentEntity.setReference(formatter.format(new Date()) + "_" +studentModel.getPassportNumber());
        Double price = 0d;
        if(studentModel.getCourses()!=null && !studentModel.getCourses().isEmpty()){
            price = studentModel.getCourses().size() * TAX;
        }
        paymentEntity.setPrice(price);

        return paymentEntity;
    }
}
