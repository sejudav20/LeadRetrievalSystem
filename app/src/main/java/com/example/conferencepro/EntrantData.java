package com.example.conferencepro;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Entrant Data",foreignKeys = @ForeignKey(entity = ApplicantInfo.class,parentColumns = "userId",childColumns = "userData",onDelete = ForeignKey.CASCADE),indices = {@Index("userData")})
public class EntrantData {
   @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo()
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntrantData(String name, int userData, int timesVisited, int timeStayed) {
        this.name = name;
        this.userData = userData;
        this.timesVisited = timesVisited;
        this.timeStayed = timeStayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserData() {
        return userData;
    }

    public void setUserData(int userData) {
        this.userData = userData;
    }

    public int getTimesVisited() {
        return timesVisited;
    }

    public void setTimesVisited(int timesVisited) {
        this.timesVisited = timesVisited;
    }

    public long getTimeStayed() {
        return timeStayed;
    }

    public void setTimeStayed(long timeStayed) {
        this.timeStayed = timeStayed;
    }

    @ColumnInfo()
    private int userData;

    @ColumnInfo()
    private int timesVisited;
   @ColumnInfo()
    private long timeStayed;
}
