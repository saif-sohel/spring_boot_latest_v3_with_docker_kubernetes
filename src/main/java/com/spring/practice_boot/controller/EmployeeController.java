package com.spring.practice_boot.controller;

import com.spring.practice_boot.model.Employee;
import com.spring.practice_boot.model.EmployeeSignupRequest;
import com.spring.practice_boot.payload.response.MessageResponse;
import com.spring.practice_boot.repository.EmployeeRepository;
import com.spring.practice_boot.security.jwt.JwtUtils;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@EnableGlobalMethodSecurity(jsr250Enabled = true)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    EmployeeRepository employeeRepository;


    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/signup")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody EmployeeSignupRequest employeeSignupRequest)
    {
        if (employeeRepository.existsByUserName(employeeSignupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already exists!"));
        }

        if (StringHelper.isNullOrEmptyString(employeeSignupRequest.getName()))
            return ResponseEntity.badRequest().body("Please provide a valid name");

        if(StringHelper.isNullOrEmptyString(employeeSignupRequest.getUsername())|| StringHelper.isNullOrEmptyString(employeeSignupRequest.getPassword())) {
            return ResponseEntity.badRequest().body("Username or Password cannot be empty");
        }

        Matcher matcher = Pattern.compile("^[a-zA-Z0-9._@]+$").matcher(employeeSignupRequest.getUsername());

        if(!matcher.matches()) {
            return ResponseEntity.badRequest().body("Invalid username. Only Alphanumeric and \"@, ., _\" characters are allowed");
        }

        Employee employee = new Employee(employeeSignupRequest.getName(), employeeSignupRequest.getPhone()
                ,employeeSignupRequest.getUsername(), encoder.encode(employeeSignupRequest.getPassword()), employeeSignupRequest.getRole());

        employeeRepository.save(employee);


        return ResponseEntity.ok(new MessageResponse("Employee registered successfully!"));
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/fetch_all")
    public ResponseEntity<?> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable String id) {
        Employee employee = employeeRepository.findById(Long.parseLong(id));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        employeeRepository.deleteById(Long.parseLong(id));
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }




}
