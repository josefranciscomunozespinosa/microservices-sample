package es.eoi.common.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
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
	private String paymentReference;
	private Date paymentDate;

	private List<CourseModel> courses;
}
