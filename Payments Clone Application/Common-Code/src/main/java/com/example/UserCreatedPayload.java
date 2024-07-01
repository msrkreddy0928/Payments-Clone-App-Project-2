package com.example;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedPayload {

    private Long userId;

    private String userName;

    private String userEmail;

    private String requestId;


}
