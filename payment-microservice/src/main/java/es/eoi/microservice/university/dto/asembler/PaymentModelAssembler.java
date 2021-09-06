package es.eoi.microservice.university.dto.asembler;

import es.eoi.common.dto.payment.PaymentModel;
import es.eoi.common.entity.payment.PaymentEntity;
import es.eoi.microservice.university.controller.PaymentController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
