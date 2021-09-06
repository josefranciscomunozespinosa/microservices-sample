package es.eoi.microservice.university.controller;

import es.eoi.common.dto.StudentModel;
import es.eoi.microservice.university.dto.asembler.StudentModelAssembler;
import es.eoi.microservice.university.entity.StudentEntity;
import es.eoi.microservice.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	StudentModelAssembler studentModelAssembler;

	/*
	@GetMapping("/students")
	public List<StudentEntity> retrieveAllStudents() {
		return studentRepository.findAll();
	}
	*/

	@GetMapping("/api/students")
	public ResponseEntity<CollectionModel<StudentModel>> getAllStudents()
	{
		List<StudentEntity> actorEntities = studentRepository.findAll();
		return new ResponseEntity<>(
				studentModelAssembler.toCollectionModel(actorEntities),
				HttpStatus.OK);
	}

	/*
	@GetMapping("/students/{id}")
	public EntityModel<StudentEntity> retrieveStudent(@PathVariable long id) {
		Optional<StudentEntity> student = studentRepository.findById(id);

		if (!student.isPresent())
			throw new ResourceNotFoundException("id-" + id);

		EntityModel<StudentEntity> resource = EntityModel.of(student.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllStudents());

		resource.add(linkTo.withRel("all-students"));

		return resource;
	}
	*/

	@GetMapping("/api/student/{id}")
	public ResponseEntity<StudentModel> getStudentById(@PathVariable("id") Long id)
	{
		return studentRepository.findById(id)
				.map(studentModelAssembler::toModel)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/*
	@DeleteMapping("/students/{id}")
	public void deleteStudent(@PathVariable long id) {
		studentRepository.deleteById(id);
	}
	*/

	@DeleteMapping("/api/students/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteStudentApi(@PathVariable long id) {
		studentRepository.deleteById(id);
	}

	/*
	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody StudentEntity student) {
		StudentEntity savedStudent = studentRepository.save(student);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	*/

	@PostMapping("/api/students")
	public ResponseEntity<StudentModel> createStudentApi(@RequestBody StudentEntity student) {
		StudentEntity savedStudent = studentRepository.save(student);

		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(savedStudent.getId())
				.toUri();

		return ResponseEntity.created(location).body(studentModelAssembler.toModel(savedStudent));
	}

	/*
	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody StudentEntity student, @PathVariable long id) {

		Optional<StudentEntity> studentOptional = studentRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		student.setId(id);

		studentRepository.save(student);

		return ResponseEntity.noContent().build();
	}
	*/

	@PutMapping("/api/students/{id}")
	public ResponseEntity<StudentModel> updateStudentApi(@RequestBody StudentEntity student, @PathVariable long id) {

		Optional<StudentEntity> studentOptional = studentRepository.findById(id);

		if (!studentOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		student.setId(id);

		StudentEntity savedStudent = studentRepository.save(student);
		return ResponseEntity.ok().body(studentModelAssembler.toModel(savedStudent));
	}
}
