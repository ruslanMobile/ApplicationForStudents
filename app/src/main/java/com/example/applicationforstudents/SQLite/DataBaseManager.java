package com.example.applicationforstudents.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.applicationforstudents.Subject;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void open() {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
    }

    public ArrayList<Subject> getSubjectsToDate(String date_){
        ArrayList<Subject> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constants.TABLE + " WHERE " + Constants.COLUMN_DATE + "=?",new String[]{String.valueOf(date_)});

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_SUBJECT));
            String teacher = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TEACHER));
            String type = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TYPE_SUBJECT));
            String time = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TIME));
            String audience = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_AUDIENCE));
            String note = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NOTE));
            String date = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_DATE));

            list.add(new Subject(name,teacher,type,time,audience,note,date));
        }
        cursor.close();
        return list;
    }

    public Cursor getCursorForId(long id){
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + Constants.TABLE + " WHERE " + Constants.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return cursor;
    }

    public void insert(String subject, String teacher, String type, String time, String audience, String note, String date) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_NAME_SUBJECT, subject);
        cv.put(Constants.COLUMN_TEACHER, teacher);
        cv.put(Constants.COLUMN_TYPE_SUBJECT, type);
        cv.put(Constants.COLUMN_TIME, time);
        cv.put(Constants.COLUMN_AUDIENCE, audience);
        cv.put(Constants.COLUMN_NOTE, note);
        cv.put(Constants.COLUMN_DATE, date);
        sqLiteDatabase.insert(Constants.TABLE, null, cv);
    }
    public void upDate(String subject, String teacher, String type, String time, String audience, String note, String date,long id){
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_NAME_SUBJECT, subject);
        cv.put(Constants.COLUMN_TEACHER, teacher);
        cv.put(Constants.COLUMN_TYPE_SUBJECT, type);
        cv.put(Constants.COLUMN_TIME, time);
        cv.put(Constants.COLUMN_AUDIENCE, audience);
        cv.put(Constants.COLUMN_NOTE, note);
        cv.put(Constants.COLUMN_DATE, date);
        sqLiteDatabase.update(Constants.TABLE,cv,Constants.COLUMN_ID + "=" + String.valueOf(id),null);
    }
    public void deleteForId(long id){
        sqLiteDatabase.delete(Constants.TABLE,"_id=?", new String[]{String.valueOf(id)});
    }

    public void deleteAll(){
        sqLiteDatabase.delete(Constants.TABLE,null,null);
    }

    public void close() {
        sqLiteDatabase.close();
    }
}
