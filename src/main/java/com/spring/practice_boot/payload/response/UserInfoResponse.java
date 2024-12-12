package com.spring.practice_boot.payload.response;

import java.util.List;

public class UserInfoResponse
{
    private Long id;
    private String name;
    private String username;
    private String phone;
    private List<String> roles;
    private String jwtToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRole() {
        return roles;
    }

    public void setRole(List<String> roles) {
        this.roles = roles;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public UserInfoResponse(Long id, String name, String username, String phone, List<String> roles, String jwtToken)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }
}
