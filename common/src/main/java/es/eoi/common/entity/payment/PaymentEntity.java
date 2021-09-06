package es.eoi.common.entity.payment;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentEntity {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	private String name;
	private String reference;
	private Double price;
	private Boolean payed;

}