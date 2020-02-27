package com.example.conferencepro;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Entrant Data", foreignKeys = @ForeignKey(entity = ApplicantInfo.class, parentColumns = "userName", childColumns = "name", onDelete = ForeignKey.CASCADE), indices = {@Index("name")})
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

    public EntrantData(String name, int timesVisited, long timeStayed) {
        this.name = name;

        this.timesVisited = timesVisited;
        this.timeStayed = timeStayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    private int timesVisited;
    @ColumnInfo()
    private long timeStayed;
}
