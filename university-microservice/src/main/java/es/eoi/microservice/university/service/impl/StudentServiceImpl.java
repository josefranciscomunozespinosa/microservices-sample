package es.eoi.microservice.university.service.impl;

import es.eoi.common.dto.StudentModel;
import es.eoi.common.dto.payment.PaymentModel;
import es.eoi.common.exceptions.PaymentException;
import es.eoi.common.exceptions.ResourceNotFoundException;
import es.eoi.microservice.university.entity.StudentEntity;
import es.eoi.microservice.university.external.PaymentService;
import es.eoi.microservice.university.mapper.StudentMapper;
import es.eoi.microservice.university.repository.StudentRepository;
import es.eoi.microservice.university.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PaymentService paymentService;

    @Override
    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public StudentEntity findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Student with id %s not found", id)));
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    @Override
    public StudentEntity update(StudentEntity studentEntity, long id) {

        StudentEntity studentEntityDB = findById(id);

        studentEntityDB.setName(studentEntity.getName());
        studentEntityDB.setPassportNumber(studentEntity.getPassportNumber());
        studentEntityDB.setCourses(studentEntity.getCourses());

        return save(studentEntityDB);
    }

    @Override
    public StudentEntity createPayment(Long studentId) {
        log.debug("Creating payment reference for student {}", studentId);
        StudentEntity studentEntity = findById(studentId);
        StudentModel studentModel = StudentMapper.toModel(studentEntity);
        try {
            PaymentModel paymentResponse = paymentService.createPaymentApi(studentModel);
            studentEntity.setPaymentReference(paymentResponse.getReference());
            log.debug("Payment created correctly. Payment reference {}", paymentResponse.getReference());
            studentEntity = studentRepository.save(studentEntity);
        } catch (Exception e){
            log.error("Payment error when try to create Payment", e.getCause());
            throw new PaymentException(e.getMessage());
        }
        return studentEntity;
    }

    @Override
    public StudentEntity pay(Long studentId) {
        StudentEntity studentEntity = findById(studentId);
        if(studentEntity.getPaymentReference()==null){
            throw new PaymentException(String.format("Student %s with id %s hasn't payment reference assigned yet.",
                    studentEntity.getName(), studentId));
        }
        if(studentEntity.getPaymentDate()!=null){
            throw new PaymentException(String.format("Student %s with id %s already have the taxes payed wiht payment reference %s.",
                    studentEntity.getName(), studentId, studentEntity.getPaymentReference()));
        }
        try {
            PaymentModel paymentModel = paymentService.pay(studentEntity.getPaymentReference());
            if(paymentModel!=null && paymentModel.getPayed()){
                studentEntity.setPaymentDate(new Date());
            }
        }catch (Exception e){
            log.error("Payment error", e.getCause());
            throw new PaymentException(e.getMessage());
        }
        return studentRepository.save(studentEntity);
    }


    @Override
    public void deleteById(long id) {
        studentRepository.deleteById(id);
    }

}
