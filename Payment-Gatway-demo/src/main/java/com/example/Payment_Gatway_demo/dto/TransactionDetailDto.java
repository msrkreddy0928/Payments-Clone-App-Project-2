package com.example.Payment_Gatway_demo.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailDto {

    private String status;
    private Long userId;
    private Double amount;
}
