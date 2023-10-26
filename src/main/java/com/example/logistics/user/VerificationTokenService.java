package com.example.logistics.user;

import com.example.logistics.model.VerificationToken;

public interface VerificationTokenService {
    VerificationToken createEmailVerificationToken(String email);
    VerificationToken createPasswordVerificationToken(String email);
//    VerificationToken findTokenByToken(String token);
}
