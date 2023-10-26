package com.example.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String userId;
    private String email;

    public static AuthenticationResponse of(String jwtToken, String userId, String email){
        return new AuthenticationResponse(jwtToken, userId,  email);
    }
}
