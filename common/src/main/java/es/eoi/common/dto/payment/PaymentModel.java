package es.eoi.common.dto.payment;

import es.eoi.common.dto.CourseModel;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentModel extends RepresentationModel<PaymentModel> {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	private String name;
	private String reference;
	private Double price;
	private Boolean payed;

}