package com.spring.practice_boot.payload.request;

public class CreateProjectRequest {
    private String name;
    private String description;
    private String startDate;
    private String duration;
    private String locale;


    public CreateProjectRequest() {
    }

    public CreateProjectRequest(String name, String description, String startDate, String duration, String locale) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocale() {
        return locale;
    }


}
