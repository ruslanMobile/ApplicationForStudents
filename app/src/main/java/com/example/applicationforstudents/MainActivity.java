package com.example.applicationforstudents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.applicationforstudents.Architecture.LifecycleMy;
import com.example.applicationforstudents.Fragments.CustomBottomSheet;
import com.example.applicationforstudents.Fragments.FragmentList;
import com.example.applicationforstudents.Fragments.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity/* implements CustomBottomSheet.ISetListViewListener*/ {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayoutFragment;
    LifecycleMy lifecycle = new LifecycleMy(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(lifecycle);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,new FragmentList()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }
    //Слухач навігації
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
    //Ініціалізація елементів
    public void init(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayoutFragment = findViewById(R.id.fragment);
    }

  /*  @Override
    public void setData() {
        FragmentList fragment = (FragmentList) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        if (fragment != null)
            fragment.resetListView();
    }*/
}