package com.example;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletUpdatePayload {
    private String userEmail;

    private Double balance;

    private String requestId;
}
