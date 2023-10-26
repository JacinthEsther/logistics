package com.example.logistics.user;

import com.example.logistics.config.SecureUser;
import com.example.logistics.dto.AuthenticationResponse;
import com.example.logistics.dto.LoginDto;
import com.example.logistics.exception.InvalidEmailException;
import com.example.logistics.model.User;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.repository.VerificationTokenRepository;
import com.example.logistics.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.InvalidModuleDescriptorException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final VerificationTokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public AuthenticationResponse login(LoginDto loginDto) throws InvalidEmailException {
        Optional<User>savedUser = userRepository.findByEmail(loginDto.getEmail().toLowerCase());
        if (savedUser.isPresent()){
            if (savedUser.get().isActive()){
                try {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginDto.getEmail().toLowerCase(), loginDto.getPassword())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (BadCredentialsException e) {
                    throw new InvalidEmailException(e.getLocalizedMessage());
                }
                User foundUser = userRepository.findByEmail(loginDto.getEmail().toLowerCase()).orElseThrow(() -> new InvalidModuleDescriptorException("user not found"));
                SecureUser user = new SecureUser(foundUser);
                String jwtToken = jwtService.generateToken(user);
//                String fullName = foundUser.getFirstname() + " " + foundUser.getLastname();
                return AuthenticationResponse.of(jwtToken, user.getUserId(),  foundUser.getEmail());
            }
            throw new InvalidEmailException("Your account has not been verified !!!");
        }
        throw new InvalidEmailException("User not found!!!");
    }
}
