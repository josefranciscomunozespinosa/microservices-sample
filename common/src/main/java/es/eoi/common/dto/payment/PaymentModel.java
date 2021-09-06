package es.eoi.common.dto.payment;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentModel extends RepresentationModel<PaymentModel> {

	@JsonIgnore
	private Long id;

	private String name;
	private String reference;
	private Double price;
	private Boolean payed;

}