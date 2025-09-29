package com.acervantes.mcc_customer_service.controller;

import com.acervantes.mcc_customer_service.dto.CustomerDTO;
import com.acervantes.mcc_customer_service.service.interfaces.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CustomerRESTController {

    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(this.customerService.getAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @jakarta.validation.Valid CustomerDTO customerDTO){
        CustomerDTO created = this.customerService.add(customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/customers/id/" + created.getId());
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id){
        return ResponseEntity.ok(this.customerService.getById(id));
    }

    @GetMapping("/cu/{cu}")
    public ResponseEntity<CustomerDTO> getCustomerByCu(@PathVariable String cu){
        return ResponseEntity.ok(this.customerService.getByCu(cu));
    }

    @DeleteMapping("/cu/{cu}")
    public ResponseEntity<Void> deleteByCu(@PathVariable String cu) {
        this.customerService.delete(cu);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody @jakarta.validation.Valid CustomerDTO customerDTO) {
        CustomerDTO updated = this.customerService.update(customerDTO);
        return ResponseEntity.ok(updated);
    }
}
