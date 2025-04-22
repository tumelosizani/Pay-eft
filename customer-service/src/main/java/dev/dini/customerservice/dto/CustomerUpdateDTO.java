package dev.dini.customerservice.dto;

import lombok.Data;

@Data
public class CustomerUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idNumber;
    private String address;
}
