package com.example.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private String accessToken;
    private String userId;


    public static LoginResponse of(String jwtToken, String userId){
        return new LoginResponse(jwtToken,userId);
    }
}
