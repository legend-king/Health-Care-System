package com.example.healthcaresystem.Models;

public class SearchDoctorModel {
    String username,name;

    public int getConsultationCharge() {
        return consultationCharge;
    }

    public void setConsultationCharge(int consultationCharge) {
        this.consultationCharge = consultationCharge;
    }

    int consultationCharge;

    public SearchDoctorModel(String username, String name) {
        this.username = username;
        this.name = name;
    }
    public SearchDoctorModel(String username, String name, int consultationCharge) {
        this.username = username;
        this.name = name;
        this.consultationCharge = consultationCharge;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
