package es.eoi.microservice.university.service;

import es.eoi.microservice.university.entity.CourseEntity;

import java.util.List;

public interface CourseService {
    List<CourseEntity> findAll();

    CourseEntity findById(Long id);

    CourseEntity save(CourseEntity course);

    CourseEntity update(CourseEntity course, long id);

    void deleteById(long id);
}
