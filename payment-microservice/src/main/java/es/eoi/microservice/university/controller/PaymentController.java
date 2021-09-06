package es.eoi.microservice.university.controller;

import es.eoi.common.dto.payment.PaymentModel;
import es.eoi.common.entity.payment.PaymentEntity;
import es.eoi.microservice.university.dto.asembler.PaymentModelAssembler;
import es.eoi.microservice.university.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PaymentController {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	PaymentModelAssembler paymentModelAssembler;


	@GetMapping("/api/Payments")
	public ResponseEntity<CollectionModel<PaymentModel>> getAllPayments()
	{
		List<PaymentEntity> PaymentEntityList = paymentRepository.findAll();
		return new ResponseEntity<>(
				paymentModelAssembler.toCollectionModel(PaymentEntityList),
				HttpStatus.OK);
	}


	@GetMapping("/api/Payment/{id}")
	public ResponseEntity<PaymentModel> getPaymentById(@PathVariable("id") Long id)
	{
		return paymentRepository.findById(id)
				.map(paymentModelAssembler::toModel)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/api/Payments")
	public ResponseEntity<PaymentModel> createPaymentApi(@RequestBody PaymentEntity Payment) {
		PaymentEntity savedPayment = paymentRepository.save(Payment);

		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(savedPayment.getId())
				.toUri();

		return ResponseEntity.created(location).body(paymentModelAssembler.toModel(savedPayment));
	}

	@PutMapping("/api/Payments/{id}")
	public ResponseEntity<PaymentModel> updatePaymentApi(@RequestBody PaymentEntity Payment, @PathVariable long id) {

		Optional<PaymentEntity> PaymentOptional = paymentRepository.findById(id);

		if (!PaymentOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Payment.setId(id);

		PaymentEntity savedPayment = paymentRepository.save(Payment);
		return ResponseEntity.ok().body(paymentModelAssembler.toModel(savedPayment));
	}
}
