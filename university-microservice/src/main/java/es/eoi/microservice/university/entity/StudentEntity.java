package es.eoi.microservice.university.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "courses")
@Entity(name = "student")
public class StudentEntity implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String passportNumber;


	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(
			name = "student_course",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<CourseEntity> courses;

}
