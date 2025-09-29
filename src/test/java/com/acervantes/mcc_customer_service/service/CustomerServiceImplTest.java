package com.acervantes.mcc_customer_service.service;

import com.acervantes.mcc_customer_service.controller.ResourceNotFoundException;
import com.acervantes.mcc_customer_service.dto.CustomerDTO;
import com.acervantes.mcc_customer_service.entity.CustomerEntity;
import com.acervantes.mcc_customer_service.repository.ICustomerRepository;
import com.acervantes.mcc_customer_service.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerEntity buildEntity() {
        CustomerEntity e = new CustomerEntity();
        e.setId("78db14e2-188d-46ed-bffc-4420cf1bc1a1");
        e.setCu("99999999");
        e.setName("Alain Cervantes");
        e.setAddress("Street 123");
        e.setEmail("acervantes9@hotmail.com");
        e.setMobile("555123");
        return e;
    }

    private CustomerDTO buildDto() {
        return CustomerDTO.builder()
                .id("78db14e2-188d-46ed-bffc-4420cf1bc1a1")
                .cu("99999999")
                .name("Alain Cervantes")
                .address("Street 123")
                .email("acervantes9@hotmail.com")
                .mobile("555123")
                .build();
    }

    @Test
    void getByCu_found_returnsDto() {
        CustomerEntity entity = buildEntity();
        when(customerRepository.findByCu("99999999")).thenReturn(Optional.of(entity));

        CustomerDTO dto = customerService.getByCu("99999999");

        assertEquals("99999999", dto.getCu());
        verify(customerRepository, times(1)).findByCu("99999999");
    }

    @Test
    void getByCu_notFound_throws() {
        when(customerRepository.findByCu("X")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.getByCu("X"));
    }

    @Test
    void getAll_mapsEntitiesToDto() {
        when(customerRepository.findAll()).thenReturn(List.of(buildEntity()));
        List<CustomerDTO> result = customerService.getAll();
        assertEquals(1, result.size());
        assertEquals("99999999", result.get(0).getCu());
    }

    @Test
    void add_clearsId_andSaves() {
        CustomerDTO input = buildDto();
        input.setId("should-be-ignored");
        CustomerEntity saved = buildEntity();
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(saved);

        CustomerDTO created = customerService.add(input);

        assertEquals("99999999", created.getCu());
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void update_byId_updatesAndSaves() {
        CustomerDTO input = buildDto();
        when(customerRepository.findById("78db14e2-188d-46ed-bffc-4420cf1bc1a1")).thenReturn(Optional.of(buildEntity()));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(buildEntity());

        CustomerDTO updated = customerService.update(input);
        assertEquals("99999999", updated.getCu());
    }

    @Test
    void update_withoutIdAndCu_throws() {
        CustomerDTO input = CustomerDTO.builder().build();
        assertThrows(IllegalArgumentException.class, () -> customerService.update(input));
    }

    @Test
    void delete_found_deletesAndReturnsDto() {
        CustomerEntity entity = buildEntity();
        when(customerRepository.findByCu("99999999")).thenReturn(Optional.of(entity));

        CustomerDTO dto = customerService.delete("99999999");
        assertEquals("99999999", dto.getCu());
        verify(customerRepository).delete(entity);
    }

    @Test
    void getById_found_returnsDto() {
        when(customerRepository.findById("78db14e2-188d-46ed-bffc-4420cf1bc1a1")).thenReturn(Optional.of(buildEntity()));
        CustomerDTO dto = customerService.getById("78db14e2-188d-46ed-bffc-4420cf1bc1a1");
        assertEquals("78db14e2-188d-46ed-bffc-4420cf1bc1a1", dto.getId());
    }

    @Test
    void getById_notFound_throws() {
        when(customerRepository.findById("nope")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.getById("nope"));
    }
}


