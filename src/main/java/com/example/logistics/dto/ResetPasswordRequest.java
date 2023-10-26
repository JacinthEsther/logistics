package com.example.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
public class ResetPasswordRequest {
    private String password;
    private String confirmPassword;
}
