package com.example.conferencepro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("userName")})
public class ApplicantInfo {
    @PrimaryKey()
    @ColumnInfo
    @NonNull
    private String userName;
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

    public ApplicantInfo(String userName,String email, String number, String educationLevel, String currentRole, String company, String linkedIn) {
        this.userName=userName;
        this.email = email;
        this.number = number;
        this.educationLevel = educationLevel;
        this.currentRole = currentRole;
        this.company = company;
        this.linkedIn = linkedIn;
    }

    @ColumnInfo()
    private String linkedIn;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
