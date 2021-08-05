package com.example.applicationforstudents.Architecture;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.applicationforstudents.Room.Repository;
import com.example.applicationforstudents.Room.Subject;

import java.util.List;

public class ViewModelMy extends AndroidViewModel {
    LiveData<List<Subject>> liveData;
    Repository repository;

    public ViewModelMy(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        liveData = repository.getLiveData();
    }

    public LiveData<List<Subject>> getLiveData(){
        return liveData;
    }
    public List<Subject> getSubjectsToDate(String date){
        return repository.getSubjectsToDate(date);
    }

    public void insert(Subject subject){
        repository.insert(subject);
    }
    public void upDate(Subject subject){
        repository.upDate(subject);
    }
    public  void deleteForId(Subject subject){
       repository.deleteForId(subject);
    }
    public Subject getElementForId(long id){
        return repository.getElementForId(id);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
}