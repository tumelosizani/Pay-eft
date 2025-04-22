package dev.dini.customerservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idNumber;
    private String address;
}
