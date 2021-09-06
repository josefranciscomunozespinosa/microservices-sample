package es.eoi.microservice.university.service.impl;

import es.eoi.common.exceptions.ResourceNotFoundException;
import es.eoi.microservice.university.entity.CourseEntity;
import es.eoi.microservice.university.repository.CourseRepository;
import es.eoi.microservice.university.repository.StudentRepository;
import es.eoi.microservice.university.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<CourseEntity> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public CourseEntity findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Course with id %s not found", id)));
    }

    @Override
    public CourseEntity save(CourseEntity course) {
        return courseRepository.save(course);
    }

    @Override
    public CourseEntity update(CourseEntity course, long id) {

        CourseEntity courseDB = findById(id);

        courseDB.setDescription(course.getDescription());
        courseDB.setName(course.getName());
        courseDB.setStudents(course.getStudents());

        return save(courseDB);
    }

    @Override
    public void deleteById(long id) {
        courseRepository.deleteById(id);
    }

}
