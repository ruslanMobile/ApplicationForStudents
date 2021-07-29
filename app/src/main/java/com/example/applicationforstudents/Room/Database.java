package com.example.applicationforstudents.Room;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
@androidx.room.Database(entities = {Subject.class},version = 2)
public abstract class Database extends RoomDatabase {
    public abstract SubjectDao getDao();
    public static Database INSTANCE;

    public static Database getDatabase(Context context){
        if(INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "newData").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
