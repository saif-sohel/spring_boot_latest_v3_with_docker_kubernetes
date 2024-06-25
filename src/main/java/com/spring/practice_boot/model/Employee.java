package com.spring.practice_boot.model;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "username")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Column(name="address")
    private String address;

    @Column(name = "joining_date")
    private Date joiningDate;



    public Employee() {
    }

    public Employee(String name, String phone, String userName, String password, String role, String address, Date joiningDate) {
        this.name = name;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.address = address;
        this.joiningDate = joiningDate;
    }

    public Employee(long id, String name, String phone, String userName, String password, String role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String email) {
        this.userName = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }
    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
}
