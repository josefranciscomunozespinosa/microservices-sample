package es.eoi.microservice.university.service;

import es.eoi.microservice.university.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    List<StudentEntity> findAll();

    StudentEntity findById(Long id);

    StudentEntity save(StudentEntity course);

    StudentEntity update(StudentEntity course, long id);

    StudentEntity createPayment(Long studentId);

    StudentEntity pay(Long studentId);

    void deleteById(long id);

}
