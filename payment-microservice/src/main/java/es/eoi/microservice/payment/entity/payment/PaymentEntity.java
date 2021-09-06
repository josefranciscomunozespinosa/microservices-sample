package es.eoi.microservice.payment.entity.payment;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "payment")
public class PaymentEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String reference;
	private Double price;
	private Boolean payed;

}