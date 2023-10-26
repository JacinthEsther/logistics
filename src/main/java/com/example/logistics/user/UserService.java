package com.example.logistics.user;

import com.example.logistics.dto.ResetPasswordRequest;
import com.example.logistics.dto.SignupDto;
import com.example.logistics.dto.SignupResponse;
import com.example.logistics.exception.InvalidEmailException;
import com.example.logistics.model.User;

public interface UserService {
    SignupResponse signUp(SignupDto signupDto) throws InvalidEmailException;
    String verifyUser(String token) throws InvalidEmailException;
    String forgotPassword(String email) throws InvalidEmailException;

    String resetPassword(String token, ResetPasswordRequest passwordRequest) throws InvalidEmailException;

    User loadUser(String email) throws InvalidEmailException;

    void deleteUserByEmail(String mail) throws InvalidEmailException;

    User findById(String userId) throws InvalidEmailException;

    User findUserByEmail(String email) throws InvalidEmailException;
}
