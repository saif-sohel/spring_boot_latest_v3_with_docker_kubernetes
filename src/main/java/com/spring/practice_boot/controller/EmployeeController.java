package com.spring.practice_boot.controller;

import com.spring.practice_boot.model.Employee;
import com.spring.practice_boot.payload.request.EmployeeSignupRequest;
import com.spring.practice_boot.payload.response.MessageResponse;
import com.spring.practice_boot.payload.response.UserInfoBatchResponse;
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@EnableGlobalMethodSecurity(jsr250Enabled = true)
@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = {"http://localhost:5173", "https://employee-info-5r9f.onrender.com"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
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


        Date joiningDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            joiningDate = formatter.parse(employeeSignupRequest.getJoiningDate());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid date format. Please provide date in dd-MM-yyyy format");
        }
        Employee employee = new Employee(employeeSignupRequest.getName(), employeeSignupRequest.getPhone()
                ,employeeSignupRequest.getUsername(), encoder.encode(employeeSignupRequest.getPassword()), employeeSignupRequest.getRole(), employeeSignupRequest.getAddress(), joiningDate);

        employeeRepository.save(employee);


        return ResponseEntity.ok(new MessageResponse("Employee registered successfully!"));
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/fetch_all")
    public ResponseEntity<?> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<UserInfoBatchResponse> userInfoBatchResponses = new ArrayList<>();
        employees.forEach(employee -> {
            Map<String, String> address = new HashMap<>();
            address.put("city", employee.getAddress().split(";")[0]);
            address.put("country", employee.getAddress().split(";")[1]);
            address.put("country_code", employee.getAddress().split(";")[2]);
            userInfoBatchResponses.add(new UserInfoBatchResponse(employee.getId(), employee.getName(), employee.getUserName(), employee.getPhone(), employee.getRole(), address));
        });
        System.out.println(userInfoBatchResponses);
        return new ResponseEntity<>(userInfoBatchResponses, HttpStatus.OK);
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
