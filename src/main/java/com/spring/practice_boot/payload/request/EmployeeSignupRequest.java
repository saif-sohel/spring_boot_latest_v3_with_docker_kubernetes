package com.spring.practice_boot.payload.request;

public class EmployeeSignupRequest {
    private String name;
    private String username;
    private String password;
    private String phone;
    private String role;
    private String address;
    private String joiningDate;

    public EmployeeSignupRequest() {
    }

    public EmployeeSignupRequest(String name, String username, String password, String phone, String role, String address, String joiningDate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.address = address;
        this.joiningDate = joiningDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoiningDate() {
        return joiningDate;
    }
    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }
}
