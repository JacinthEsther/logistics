package com.example.logistics.model;

import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class VerificationToken {
    @Id
    private String tokenId;
    private String email;
    private String token;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUsed;
    private LocalDateTime expirationDate;

}
