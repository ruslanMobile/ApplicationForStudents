package com.example.applicationforstudents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.QuoteSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.applicationforstudents.Architecture.ViewModelMy;
import com.example.applicationforstudents.Fragments.CustomBottomSheet;
import com.example.applicationforstudents.Fragments.FragmentList;
import com.example.applicationforstudents.Room.Subject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubjectsAdapter extends ArrayAdapter<com.example.applicationforstudents.Room.Subject> {
    private LayoutInflater layoutInflater;
    private List<com.example.applicationforstudents.Room.Subject> list;
    private int layout;
    Subject subject;

    public SubjectsAdapter(Context context, int resource, List<com.example.applicationforstudents.Room.Subject> objects/*, Date date, FragmentManager fragmentManager*/) {
        super(context,resource,objects);
        this.layoutInflater = LayoutInflater.from(context);
        this.layout = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(this.layout,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        subject = list.get(position);

        viewHolder.textClock.setText(subject.getTime());
        viewHolder.textTypeOfLesson.setText(subject.getType());
        viewHolder.textNameOfAudience.setText(subject.getAudience());
        viewHolder.textNameOfLesson.setText(subject.getName());
        viewHolder.textNameOfTeacher.setText(subject.getTeacher());
        viewHolder.textViewId.setText(String.valueOf(subject.getId()));

        if (subject.getNote().equals("")) {
            viewHolder.textNote.setVisibility(View.GONE);
        } else viewHolder.textNote.setText(subject.getNote());

        //Log.d("MyLog","pos: " +position + " size: " + list.size());
        return convertView;
    }

    private class ViewHolder {
        final TextView textClock,textTypeOfLesson,textNameOfAudience,textNameOfLesson,textNote,textNameOfTeacher,textViewId;
        ViewHolder(View view){
            textClock = view.findViewById(R.id.textClock);
            textTypeOfLesson = view.findViewById(R.id.textTypeOfLesson);
            textNameOfAudience = view.findViewById(R.id.textNameOfAudience);
            textNameOfLesson = view.findViewById(R.id.textNameOfLesson);
            textNote = view.findViewById(R.id.textNote);
            textNameOfTeacher = view.findViewById(R.id.textNameOfTeacher);
            textViewId = view.findViewById(R.id.textViewId);
        }

    }
}
