package com.example.applicationforstudents.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM subjects WHERE date = :date_")
    Single<List<Subject>> getSubjectsToDate(String date_);

    @Query("SELECT * FROM subjects WHERE id= :id")
    Single<Subject> getElementForId(long id);

    @Insert
    Completable insert(Subject subject);

    @Update
    Completable upDate(Subject subject);

    @Delete
    Completable deleteForId(Subject subject);

    @Query("DELETE FROM subjects")
    Completable deleteAll();

    @Query("SELECT * FROM subjects")
    Flowable<List<Subject>> getLiveData();
}
