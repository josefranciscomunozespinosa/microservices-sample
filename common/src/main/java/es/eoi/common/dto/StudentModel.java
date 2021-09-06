package es.eoi.common.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudentModel extends RepresentationModel<StudentModel> {

	private Long id;
	private String name;
	private String passportNumber;

	private List<CourseModel> courses;
}
