package com.example.conferencepro;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EntrantData.class,ApplicantInfo.class}, version = 1)
public abstract class ApplicantDatabase extends RoomDatabase {
    public abstract AccessDao dao();

    private static volatile ApplicantDatabase INSTANCE;


    static ApplicantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ApplicantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ApplicantDatabase.class, "Entrant_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
