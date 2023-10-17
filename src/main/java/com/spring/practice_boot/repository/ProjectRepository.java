package com.spring.practice_boot.repository;

import com.spring.practice_boot.model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Projects, Long> {
    boolean existsByName(String name);
    Projects findById(long id);
    boolean existsById(long id);
    List<Projects> findByProjectManagerId(long id);
    List<Projects> findByDevelopersId(long id);
    void deleteById(long id);
}
