package com.example.android_mvvm.room_repository;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDataObject {

    @Insert
    void insert(Note note);

    @Query("select * from myNotes order by priority asc")
    LiveData<List<Note>> getnotes();

    @Query("select * from myNotes where id=:id")
    LiveData<Note> getnote(String  id);

    @Update
    void  update(Note note);

    @Query("delete  from myNotes where id=:id")
    int delete_from_id(String id);


    @Delete
    int delete(Note note);
}
