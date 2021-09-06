package es.eoi.microservice.payment.controller;

import es.eoi.common.dto.StudentModel;
import es.eoi.common.dto.payment.PaymentModel;
import es.eoi.microservice.payment.dto.asembler.PaymentModelAssembler;
import es.eoi.microservice.payment.entity.payment.PaymentEntity;
import es.eoi.microservice.payment.repository.PaymentRepository;
import es.eoi.microservice.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	PaymentModelAssembler paymentModelAssembler;


	@GetMapping("/api/payments/pending")
	public ResponseEntity<CollectionModel<PaymentModel>> getAllPendingPayments()
	{
		List<PaymentEntity> paymentEntityList = paymentService.getAllPendingPayments();
		return new ResponseEntity<>(
				paymentModelAssembler.toCollectionModel(paymentEntityList),
				HttpStatus.OK);
	}

	/*
	@GetMapping("/api/payment/id/{id}")
	public ResponseEntity<PaymentModel> getPaymentById(@PathVariable("id") Long id)
	{
		PaymentEntity paymentEntity = paymentService.findById(id);

		return ResponseEntity.ok().body(paymentModelAssembler.toModel(paymentEntity));
	}
	*/

	@GetMapping("/api/payment/reference/{reference}")
	public ResponseEntity<PaymentModel> getPaymentByReference(@PathVariable("reference") String reference)
	{
		PaymentEntity paymentEntity = paymentService.getFirstByReference(reference);

		return ResponseEntity.ok().body(paymentModelAssembler.toModel(paymentEntity));
	}

	@PostMapping("/api/payments")
	public ResponseEntity<PaymentModel> createPaymentApi(@RequestBody StudentModel studentModel) {
		if(studentModel==null){
			throw new IllegalArgumentException("Wrong params");
		}

		PaymentEntity savedPayment = paymentService.generatePaymentData(studentModel);

		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(savedPayment.getId())
				.toUri();

		return ResponseEntity.created(location).body(paymentModelAssembler.toModel(savedPayment));
	}

	@PutMapping("/api/payments/reference/{reference}")
	public ResponseEntity<PaymentModel> pay(@PathVariable("reference") String reference) {

		PaymentEntity payment = paymentService.pay(reference);

		PaymentEntity savedPayment = paymentRepository.save(payment);
		return ResponseEntity.ok().body(paymentModelAssembler.toModel(savedPayment));
	}
}
