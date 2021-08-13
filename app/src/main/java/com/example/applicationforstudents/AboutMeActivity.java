package com.example.applicationforstudents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {
    ImageButton buttonBack,buttonEditText;
    TextView textViewEditGroup,textViewEditCourse,textViewEditDirection,textViewEditStudentCard,textViewEditProfTicket;
    private static final String PREFS_FILE = "Profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        getSupportActionBar().hide();

        textViewEditGroup = findViewById(R.id.textViewEditGroup);
        textViewEditCourse = findViewById(R.id.textViewEditCourse);
        textViewEditDirection = findViewById(R.id.textViewEditDirection);
        textViewEditStudentCard = findViewById(R.id.textViewEditStudentCard);
        textViewEditProfTicket = findViewById(R.id.textViewEditProfTicket);

        buttonBack = findViewById(R.id.buttonBack);
        //Обробка натиску на вихід з актівіті
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonEditText = findViewById(R.id.buttonEditText);
        //Перехід в актівіті для зміни даних студента
        buttonEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditAboutMeActivity.class);
                startActivity(intent);
            }
        });
    }

    //Відображення даних студента з пам'яті
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_FILE,MODE_PRIVATE);
        String str = "";
        textViewEditGroup.setText((str = sharedPreferences.getString("GroupName","Не указано")).trim().equals("") ? "Не указано" : str);
        textViewEditCourse.setText((str = sharedPreferences.getString("CourseName","Не указано")).trim().equals("") ? "Не указано" : str);
        textViewEditDirection.setText((str = sharedPreferences.getString("DirectionName","Не указано")).trim().equals("") ? "Не указано" : str);
        textViewEditStudentCard.setText((str = sharedPreferences.getString("StudentCardName","Не указано")).trim().equals("") ? "Не указано" : str);
        textViewEditProfTicket.setText((str = sharedPreferences.getString("ProfTicketName","Не указано")).trim().equals("") ? "Не указано" : str);
    }

}