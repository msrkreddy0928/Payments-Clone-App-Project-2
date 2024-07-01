package com.example.Payment_Gatway_demo.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentPageRequest {

    private Long merchantId;

    private Double amount;

    private Long userId;
}
