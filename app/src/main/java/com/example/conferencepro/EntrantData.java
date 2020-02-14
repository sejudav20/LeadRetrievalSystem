package com.example.conferencepro;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Entrant Data", foreignKeys = @ForeignKey(entity = CandidateData.class, parentColumns = "id",childColumns = "userKey", onDelete = ForeignKey.CASCADE))

public class EntrantData {
@PrimaryKey(autoGenerate = true)
    private int id;
    private int name;
    private int userKey;

    public EntrantData(int name, int userKey, int timeStayed, boolean askedQuestions) {
        this.name = name;
        this.userKey = userKey;
        this.timeStayed = timeStayed;
        this.askedQuestions = askedQuestions;
    }

    private int timeStayed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
    }

    public int getTimeStayed() {
        return timeStayed;
    }

    public void setTimeStayed(int timeStayed) {
        this.timeStayed = timeStayed;
    }

    public boolean isAskedQuestions() {
        return askedQuestions;
    }

    public void setAskedQuestions(boolean askedQuestions) {
        this.askedQuestions = askedQuestions;
    }

    private boolean askedQuestions;


}
