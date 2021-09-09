package es.eoi.common.dto.payment;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentModel extends RepresentationModel<PaymentModel> {

	private String name;
	private String reference;
	private Double price;
	private Boolean payed;

}