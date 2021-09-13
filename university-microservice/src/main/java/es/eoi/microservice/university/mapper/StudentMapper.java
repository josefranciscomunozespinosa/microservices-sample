package es.eoi.microservice.university.mapper;

import es.eoi.common.dto.StudentModel;
import es.eoi.microservice.university.entity.StudentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentModel toModel(StudentEntity studentEntity) {
        StudentModel studentModel = StudentModel.builder()
                .id(studentEntity.getId())
                .name(studentEntity.getName())
                .passportNumber(studentEntity.getPassportNumber())
                .paymentReference(studentEntity.getPaymentReference())
                .courses(CourseMapper.toModel(studentEntity.getCourses()))
                .build();
        return studentModel;
    }

    public static List<StudentModel> toModel(List<StudentEntity> studentEntityList) {
        return studentEntityList.stream()
                .map(StudentMapper::toModel)
                .collect(Collectors.toList());
    }
}
