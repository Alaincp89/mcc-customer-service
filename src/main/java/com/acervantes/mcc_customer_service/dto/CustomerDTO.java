package com.acervantes.mcc_customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String id;
    @NotBlank
    @Size(max = 20)
    private String cu;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 150)
    private String address;
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
    @NotBlank
    @Size(max = 30)
    private String mobile;


}
