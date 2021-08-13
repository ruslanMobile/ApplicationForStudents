package com.example.applicationforstudents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditAboutMeActivity extends AppCompatActivity {
    ImageButton buttonBackEdit;
    Button buttonSave;
    SharedPreferences sharedPreferences;
    EditText editTextGroup,editTextCourse,editTextDirection,editTextStudentCard,editTextProfTicket;
    private static final String PREFS_FILE = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_me);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(PREFS_FILE,MODE_PRIVATE);

        editTextGroup = findViewById(R.id.editTextGroup);
        editTextCourse = findViewById(R.id.editTextCourse);
        editTextDirection = findViewById(R.id.editTextDirection);
        editTextStudentCard = findViewById(R.id.editTextStudentCard);
        editTextProfTicket = findViewById(R.id.editTextProfTicket);
        initEditText();

        buttonBackEdit = findViewById(R.id.buttonBackEdit);
        //Обробка натиску на вихід з актівіті
        buttonBackEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Изменения не сохранены",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonSave = findViewById(R.id.buttonSaveAboutMe);
        buttonSave.setOnClickListener(clickButtonSave);
    }
    //Ініціалізація полів для вводу з пам'яті
    public void initEditText(){
        editTextGroup.setText(sharedPreferences.getString("GroupName",""));
        editTextCourse.setText(sharedPreferences.getString("CourseName",""));
        editTextDirection.setText(sharedPreferences.getString("DirectionName",""));
        editTextStudentCard.setText(sharedPreferences.getString("StudentCardName",""));
        editTextProfTicket.setText(sharedPreferences.getString("ProfTicketName",""));
    }

    //Обробка натиску на збереження даних про студента
    View.OnClickListener clickButtonSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor prefEdit = sharedPreferences.edit();
            prefEdit.putString("GroupName",editTextGroup.getText().toString());
            prefEdit.putString("CourseName",editTextCourse.getText().toString());
            prefEdit.putString("DirectionName",editTextDirection.getText().toString());
            prefEdit.putString("StudentCardName",editTextStudentCard.getText().toString());
            prefEdit.putString("ProfTicketName",editTextProfTicket.getText().toString());
            prefEdit.apply();
            Toast.makeText(getApplicationContext(),"Изменения сохранены",Toast.LENGTH_SHORT).show();
            Log.d("MyLog","test");
            finish();
        }
    };
}