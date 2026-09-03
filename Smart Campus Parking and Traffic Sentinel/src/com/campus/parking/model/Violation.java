package com.campus.parking.model;

import java.time.LocalDateTime;

public class Violation {

    private int violationId;
    private int userId;
    private int vehicleId;
    private String violationType;
    private String description;
    private double fineAmount;
    private LocalDateTime violationDate;
    private String status;

    public Violation() {
    }

    public int getViolationId() {
        return violationId;
    }

    public void setViolationId(int violationId) {
        this.violationId = violationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public LocalDateTime getViolationDate() {
        return violationDate;
    }

    public void setViolationDate(LocalDateTime violationDate) {
        this.violationDate = violationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}