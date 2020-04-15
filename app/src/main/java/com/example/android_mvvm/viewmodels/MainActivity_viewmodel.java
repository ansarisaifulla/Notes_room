package com.example.android_mvvm.viewmodels;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android_mvvm.room_repository.Note;
import com.example.android_mvvm.room_repository.NoteDataObject;
import com.example.android_mvvm.room_repository.NoteDatabase_holder;

import java.util.List;

public class MainActivity_viewmodel extends AndroidViewModel {

    private NoteDataObject noteDataObject;
    private NoteDatabase_holder db;
    private LiveData<List<Note>> notes;


    public MainActivity_viewmodel(@NonNull Application application) {
        super(application);
        db=NoteDatabase_holder.getDatabase(application);
        noteDataObject=db.noteDataObject();
        notes=noteDataObject.getnotes();
    }

    public void insert(Note note)
    {
        new insertAsyncTask(noteDataObject).execute(note);
    }
    public LiveData<List<Note>> getAllData(){
        return  notes;
    }
    public void update(Note note)
    {
        new updateAsyncTask(noteDataObject).execute(note);
    }
    public void delete(Note note)
    {
        new deleteAsyncTask(noteDataObject).execute(note);
    }

    class updateAsyncTask extends AsyncTask<Note,Void,Void>{

        NoteDataObject noa;
        public updateAsyncTask(NoteDataObject noa)
        {
            this.noa=noa;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noa.update(notes[0]);
            return null;
        }
    }

    class deleteAsyncTask extends AsyncTask<Note,Void,Void>{
        NoteDataObject noa;
        public deleteAsyncTask(NoteDataObject noa)
        {
            this.noa=noa;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noa.delete(notes[0]);
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(this.getClass().getSimpleName().toString(),"viewmodel destroyed");
    }

    class insertAsyncTask extends AsyncTask<Note ,Void,Void>{

        NoteDataObject noa;
        public insertAsyncTask(NoteDataObject noa)
        {
            this.noa=noa;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noa.insert(notes[0]);
            return null;
        }
    }
}
