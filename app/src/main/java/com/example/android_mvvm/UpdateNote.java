package com.example.android_mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_mvvm.room_repository.Note;
import com.example.android_mvvm.viewmodels.UpdateActivity_viewmodel;
import com.google.android.material.button.MaterialButton;

public class UpdateNote extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText title,desc;
    String priority;
    int rank;
    MaterialButton bt_save,bt_discard;
    LiveData<Note> noteLiveData;
    UpdateActivity_viewmodel updateActivity_viewmodel;
     String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        spinner=findViewById(R.id.spinner_update);
        title=findViewById(R.id.et_title_update);
        desc=findViewById(R.id.et_desc_update);
        bt_save=findViewById(R.id.bt_save_up);
        bt_discard=findViewById(R.id.bt_delete_up);


         String[] items= new String[]{"High","Medium","Low"};
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            id=bundle.getString("id");
        }

        ArrayAdapter<CharSequence> arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,items);
         arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        updateActivity_viewmodel= new ViewModelProvider(this).get(UpdateActivity_viewmodel.class);
        noteLiveData= updateActivity_viewmodel.getnote(id);

        noteLiveData.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                title.setText(note.getTitle());
                desc.setText(note.getDesc());
                setPriority(note.getPriority());
            }
        });


        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result=new Intent();
                result.putExtra("id",id);
                    result.putExtra("title",title.getText().toString().trim());
                    result.putExtra("desc",desc.getText().toString().trim());
                    result.putExtra("priority",rank);
                    setResult(RESULT_OK,result);

                finish();
            }
        });

        bt_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateNote.this, "dicarded", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateNote.this,MainActivity.class));
            }
        });
    }
    void setPriority(int value)
    {
        switch (value)
        {
            //itemsSpinner.getPosition("item2")
            case 1:spinner.setSelection(0);
                    break;
            case 2:spinner.setSelection(1);
                    break;
            case 3:spinner.setSelection(2);
                    break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        priority=parent.getItemAtPosition(position).toString();
        switch(priority)
        {
            case "High":
                rank=1;
                break;
            case "Medium":
                rank=2;
                break;
            case "Low":
                rank=3;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
