package es.eoi.microservice.university.controller;

import es.eoi.common.dto.CourseModel;
import es.eoi.common.dto.StudentModel;
import es.eoi.microservice.university.dto.asembler.CourseModelAssembler;
import es.eoi.microservice.university.entity.CourseEntity;
import es.eoi.microservice.university.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CourseController {

	@Autowired
	CourseService courseService;

	@Autowired
	CourseModelAssembler courseModelAssembler;


	@GetMapping("/api/courses")
	public ResponseEntity<CollectionModel<CourseModel>> getAllCourses()
	{
		List<CourseEntity> courseEntityList = courseService.findAll();
		return new ResponseEntity<>(
				courseModelAssembler.toCollectionModel(courseEntityList),
				HttpStatus.OK);
	}


	@GetMapping("/api/course/{id}")
	public ResponseEntity<CourseModel> getCourseById(@PathVariable("id") Long id)
	{
		CourseEntity courseEntity = courseService.findById(id);

		return ResponseEntity.ok().body(courseModelAssembler.toModel(courseEntity));
	}


	@DeleteMapping("/api/courses/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteCourseApi(@PathVariable long id) {
		courseService.deleteById(id);
	}


	@PostMapping("/api/courses")
	public ResponseEntity<CourseModel> createCourseApi(@RequestBody CourseEntity course) {
		CourseEntity savedCourse = courseService.save(course);

		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(savedCourse.getId())
				.toUri();

		return ResponseEntity.created(location).body(courseModelAssembler.toModel(savedCourse));
	}

	@PutMapping("/api/courses/{id}")
	public ResponseEntity<CourseModel> updateCourseApi(@RequestBody CourseEntity course, @PathVariable long id) {

		CourseEntity savedCourse = courseService.update(course, id);

		return ResponseEntity.ok().body(courseModelAssembler.toModel(savedCourse));
	}

}
