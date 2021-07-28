package com.example.applicationforstudents.Architecture;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.applicationforstudents.Room.Repository;
import com.example.applicationforstudents.Room.Subject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewModelMy extends AndroidViewModel {
    //LiveDataMy liveData = new LiveDataMy();

    /*MutableLiveData liveData = new MutableLiveData();

    public LiveData<Calendar> getLiveData() {
        return liveData;
    }
    public void setData(Calendar calendar){
        //liveData.seValue(calendar);
        liveData.setValue(calendar);
    }*/
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
}