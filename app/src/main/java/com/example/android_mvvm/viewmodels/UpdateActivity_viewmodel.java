package com.example.android_mvvm.viewmodels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android_mvvm.room_repository.Note;
import com.example.android_mvvm.room_repository.NoteDataObject;
import com.example.android_mvvm.room_repository.NoteDatabase_holder;

public class UpdateActivity_viewmodel extends AndroidViewModel {

    private NoteDataObject noteDataObject;
    private NoteDatabase_holder db;
    public UpdateActivity_viewmodel(@NonNull Application application) {
        super(application);
        db=NoteDatabase_holder.getDatabase(application);
        noteDataObject=db.noteDataObject();

    }


    public LiveData<Note> getnote(String id)
    {
        return noteDataObject.getnote(id);
    }

}
