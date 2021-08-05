package com.example.applicationforstudents.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM subjects WHERE date = :date_")
    List<Subject> getSubjectsToDate(String date_);

    @Query("SELECT * FROM subjects WHERE id= :id")
    Subject getElementForId(long id);

    @Insert
    void insert(Subject subject);

    @Update
    void upDate(Subject subject);

    @Delete
    void deleteForId(Subject subject);

    @Query("DELETE FROM subjects")
    void deleteAll();

    @Query("SELECT * FROM subjects")
    LiveData<List<Subject>> getLiveData();
}
