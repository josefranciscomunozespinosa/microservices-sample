package es.eoi.microservice.payment.dto.asembler;

import es.eoi.common.dto.payment.PaymentModel;
import es.eoi.microservice.payment.controller.PaymentController;
import es.eoi.microservice.payment.entity.payment.PaymentEntity;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentModelAssembler extends RepresentationModelAssemblerSupport<PaymentEntity, PaymentModel> {

    public PaymentModelAssembler() {
        super(PaymentController.class, PaymentModel.class);
    }

    @Override
    public PaymentModel toModel(PaymentEntity entity) {

        PaymentModel paymentModel = instantiateModel(entity);

        paymentModel.add(linkTo(
                methodOn(PaymentController.class)
                        .getPaymentById(entity.getId()))
                .withSelfRel());

        paymentModel.setId(entity.getId());
        paymentModel.setName(entity.getName());
        paymentModel.setPayed(entity.getPayed());
        paymentModel.setReference(entity.getReference());
        paymentModel.setPrice(entity.getPrice());
        return paymentModel;
    }

}
