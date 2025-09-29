package com.acervantes.mcc_customer_service.controller;

import com.acervantes.mcc_customer_service.dto.CustomerDTO;
import com.acervantes.mcc_customer_service.service.interfaces.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerRESTController.class)
class CustomerRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDTO dto() {
        return CustomerDTO.builder()
                .id("id-1")
                .cu("CU001")
                .name("John Doe")
                .address("Street 123")
                .email("john@example.com")
                .mobile("555123")
                .build();
    }

    @Test
    void getAll_returnsList() throws Exception {
        when(customerService.getAll()).thenReturn(List.of(dto()));
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cu", is("CU001")));
    }

    @Test
    void getById_found() throws Exception {
        when(customerService.getById("id-1")).thenReturn(dto());
        mockMvc.perform(get("/customers/id/{id}", "id-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id-1")));
    }

    @Test
    void getByCu_found() throws Exception {
        when(customerService.getByCu("CU001")).thenReturn(dto());
        mockMvc.perform(get("/customers/cu/{cu}", "CU001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cu", is("CU001")));
    }

    @Test
    void deleteByCu_noContent() throws Exception {
        when(customerService.delete("CU001")).thenReturn(dto());
        mockMvc.perform(delete("/customers/cu/{cu}", "CU001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addCustomer_createsAndReturnsLocation() throws Exception {
        when(customerService.add(any(CustomerDTO.class))).thenReturn(dto());
        CustomerDTO input = dto();
        input.setId(null);
        String json = objectMapper.writeValueAsString(input);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/customers/id/")))
                .andExpect(jsonPath("$.cu", is("CU001")));
    }

    @Test
    void updateCustomer_ok() throws Exception {
        when(customerService.update(any(CustomerDTO.class))).thenReturn(dto());
        String json = objectMapper.writeValueAsString(dto());

        mockMvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id-1")));
    }
}


