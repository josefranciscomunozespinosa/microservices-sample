package es.eoi.microservice.university.dto.asembler;

import es.eoi.common.dto.CourseModel;
import es.eoi.common.dto.StudentModel;
import es.eoi.microservice.university.controller.CourseController;
import es.eoi.microservice.university.controller.StudentController;
import es.eoi.microservice.university.entity.CourseEntity;
import es.eoi.microservice.university.entity.StudentEntity;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentModelAssembler extends RepresentationModelAssemblerSupport<StudentEntity, StudentModel> {

    public StudentModelAssembler() {
        super(StudentController.class, StudentModel.class);
    }

    @Override
    public StudentModel toModel(StudentEntity entity) {

        StudentModel studentModel = instantiateModel(entity);

        studentModel.add(linkTo(
                methodOn(StudentController.class)
                        .getStudentById(entity.getId()))
                .withSelfRel());

        studentModel.setId(entity.getId());
        studentModel.setName(entity.getName());
        studentModel.setPassportNumber(entity.getPassportNumber());

        studentModel.setCourses(toCourseModel(entity.getCourses()));
        return studentModel;
    }

    private List<CourseModel> toCourseModel(List<CourseEntity> courses) {
        if (courses.isEmpty())
            return Collections.emptyList();
        return courses.stream()
                .map(course -> CourseModel.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .build()
                        .add(WebMvcLinkBuilder.linkTo(
                                methodOn(CourseController.class)
                                        .getCourseById(course.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
    }

}
