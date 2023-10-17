package com.spring.practice_boot.payload.request;

public class AddProjectManagerRequest {
    public String projectId;
    public String projectManagerId;

    public AddProjectManagerRequest() {
    }
    public AddProjectManagerRequest(String projectId, String projectManagerId) {
        this.projectId = projectId;
        this.projectManagerId = projectManagerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }
}
