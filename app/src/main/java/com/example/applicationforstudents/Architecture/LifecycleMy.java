package com.example.applicationforstudents.Architecture;

import android.content.Context;
import android.content.res.Configuration;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.Locale;

public class LifecycleMy implements LifecycleObserver {
    Context context;
    public LifecycleMy(Context applicationContext) {
        this.context = applicationContext;
    }

    @OnLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_CREATE)
    public void locale(){
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }
}