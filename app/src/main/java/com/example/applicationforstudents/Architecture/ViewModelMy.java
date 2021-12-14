package com.example.applicationforstudents.Architecture;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.applicationforstudents.Room.Repository;
import com.example.applicationforstudents.Room.Subject;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class ViewModelMy extends AndroidViewModel {
    Flowable<List<Subject>> liveData;
    Repository repository;

    public ViewModelMy(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        liveData = repository.getLiveData();
    }

    public Flowable<List<Subject>> getLiveData(){
        return liveData;
    }
    public Single getSubjectsToDate(String date){
        return repository.getSubjectsToDate(date);
    }

    public Completable insert(Subject subject){
        return repository.insert(subject);
    }
    public Completable upDate(Subject subject){
        return repository.upDate(subject);
    }
    public Completable deleteForId(Subject subject){
       return repository.deleteForId(subject);
    }
    public Single getElementForId(long id){
        return repository.getElementForId(id);
    }
    public Completable deleteAll(){
        return repository.deleteAll();
    }
}