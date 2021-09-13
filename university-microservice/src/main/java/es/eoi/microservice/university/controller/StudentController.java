package es.eoi.microservice.university.controller;

import es.eoi.common.dto.StudentModel;
import es.eoi.microservice.university.dto.asembler.StudentModelAssembler;
import es.eoi.microservice.university.entity.StudentEntity;
import es.eoi.microservice.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	StudentModelAssembler studentModelAssembler;


	@GetMapping("/api/students")
	public ResponseEntity<CollectionModel<StudentModel>> getAllStudents()
	{
		List<StudentEntity> actorEntities = studentService.findAll();
		return new ResponseEntity<>(
				studentModelAssembler.toCollectionModel(actorEntities),
				HttpStatus.OK);
	}

	@GetMapping("/api/student/{id}")
	public ResponseEntity<StudentModel> getStudentById(@PathVariable("id") Long id)
	{
		StudentEntity studentEntity = studentService.findById(id);
		return ResponseEntity.ok().body(studentModelAssembler.toModel(studentEntity));
	}

	@DeleteMapping("/api/students/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteStudentApi(@PathVariable long id) {
		studentService.deleteById(id);
	}

	@PostMapping("/api/students")
	public ResponseEntity<StudentModel> createStudentApi(@RequestBody StudentEntity student) {
		StudentEntity savedStudent = studentService.save(student);

		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(savedStudent.getId())
				.toUri();

		return ResponseEntity.created(location).body(studentModelAssembler.toModel(savedStudent));
	}

	@PutMapping("/api/students/{id}")
	public ResponseEntity<StudentModel> updateStudentApi(@RequestBody StudentEntity student, @PathVariable long id) {

		StudentEntity savedStudent = studentService.update(student, id);

		return ResponseEntity.ok().body(studentModelAssembler.toModel(savedStudent));
	}


	/* ==== Methods that call payment Microservice ==== */

	@PostMapping("/api/students/{id}/createPayment")
	public ResponseEntity<StudentModel> createPayment(@PathVariable long id) {

		StudentEntity savedStudent = studentService.createPayment(id);

		return ResponseEntity.ok().body(studentModelAssembler.toModel(savedStudent));
	}

	@PutMapping("/api/students/{id}/pay")
	public ResponseEntity<StudentModel> pay(@PathVariable long id) {

		StudentEntity savedStudent = studentService.pay(id);

		return ResponseEntity.ok().body(studentModelAssembler.toModel(savedStudent));
	}
}
