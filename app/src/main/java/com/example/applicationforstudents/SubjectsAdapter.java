package com.example.applicationforstudents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.QuoteSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applicationforstudents.Room.Subject;

import java.util.List;

public class SubjectsAdapter extends ArrayAdapter<com.example.applicationforstudents.Room.Subject> {
    private LayoutInflater layoutInflater;
    private List<com.example.applicationforstudents.Room.Subject> list;
    private int layout;

    public SubjectsAdapter(Context context, int resource, List<com.example.applicationforstudents.Room.Subject> objects) {
        super(context,resource,objects);
        this.layoutInflater = LayoutInflater.from(context);
        this.layout = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(this.layout, parent, false);
        TextView textClock = view.findViewById(R.id.textClock);
        TextView textTypeOfLesson = view.findViewById(R.id.textTypeOfLesson);
        TextView textNameOfAudience = view.findViewById(R.id.textNameOfAudience);
        TextView textNameOfLesson = view.findViewById(R.id.textNameOfLesson);
        TextView textNote = view.findViewById(R.id.textNote);
        TextView textNameOfTeacher = view.findViewById(R.id.textNameOfTeacher);

        Subject subject = list.get(position);
        textClock.setText(subject.getTime());
        textTypeOfLesson.setText(subject.getType());
        textNameOfAudience.setText(subject.getAudience());
        textNameOfLesson.setText(subject.getName());
        textNameOfTeacher.setText(subject.getTeacher());


        if (subject.getNote().equals("")) {
            textNote.setVisibility(View.GONE);
        } else textNote.setText(subject.getNote());


        return view;
    }
}
