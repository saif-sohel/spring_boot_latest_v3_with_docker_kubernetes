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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
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

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(employeeDetails);

        String jwtToken = jwtCookie.toString().split(";")[0];
        jwtToken = jwtToken.split("=")[1];

        List<String> roles = employeeDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(employeeDetails.getId(),
                        employeeDetails.getName(),
                        employeeDetails.getUsername(),
                        employeeDetails.getPhone(),
                        roles.get(0),
                        jwtToken));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser()
    {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

}

