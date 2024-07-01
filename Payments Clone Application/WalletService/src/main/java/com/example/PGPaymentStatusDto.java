package com.example;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PGPaymentStatusDto {
    private String status;
    private Long userId;
    private Double amount;
}
