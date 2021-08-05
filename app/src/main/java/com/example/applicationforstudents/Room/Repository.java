package com.example.applicationforstudents.Room;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    LiveData<List<Subject>> liveData;
    Database db;
    public Repository(Application application) {
        db = Database.getDatabase(application);
        liveData = db.getDao().getLiveData();
        //db.getDao().insert(new Subject("Українська мова","Олена Ярославівна","Лекція","10:00-11:20","123А","Купити зошит для робіт","2021.07.29"));
    }

    public LiveData<List<Subject>> getLiveData() {
        return liveData;
    }

    public List<Subject> getSubjectsToDate(String date){
        return db.getDao().getSubjectsToDate(date);
    }
    public void insert(Subject subject){
        db.getDao().insert(subject);
    }
    public void upDate(Subject subject){
        Log.d("MyLog","updaterepository " + subject.getId() + " " + subject.getName());
        db.getDao().upDate(subject);
    }
    public  void deleteForId(Subject subject){
        db.getDao().deleteForId(subject);
    }
    public Subject getElementForId(long id){
        return db.getDao().getElementForId(id);
    }
    public void deleteAll(){
        db.getDao().deleteAll();
    }

}
