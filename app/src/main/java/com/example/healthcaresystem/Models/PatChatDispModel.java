package com.example.healthcaresystem.Models;

public class PatChatDispModel {
    String username, name, specialist;

    public PatChatDispModel(String username, String name, String specialist) {
        this.username = username;
        this.name = name;
        this.specialist = specialist;
    }

    public PatChatDispModel(String username, String name) {
        this.username = username;
        this.name = name;
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

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }
}
