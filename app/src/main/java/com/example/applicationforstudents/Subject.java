package com.example.applicationforstudents;

import java.io.Serializable;

public class Subject   {
    private String name,teacher,type,time,audience,note,date;

    public Subject(String name, String teacher, String type, String time, String audience, String note, String date) {
        this.name = name;
        this.teacher = teacher;
        this.type = type;
        this.time = time;
        this.audience = audience;
        this.note = note;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", audience='" + audience + '\'' +
                ", note='" + note + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getAudience() {
        return audience;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
