package com.example.logistics.controller.user;

import com.example.logistics.dto.ResetPasswordRequest;
import com.example.logistics.dto.SignupDto;
import com.example.logistics.dto.SignupResponse;
import com.example.logistics.exception.InvalidEmailException;
import com.example.logistics.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity <SignupResponse> signUp(@RequestBody @Valid SignupDto signupDto) throws InvalidEmailException {
        try {
            SignupResponse response = userService.signUp(signupDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (RuntimeException ex){
            throw new InvalidEmailException(ex.getMessage());
        }
    }

    @PatchMapping("/verify-user/{token}")
    public ResponseEntity<?>verifyAccount(@PathVariable("token") @Valid String token) throws InvalidEmailException {
        userService.verifyUser(token);
        return ResponseEntity.ok("Your account has been successfully verified");
    }
    @PatchMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") @Valid String token, @RequestBody @Valid ResetPasswordRequest passwordRequest) throws InvalidEmailException {
        userService.resetPassword(token,passwordRequest);
        return ResponseEntity.ok("You have successfully changed you password");
    }
    @PostMapping("/forget-password/{email}")
    public ResponseEntity<?>forgetPassword(@PathVariable("email") @Valid String email) throws InvalidEmailException {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Please check you mail for a reset password link");
    }



}
