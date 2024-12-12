package com.spring.practice_boot.controller;

import com.spring.practice_boot.payload.request.LoginRequest;
import com.spring.practice_boot.payload.response.MessageResponse;
import com.spring.practice_boot.payload.response.UserInfoResponse;
import com.spring.practice_boot.repository.EmployeeRepository;
import com.spring.practice_boot.security.jwt.JwtUtils;
import com.spring.practice_boot.security.services.EmployeeDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    AuthenticationManager authenticationManager;
    
    EmployeeRepository employeeRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, EmployeeRepository employeeRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(employeeDetails.getUsername());

        List<String> roles = employeeDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_PREFIX + jwtToken)
                .body(new UserInfoResponse(employeeDetails.getId(),
                        employeeDetails.getName(),
                        employeeDetails.getUsername(),
                        employeeDetails.getPhone(),
                        roles,
                        jwtToken));
    }
    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok(new MessageResponse("You've been signed out!"));
    }


    @GetMapping("/test")
    public String test(@RequestHeader("Origin") String origin) {
        System.out.println("Origin: " + origin);
        return "Origin: " + origin;
    }

}

