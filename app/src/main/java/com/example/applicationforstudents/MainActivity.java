package com.example.applicationforstudents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.applicationforstudents.Fragments.FragmentList;
import com.example.applicationforstudents.Fragments.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Locale;


public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayoutFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        init();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,new FragmentList()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.fragment_time_table:
                    fragment = new FragmentList();
                    break;
                case R.id.fragment_profile:
                    fragment = new FragmentProfile();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
            return true;
        }
    };
    public void init(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayoutFragment = findViewById(R.id.fragment);
    }
}