package com.example.logistics.service.user;

import com.example.logistics.dto.ResetPasswordRequest;
import com.example.logistics.dto.SignupDto;
import com.example.logistics.dto.SignupResponse;
import com.example.logistics.exception.InvalidEmailException;
import com.example.logistics.model.User;
import com.example.logistics.model.VerificationToken;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;
    private final VerificationTokenService tokenService;
    private final VerificationTokenRepository tokenRepository;


    @Override
    public SignupResponse signUp(SignupDto signupDto) throws InvalidEmailException {
        isValidEmail(signupDto.getEmail());
        isValidPassword(signupDto.getPassword());
        isNameValid(signupDto.getFirstName(), signupDto.getLastName());
        Optional<User> savedUser = userRepository.findUserByEmailIgnoreCase(signupDto.getEmail());
        if (savedUser.isPresent()) {
            throw new InvalidEmailException("User with this email address already exist !!!");
        } else {
            if (signupDto.getPassword().equals(signupDto.getConfirmPassword())) {
                User newUser = new User();

                newUser.setEmail(signupDto.getEmail().toLowerCase());
                newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));

                newUser.setActive(false);
                User saved = userRepository.save(newUser);
                VerificationToken token = tokenService.createEmailVerificationToken(newUser.getEmail().toLowerCase());
//                emailService.sendRegistrationEmail(newUser, token.getToken());
                log.info(token.getToken());
                return SignupResponse.builder()
                        .email(saved.getEmail())

                        .message("User account created successfully. ")
                        .build();
            } else {
                throw new InvalidEmailException("Password does not match");
            }
        }

    }

    @Override
    public String verifyUser(String token) throws InvalidEmailException {
        Optional<VerificationToken> savedToken = tokenRepository.findByToken(token);
        if (savedToken.isPresent()) {
            VerificationToken tk = verifyToken(savedToken.get().getToken());
            if (tk.getToken().equals(savedToken.get().getToken())) {
                Optional<User> savedUser = userRepository.findByEmail(savedToken.get().getEmail());
                if (savedUser.isPresent()) {
                    savedToken.get().setTimeUsed(LocalDateTime.now());
                    savedUser.get().setActive(true);
                    tokenRepository.save(savedToken.get());
                    userRepository.save(savedUser.get());
                    return "Your account has been activated successfully";
                }
                throw new InvalidEmailException("Invalid User");
            }
            throw new InvalidEmailException("Account verification failed!!!");
        }
        throw new InvalidEmailException("Invalid Token");
    }

    @Override
    public String forgotPassword(String email) throws InvalidEmailException {
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());
        if (user.isEmpty()) {
            throw new InvalidEmailException("User not found");
        }

        VerificationToken token = tokenService.createPasswordVerificationToken(email.toLowerCase());
//        emailService.sendPasswordResetEmail(user.get(), token.getToken());

        return "Please check you mail for a reset-password link";
    }

    @Override
    public String resetPassword(String token, ResetPasswordRequest passwordRequest) throws InvalidEmailException {
        Optional<VerificationToken> savedToken = tokenRepository.findByToken(token);
        if (savedToken.isPresent()) {
            VerificationToken tk = verifyToken(savedToken.get().getToken());
            if (tk.getToken().equals(savedToken.get().getToken())) {
                Optional<User> user = userRepository.findByEmail(savedToken.get().getEmail());
                if (user.isPresent()) {
                    isValidPassword(passwordRequest.getPassword());
                    if (passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
                        user.get().setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
                        savedToken.get().setTimeUsed(LocalDateTime.now());
                        tokenRepository.save(savedToken.get());
                        userRepository.save(user.get());
                        return "You have successfully change you password";
                    }
                    throw new InvalidEmailException("Password does not match !!!");
                }
                throw new InvalidEmailException("User does not exist !!!");
            }
            throw new InvalidEmailException("Invalid Token");
        }
        throw new InvalidEmailException("Invalid User");
    }

    @Override
    public User loadUser(String email) throws InvalidEmailException {
        return userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() -> new InvalidEmailException("Bad Credentials"));
    }

    @Override
    public void deleteUserByEmail(String mail) throws InvalidEmailException {
        User user_found = userRepository.findByEmail(mail).orElseThrow(() -> new InvalidEmailException("not found"));
        userRepository.delete(user_found);
    }

    @Override
    public User findById(String userId) throws InvalidEmailException {
        return userRepository.findById(userId).orElseThrow(
                () -> new InvalidEmailException("User not found"));
    }

    @Override
    public User findUserByEmail(String email) throws InvalidEmailException {
        return userRepository.findByEmail(email).orElseThrow(() -> new InvalidEmailException("User cannot be found"));
    }

    private void isValidEmail(String email) throws InvalidEmailException {

        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new InvalidEmailException(email + " is not a valid Email address");
        }
        ;
    }

    private void isValidPassword(String password) throws  InvalidEmailException {
//        password must have at least 8 characters, 1 upper case, 1 number, 1 special character
//        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$");
        if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~@$!%^(#){/}*?&])[A-Za-z\\d@~$!%^(#){/}*?&]{8,}")) {
            throw new InvalidEmailException("Invalid password: Password must have at least 8 characters, 1 upper case, 1 number, 1 special character");
        }
    }

    private void isNameValid(String firstname, String lastname) throws InvalidEmailException {
        if (!firstname.matches("^[A-Za-z-']{2,30}$") || !lastname.matches("^[A-Za-z-']{2,30}$"))
            throw new InvalidEmailException("Invalid Firstname or Lastname");
    }

    private VerificationToken verifyToken(String token) throws  InvalidEmailException {

        Optional<VerificationToken> savedToken = tokenRepository.findByToken(token);
        if (savedToken.isPresent()) {
            if (savedToken.get().getTimeUsed() != null) {
                throw new InvalidEmailException("This token has been used !!!");
            }
            if (savedToken.get().getExpirationDate().isBefore(LocalDateTime.now())) {
                throw new InvalidEmailException("This token has expired !!!");
            }
            return savedToken.get();
        }
        throw new InvalidEmailException("Invalid Token");
    }
}
