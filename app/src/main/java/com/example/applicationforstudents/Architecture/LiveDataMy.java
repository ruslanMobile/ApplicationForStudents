package com.example.applicationforstudents.Architecture;

import androidx.lifecycle.LiveData;

import java.util.Calendar;

public class LiveDataMy extends LiveData {
    public void seValue(Calendar calendar){
        setValue(calendar);
    }
}