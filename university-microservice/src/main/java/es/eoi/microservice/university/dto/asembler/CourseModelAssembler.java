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
public class CourseModelAssembler extends RepresentationModelAssemblerSupport<CourseEntity, CourseModel> {

    public CourseModelAssembler() {
        super(CourseController.class, CourseModel.class);
    }

    @Override
    public CourseModel toModel(CourseEntity entity) {

        CourseModel CourseModel = instantiateModel(entity);

        CourseModel.add(linkTo(
                methodOn(CourseController.class)
                        .getCourseById(entity.getId()))
                .withSelfRel());

        CourseModel.setId(entity.getId());
        CourseModel.setName(entity.getName());
        CourseModel.setDescription(entity.getDescription());
        CourseModel.setStudents(toStudentModel(entity.getStudents()));
        return CourseModel;
    }

    private List<StudentModel> toStudentModel(List<StudentEntity> students) {
        if (students.isEmpty())
            return Collections.emptyList();
        return students.stream()
                .map(student -> StudentModel.builder()
                        .id(student.getId())
                        .name(student.getName())
                        .passportNumber(student.getPassportNumber())
                        .build()
                        .add(WebMvcLinkBuilder.linkTo(
                                methodOn(StudentController.class)
                                        .getStudentById(student.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
    }

}
