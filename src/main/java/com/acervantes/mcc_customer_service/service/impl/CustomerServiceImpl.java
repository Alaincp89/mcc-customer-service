package com.acervantes.mcc_customer_service.service.impl;

import com.acervantes.mcc_customer_service.dto.CustomerDTO;
import com.acervantes.mcc_customer_service.entity.CustomerEntity;
import com.acervantes.mcc_customer_service.repository.ICustomerRepository;
import com.acervantes.mcc_customer_service.service.interfaces.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.acervantes.mcc_customer_service.controller.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private ICustomerRepository customerRepository;


    @Override
    public CustomerDTO getByCu(String cu) {
        log.info("Get customer by cu={}", cu);
        Optional<CustomerEntity> optionalCustomerEntity = this.customerRepository.findByCu(cu);
        return optionalCustomerEntity
                .map(CustomerEntity::getDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for cu " + cu));
    }

    @Override
    public List<CustomerDTO> getAll() {
        return this.customerRepository.findAll().stream().map(CustomerEntity::getDTO).toList();
    }

    @Override
    @Transactional
    public CustomerDTO add(CustomerDTO customerDTO) {
        log.info("Add customer cu={} name={}", customerDTO.getCu(), customerDTO.getName());
        CustomerEntity customerEntity = new CustomerEntity();
        // No confiar en ID del cliente en creación
        customerEntity.setData(customerDTO);
        customerEntity.setId(null);
        return this.customerRepository.save(customerEntity).getDTO();
    }

    @Override
    @Transactional
    public CustomerDTO update(CustomerDTO customerDTO) {
        log.info("Update customer id={} cu={}", customerDTO.getId(), customerDTO.getCu());
        if (customerDTO.getId() == null && customerDTO.getCu() == null) {
            throw new IllegalArgumentException("id or cu is required to update");
        }
        CustomerEntity entity = customerDTO.getId() != null
                ? customerRepository.findById(customerDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found for id " + customerDTO.getId()))
                : customerRepository.findByCu(customerDTO.getCu()).orElseThrow(() -> new ResourceNotFoundException("Customer not found for cu " + customerDTO.getCu()));

        entity.setData(customerDTO);
        // Mantener ID original
        entity.setId(entity.getId());
        return customerRepository.save(entity).getDTO();
    }

    @Override
    @Transactional
    public CustomerDTO delete(String cu) {
        log.info("Delete customer cu={}", cu);
        CustomerEntity entity = customerRepository.findByCu(cu)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for cu " + cu));
        customerRepository.delete(entity);
        return entity.getDTO();
    }

    @Override
    public CustomerDTO getById(String id) {
        log.info("Get customer by id={}", id);
        Optional<CustomerEntity> optionalCustomerEntity = this.customerRepository.findById(id);
        return optionalCustomerEntity
                .map(CustomerEntity::getDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for id " + id));
    }
}
