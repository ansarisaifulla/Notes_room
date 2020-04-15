package com.example.android_mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class Add_Note extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    EditText title,desc;
    String priority;
    int rank;
    MaterialButton bt_save,bt_discard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__note);
        spinner=findViewById(R.id.spinner);
        title=findViewById(R.id.et_title);
        desc=findViewById(R.id.et_decs);
        bt_save=findViewById(R.id.bt_save);
        bt_discard=findViewById(R.id.bt_delete);


        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.spinner,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result=new Intent();
                if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(desc.getText()))
                {
                    setResult(RESULT_CANCELED,result);
                }
                else
                {
                    result.putExtra("title",title.getText().toString().trim());
                    result.putExtra("desc",desc.getText().toString().trim());
                    result.putExtra("priority",rank);
                    setResult(RESULT_OK,result);
                }
                finish();
            }
        });

        bt_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add_Note.this, "discarded", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Add_Note.this,MainActivity.class));
            }
        });
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
