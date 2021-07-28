package com.example.applicationforstudents.Architecture;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public class ViewModelMy extends ViewModel {
    //LiveDataMy liveData = new LiveDataMy();
    MutableLiveData liveData = new MutableLiveData();

    public LiveData<Calendar> getLiveData() {
        return liveData;
    }
    public void setData(Calendar calendar){
        //liveData.seValue(calendar);
        liveData.setValue(calendar);
    }
}