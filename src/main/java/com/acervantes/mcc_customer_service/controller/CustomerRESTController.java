package com.acervantes.mcc_customer_service.controller;

import com.acervantes.mcc_customer_service.dto.CustomerDTO;
import com.acervantes.mcc_customer_service.service.interfaces.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerRESTController {

    private ICustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return this.customerService.getAll();
    }

    @PostMapping
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO){
        return this.customerService.add(customerDTO);
    }

    @GetMapping("/id/{id}")
    public CustomerDTO getCustomerById(@PathVariable String id){
        return this.customerService.getById(id);
    }

    @GetMapping("/cu/{cu}")
    public CustomerDTO getCustomerByCu(@PathVariable String cu){
        return this.customerService.getByCu(cu);
    }
}
