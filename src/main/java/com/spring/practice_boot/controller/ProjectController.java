package com.spring.practice_boot.controller;


import com.spring.practice_boot.model.Employee;
import com.spring.practice_boot.model.Projects;
import com.spring.practice_boot.payload.request.AddDevelopersRequest;
import com.spring.practice_boot.payload.request.AddProjectManagerRequest;
import com.spring.practice_boot.payload.request.CreateProjectRequest;
import com.spring.practice_boot.repository.EmployeeRepository;
import com.spring.practice_boot.repository.ProjectRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    ProjectRepository projectRepository;
    EmployeeRepository employeeRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/create")
    public ResponseEntity<?> createProject(@Valid @RequestBody CreateProjectRequest createProjectRequest) {
        if(projectRepository.existsByName(createProjectRequest.getName())) {
            return ResponseEntity.badRequest().body("Project with this name already exists");
        }

        SimpleDateFormat dateTimeFormatter = new  SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
           date  = dateTimeFormatter.parse(createProjectRequest.getStartDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Projects project = new Projects(createProjectRequest.getName(), createProjectRequest.getDescription(), date, createProjectRequest.getDuration(), createProjectRequest.getLocale());
        projectRepository.save(project);
        return ResponseEntity.ok("Project created successfully");
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/add-developer")
    public ResponseEntity<?> addDeveloperToProject(@Valid @RequestBody AddDevelopersRequest addDevelopersRequest) {

        if(!projectRepository.existsById(Long.parseLong(addDevelopersRequest.getProjectId()))) {
            return ResponseEntity.badRequest().body("This Project does not exist");
        }
        Projects project = projectRepository.findById(Long.parseLong(addDevelopersRequest.getProjectId()));
        Set<Employee> developers = project.getDevelopers();

        for (String id : addDevelopersRequest.getDeveloperIds()) {
            Employee developer = employeeRepository.findById(Long.parseLong(id));
            if (developer == null) {
                return ResponseEntity.badRequest().body("Developer with id " + id + " does not exist");
            }
            developers.add(developer);
        }
        project.setDevelopers(developers);
        projectRepository.save(project);

        return ResponseEntity.ok("Developer added successfully");
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/assign-manager")
    public ResponseEntity<?> assignManagerToProject(@Valid @RequestBody AddProjectManagerRequest addDevelopersRequest) {

        if(!projectRepository.existsById(Long.parseLong(addDevelopersRequest.getProjectId()))) {
            return ResponseEntity.badRequest().body("This Project does not exist");
        }
        Projects project = projectRepository.findById(Long.parseLong(addDevelopersRequest.getProjectId()));
        Employee manager = employeeRepository.findById(Long.parseLong(addDevelopersRequest.getProjectManagerId()));
        if (manager == null) {
            return ResponseEntity.badRequest().body("Manager with id " + addDevelopersRequest.getProjectManagerId() + " does not exist");
        }
        project.setProjectManager(manager);
        projectRepository.save(project);
        return ResponseEntity.ok("Manager assigned successfully");
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/fetch_all")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProject(@PathVariable String id) {
        Projects project = projectRepository.findById(Long.parseLong(id));
        return ResponseEntity.ok(project);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/get_by_manager/{id}")
    public ResponseEntity<?> getProjectByManager(@PathVariable String id) {
        return ResponseEntity.ok(projectRepository.findByProjectManagerId(Long.parseLong(id)));
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        projectRepository.deleteById(Long.parseLong(id));
        return ResponseEntity.ok("Deleted Successfully");
    }
}
