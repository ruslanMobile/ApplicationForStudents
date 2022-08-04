package com.example.applicationforstudents.SQLite;

public class Constants {
    //DB Settings
    public static final String DB_NAME = "myDataBase.db";
    public static final String TABLE = "subjects";
    public static final int VERSION_DB = 1;
    //DB Column
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_SUBJECT = "nameOfSubject";
    public static final String COLUMN_TEACHER = "teacher";
    public static final String COLUMN_TYPE_SUBJECT = "typeOfSubject";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_AUDIENCE = "audience";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_DATE = "date";
    //DB Commands
    public static final String STRUCT = "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME_SUBJECT + " TEXT, " + COLUMN_TEACHER + " TEXT, " + COLUMN_TYPE_SUBJECT + " TEXT, "
            + COLUMN_TIME + " TEXT, " + COLUMN_AUDIENCE + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_DATE + " TEXT)";
    public static final String DELETE = "DROP TABLE IF EXISTS " + TABLE;
}
