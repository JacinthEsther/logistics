package com.example.logistics.user;

import com.example.logistics.model.VerificationToken;
import com.example.logistics.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService{

    private final VerificationTokenRepository tokenRepository;
    @Override
    public VerificationToken createEmailVerificationToken(String email) {
        return generateToken(email);
    }

    @Override
    public VerificationToken createPasswordVerificationToken(String email) {
        return generateToken(email);
    }



    private VerificationToken generateToken(String email){
        VerificationToken token = VerificationToken.builder()
                .timeCreated(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(1L))
                .email(email.toLowerCase())
                .token(UUID.randomUUID().toString())
                .build();
        return tokenRepository.save(token);
    }
}
