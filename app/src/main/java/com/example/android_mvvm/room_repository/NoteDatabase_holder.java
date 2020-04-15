package com.example.android_mvvm.room_repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = Note.class ,version = 1)
public abstract  class NoteDatabase_holder extends RoomDatabase {

    public abstract NoteDataObject noteDataObject();
    private  static volatile NoteDatabase_holder noteDatabase_holder;
    public  static NoteDatabase_holder getDatabase(final Context context)
    {
        if(noteDatabase_holder==null)
        {
            synchronized (NoteDatabase_holder.class){
                if(noteDatabase_holder==null)
                {
                    noteDatabase_holder= Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase_holder.class,"note_database").build();
                }
            }
        }
        return  noteDatabase_holder;
    }
}
