package com.example.applicationforstudents.Room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    LiveData<List<Subject>> liveData;
    Database db;
    public Repository(Application application) {
        db = Database.getDatabase(application);
        liveData = db.getDao().getLiveData();
    }

    public LiveData<List<Subject>> getLiveData(){
        return liveData;
    }

    public List<Subject> getSubjectsToDate(String date){
        return db.getDao().getSubjectsToDate(date);
    }
}
