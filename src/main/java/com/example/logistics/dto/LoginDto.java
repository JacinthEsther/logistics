package com.example.logistics.dto;


import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class LoginDto {
    @Email
    private String email;
    private String password;
}
