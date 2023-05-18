package com.example.ambulancesystem.Models;


public class ETAResponse {
    private String estimatedTime;

    public ETAResponse(String updatedTime) {
        this.estimatedTime = updatedTime;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

}
