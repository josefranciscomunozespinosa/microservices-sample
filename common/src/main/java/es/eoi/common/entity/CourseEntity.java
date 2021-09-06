package es.eoi.common.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "students")
@Entity
public class CourseEntity {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;

	@ManyToMany(mappedBy = "courses",fetch = FetchType.EAGER)
	private List<StudentEntity> students;
}