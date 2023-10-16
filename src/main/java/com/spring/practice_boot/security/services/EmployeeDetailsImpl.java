package com.spring.practice_boot.security.services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.practice_boot.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeDetailsImpl implements UserDetails
{
    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String username;

    @JsonIgnore
    private String password;

    private String phone;


    private Collection<? extends GrantedAuthority> authority;

    public EmployeeDetailsImpl(long id, String name, String username, String password, String phone, Collection<? extends GrantedAuthority> authority) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.authority = authority;
    }

    public static EmployeeDetailsImpl build(Employee employee)
    {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole());
        authorityList.add(authority);

        return new EmployeeDetailsImpl(
                employee.getId(),
                employee.getName(),
                employee.getUserName(),
                employee.getPassword(),
                employee.getPhone(),
                authorityList
        );

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authority;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
