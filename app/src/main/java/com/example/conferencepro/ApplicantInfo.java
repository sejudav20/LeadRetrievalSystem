package com.example.conferencepro;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("userId")})
public class ApplicantInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private int userId;
    @ColumnInfo()
    private String email;
    @ColumnInfo()
    private String number;
    @ColumnInfo()
    private String educationLevel;
    @ColumnInfo()
    private String currentRole;
    @ColumnInfo()
    private String company;

    public ApplicantInfo(String email, String number, String educationLevel, String currentRole, String company, String linkedIn) {
        this.email = email;
        this.number = number;
        this.educationLevel = educationLevel;
        this.currentRole = currentRole;
        this.company = company;
        this.linkedIn = linkedIn;
    }

    @ColumnInfo()
    private String linkedIn;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId= id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
}
