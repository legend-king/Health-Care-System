package com.example.healthcaresystem.Models;

public class PhysicalActivityModel {
    String category, activity;

    public PhysicalActivityModel(String category, String activity) {
        this.category = category;
        this.activity = activity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
