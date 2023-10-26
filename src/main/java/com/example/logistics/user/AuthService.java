package com.example.logistics.user;

import com.example.logistics.dto.AuthenticationResponse;
import com.example.logistics.dto.LoginDto;
import com.example.logistics.exception.InvalidEmailException;


public interface AuthService {
    AuthenticationResponse login(LoginDto loginDto) throws InvalidEmailException;



}
