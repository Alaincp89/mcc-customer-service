package com.acervantes.mcc_customer_service.repository;

import com.acervantes.mcc_customer_service.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<CustomerEntity, String> {

    // Define otros metodos
    Optional<CustomerEntity> findByCu(String cu);

}
