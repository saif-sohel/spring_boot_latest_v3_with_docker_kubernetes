package com.spring.practice_boot.security.services;

import com.spring.practice_boot.model.Employee;
import com.spring.practice_boot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService
{
    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Employee employee = employeeRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username:" + username));

        return EmployeeDetailsImpl.build(employee);
    }

}