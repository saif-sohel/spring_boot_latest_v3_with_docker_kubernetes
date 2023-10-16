package com.spring.practice_boot.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "department")
public class Department {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "department_employees", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "emp_id"))
    private Set<Employee> employees;

    public Department(){}
    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(long id, String name, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
