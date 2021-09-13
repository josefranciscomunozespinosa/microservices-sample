package es.eoi.microservice.university.mapper;

import es.eoi.common.dto.CourseModel;
import es.eoi.microservice.university.entity.CourseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseModel toModel(CourseEntity courseEntity) {
        CourseModel courseModel = CourseModel.builder()
                .id(courseEntity.getId())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .build();
        return courseModel;
    }

    public static List<CourseModel> toModel(List<CourseEntity> courseEntityList) {
        return courseEntityList.stream()
                .map(CourseMapper::toModel)
                .collect(Collectors.toList());
    }
}
