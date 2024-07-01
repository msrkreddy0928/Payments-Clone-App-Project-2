package com.example.Payment_Gatway_demo.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitResponse {
    private String url;
    private String txnId;
}
