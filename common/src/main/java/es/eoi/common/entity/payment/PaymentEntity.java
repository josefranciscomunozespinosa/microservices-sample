package es.eoi.common.entity.payment;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class PaymentEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String reference;
	private Double price;
	private Boolean payed;

}