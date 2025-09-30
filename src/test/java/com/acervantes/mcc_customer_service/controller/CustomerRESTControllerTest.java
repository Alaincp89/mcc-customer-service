package com.acervantes.mcc_customer_service.controller;

import com.acervantes.mcc_customer_service.dto.CustomerDTO;
import com.acervantes.mcc_customer_service.service.interfaces.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

    @MockitoBean
    private ICustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDTO dto() {
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
    void getAll_returnsList() throws Exception {
        when(customerService.getAll()).thenReturn(List.of(dto()));
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cu", is("99999999")));
    }

    @Test
    void getByCu_found() throws Exception {
        when(customerService.getByCu("99999999")).thenReturn(dto());
        mockMvc.perform(get("/customers/cu/{cu}", "99999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cu", is("99999999")));
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
                .andExpect(jsonPath("$.cu", is("99999999")));
    }

}


