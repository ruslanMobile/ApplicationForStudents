package com.example.applicationforstudents.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.applicationforstudents.AboutMeActivity;
import com.example.applicationforstudents.R;

public class FragmentProfile extends Fragment {
    Button buttonAboutMe,buttonList,buttonData,buttonShortText,buttonAboutApp;
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
                Intent intent = new Intent(getActivity(),AboutMeActivity.class);
                startActivity(intent);
            }
        });
        buttonList = view.findViewById(R.id.buttonList);
        buttonData = view.findViewById(R.id.buttonData);
        buttonShortText = view.findViewById(R.id.buttonShortText);
        buttonAboutApp = view.findViewById(R.id.buttonAboutApp);
    }
}
