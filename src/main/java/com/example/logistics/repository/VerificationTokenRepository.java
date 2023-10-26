package com.example.logistics.repository;

import com.example.logistics.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {

    Optional<VerificationToken> findByToken(String token);
     List<VerificationToken> findByExpirationDateBeforeAndTimeUsedNull(LocalDateTime expirationTime);
}
