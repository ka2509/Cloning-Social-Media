package com.example.newproject.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String jwt;
    private boolean status;
}
