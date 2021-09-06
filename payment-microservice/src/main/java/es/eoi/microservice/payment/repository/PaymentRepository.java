package es.eoi.microservice.payment.repository;

import es.eoi.microservice.payment.entity.payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>{

    List<PaymentEntity> getAllByPayedIsFalse();

    Optional<PaymentEntity> getFirstByReference(String reference);

}
