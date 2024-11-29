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

    @RolesAllowed("ADMIN")
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
            return ResponseEntity.badRequest().body("Invalid username/email. Only Alphanumeric and \"@, ., _\" characters are allowed.");
        }


        Date joiningDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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

    @RolesAllowed("ADMIN")
    @GetMapping("/fetch_all")
    public ResponseEntity<?> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<UserInfoBatchResponse> userInfoBatchResponses = new ArrayList<>();
        employees.forEach(employee -> {
            if(!"ROLE_ADMIN".equals(employee.getRole())) {
                Map<String, String> address = new HashMap<>();
                address.put("city", employee.getAddress().split(";")[0]);
                address.put("country", employee.getAddress().split(";")[1]);
                address.put("country_code", employee.getAddress().split(";")[2]);
                userInfoBatchResponses.add(new UserInfoBatchResponse(employee.getId() + 1000, employee.getName(), employee.getUserName(), employee.getPhone(), employee.getRole(), address));
            }
        });
        userInfoBatchResponses.sort(Comparator.comparing(UserInfoBatchResponse::getId));

        return new ResponseEntity<>(userInfoBatchResponses, HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable String id) {
        if (employeeRepository.existsById(Long.parseLong(id))) {
            Employee employee = employeeRepository.findById(Long.parseLong(id));
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }


    @RolesAllowed("ADMIN")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        if (!employeeRepository.existsById(Long.parseLong(id))) {
            employeeRepository.deleteById(Long.parseLong(id));
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

}
