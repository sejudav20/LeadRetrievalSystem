package com.example.conferencepro;

import android.provider.ContactsContract;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Candidates")
public class CandidateData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String company;
    private String number;
    private String email;
    private String currentRole;
    private String educationLevel;

    public CandidateData(String name, String company, String number, String email, String currentRole, String educationLevel, String linkedInUrl) {
        this.name = name;
        this.company = company;
        this.number = number;
        this.email = email;
        this.currentRole = currentRole;
        this.educationLevel = educationLevel;
        this.linkedInUrl = linkedInUrl;
    }

    private String linkedInUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }
}
