package com.example.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendEmailRequest {
    private final String type = "email";
    private final String from = "skyfitzz11@gmail.com";
    private String message;
    private String subject;
    private String emailAddress;
}
