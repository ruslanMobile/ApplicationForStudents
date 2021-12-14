package com.example.applicationforstudents.Room;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class Repository {
    Flowable<List<Subject>> liveData;
    Database db;
    public Repository(Application application) {
        db = Database.getDatabase(application);
        liveData = db.getDao().getLiveData();
    }

    public Flowable<List<Subject>> getLiveData() {
        return liveData;
    }

    public Single getSubjectsToDate(String date){
        return db.getDao().getSubjectsToDate(date);
    }
    public Completable insert(Subject subject){
        return db.getDao().insert(subject);
    }
    public Completable upDate(Subject subject){
        return db.getDao().upDate(subject);
    }
    public  Completable deleteForId(Subject subject){
        return db.getDao().deleteForId(subject);
    }
    public Single getElementForId(long id){
        return db.getDao().getElementForId(id);
    }
    public Completable deleteAll(){
        return db.getDao().deleteAll();
    }

}
