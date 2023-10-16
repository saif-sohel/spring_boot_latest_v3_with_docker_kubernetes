package com.spring.practice_boot.repository;

import com.spring.practice_boot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
    Optional<Employee> findByUserName(String userName);


    Employee findById(long id);

    Boolean existsByUserName(String userName);

    void deleteById(long id);

    List<Employee> findByRole(String role);



}