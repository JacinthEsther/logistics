package com.example.logistics.user;


import com.example.logistics.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto)  {
        return ResponseEntity.ok(authService.login(loginDto));
    }

}
