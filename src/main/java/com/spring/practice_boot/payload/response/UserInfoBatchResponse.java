package com.spring.practice_boot.payload.response;

import java.util.Map;

public class UserInfoBatchResponse {
    private Long id;
    private String name;
    private String username;
    private String phone;
    private String role;
    private Map<String, String> address;

    public UserInfoBatchResponse(Long id, String name, String username, String phone, String role, Map<String, String> address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.role = role;
        this.address = address;
    }
    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }


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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
