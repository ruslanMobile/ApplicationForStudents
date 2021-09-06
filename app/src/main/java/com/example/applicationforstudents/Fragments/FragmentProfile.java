package com.example.applicationforstudents.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.applicationforstudents.Activity.AboutMeActivity;
import com.example.applicationforstudents.R;
import com.example.applicationforstudents.Activity.SettingsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class FragmentProfile extends Fragment {
    Button buttonAboutMe,buttonList,buttonData,buttonShortText,buttonAboutApp;
    CircleImageView photoProfile;
    TextView nameOfPerson;
    final int SELECT_PICTURE = 200;
    private static final String PREFS_FILE = "Profile";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAboutMe = view.findViewById(R.id.buttonAboutMe);
        buttonAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutMeActivity.class);
                startActivity(intent);
            }
        });

        buttonList = view.findViewById(R.id.buttonList);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        buttonData = view.findViewById(R.id.buttonData);
        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Данный функционал будет добавлен в следующих версиях приложения",Toast.LENGTH_LONG).show();
            }
        });
        buttonShortText = view.findViewById(R.id.buttonShortText);
        buttonShortText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Данный функционал будет добавлен в следующих версиях приложения",Toast.LENGTH_LONG).show();
            }
        });
        buttonAboutApp = view.findViewById(R.id.buttonAboutApp);
        buttonAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Данный функционал будет добавлен в следующих версиях приложения",Toast.LENGTH_LONG).show();
            }
        });
        photoProfile = view.findViewById(R.id.photoProfile);
        photoProfile.setOnClickListener(photoProfileListener);
        nameOfPerson = view.findViewById(R.id.nameOfPerson);
        initPhoto();
    }

    //Отримання імені і прізвища профіля
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_FILE,MODE_PRIVATE);
        String name = preferences.getString("Name","").trim(), surname = preferences.getString("Surname","").trim();
        nameOfPerson.setText(name + " " + surname);
    }

    //Слухач натиску на фото профіля для вибору фото
    View.OnClickListener photoProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
            } else {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
            }
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
    };

    //Ініціалізація фото з пам'яті
    public void initPhoto() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Uri uri = Uri.parse(preferences.getString("image", "null"));
        if (!String.valueOf(uri).equals("null")) {
            //Picasso.get().load(uri).fit().centerCrop().error(R.drawable.ic_emptyphoto).into(photoProfile);
            Glide.with(this).load(uri).centerCrop().fitCenter().into(photoProfile);
        }else {
            photoProfile.setImageResource(R.drawable.ic_emptyphoto);
        }
    }

    //Отримання Uri фотографії і збереження її в пам'ять
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    photoProfile.setImageURI(selectedImageUri);
                    photoProfile.invalidate();

                    getActivity().grantUriPermission(getActivity().getPackageName(), selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getActivity().getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("image", String.valueOf(selectedImageUri));
                    editor.commit();
                }
            }
        }
    }
}