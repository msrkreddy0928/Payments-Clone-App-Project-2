package com.example.springjpademo.Dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employeedta {
    private String empName;
    private String empEmail;

    private String line1;

    private String line2;

    private String city;

    private Long branchId;


}
