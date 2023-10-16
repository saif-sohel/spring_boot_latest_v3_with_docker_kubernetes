package com.spring.practice_boot.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Projects {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @Column(name="locale")
    private String locale;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "project_manager", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "project_manager_id"))
    private Employee projectManager;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_devs", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "dev_id"))
    private Set<Employee> developers;


    public Projects() {
    }

    public Projects(long id, String name, int duration, String locale, Date startDate, Employee projectManager, Set<Employee> developers) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.locale = locale;
        this.startDate = startDate;
        this.projectManager = projectManager;
        this.developers = developers;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Employee projectManager) {
        this.projectManager = projectManager;
    }

    public Set<Employee> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Employee> developers) {
        this.developers = developers;
    }
}
