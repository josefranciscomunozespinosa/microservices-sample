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

        PaymentModel PaymentModel = instantiateModel(entity);

        PaymentModel.add(linkTo(
                methodOn(PaymentController.class)
                        .getPaymentById(entity.getId()))
                .withSelfRel());

        PaymentModel.setId(entity.getId());
        PaymentModel.setName(entity.getName());
        PaymentModel.setPayed(entity.getPayed());
        PaymentModel.setReference(entity.getReference());
        return PaymentModel;
    }

}
