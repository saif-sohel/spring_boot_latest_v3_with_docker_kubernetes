package com.spring.practice_boot.payload.request;

import java.util.Set;

public class AddDevelopersRequest {
    private String projectId;
    private Set<String> developerIds;

    public AddDevelopersRequest() {
    }
    public AddDevelopersRequest(String projectId, Set<String> developerIds) {
        this.projectId = projectId;
        this.developerIds = developerIds;
    }
    public String getProjectId() {
        return projectId;
    }
    public Set<String> getDeveloperIds() {
        return developerIds;
    }
}
