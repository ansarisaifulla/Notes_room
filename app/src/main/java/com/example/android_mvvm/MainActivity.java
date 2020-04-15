package com.example.android_mvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android_mvvm.room_repository.Note;
import com.example.android_mvvm.viewmodels.MainActivity_viewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnDeleteListener {

    private MainActivity_viewmodel mainActivity_viewmodel;
    RecyclerView recyclerView;
    static  final int ADD_NOTE_REQUEST=1,UPDATE_NOTE_REQUEST=2;
    FloatingActionButton floatingActionButton;
    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rcv);
        floatingActionButton=findViewById(R.id.float_bt);
        noteAdapter=new NoteAdapter(this,this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Add_Note.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        mainActivity_viewmodel=new ViewModelProvider(this).get(MainActivity_viewmodel.class);
        mainActivity_viewmodel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNoteList(notes);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK)
        {
            final String id= UUID.randomUUID().toString();
            Note note=new Note(id,data.getStringExtra("title"),
                                data.getStringExtra("desc"),
                                data.getIntExtra("priority",3));
            mainActivity_viewmodel.insert(note);
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==UPDATE_NOTE_REQUEST && resultCode==RESULT_OK)
        {
            Note note=new Note(data.getStringExtra("id"),data.getStringExtra("title"),
                    data.getStringExtra("desc"),
                    data.getIntExtra("priority",1));
            mainActivity_viewmodel.update(note);
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void DeleteListener(Note note) {
            mainActivity_viewmodel.delete(note);
    }

}
