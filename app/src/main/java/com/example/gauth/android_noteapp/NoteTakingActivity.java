package com.example.gauth.android_noteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;

public class NoteTakingActivity extends AppCompatActivity {
    static ArrayList<String> notes=new ArrayList<>();
    EditText takeNotes;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);
        takeNotes=(EditText)findViewById(R.id.editText);
         sharedPreferences=this.getSharedPreferences("com.example.gauth.android_noteapp", Context.MODE_PRIVATE);

        Intent intent = getIntent();
     try {
         notes=  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
     }
     catch (Exception e)
     {
         e.printStackTrace();
         Log.i("There is","ERROR!!");
     }
        takeNotes.setText(notes.get(intent.getIntExtra("noteNumber",0)));
        Log.i("Number of notes",Integer.toString(notes.size()));  //number of notes
    }
public void saveChanges(View view)
{
    Intent intent = getIntent();
    int i=intent.getIntExtra("noteNumber",0);
    notes.set(i,takeNotes.getText().toString());
    //notes.add(i,takeNotes.getText().toString());
    try {
        sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(NoteTakingActivity.notes)).apply();
    } catch (IOException e) {
        e.printStackTrace();
    }

   MainActivity.arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,NoteTakingActivity.notes);
    MainActivity.listView.setAdapter(MainActivity.arrayAdapter);
    Log.i("Serial No",Integer.toString(i));
}
}
