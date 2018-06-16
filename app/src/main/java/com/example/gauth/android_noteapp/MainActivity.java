package com.example.gauth.android_noteapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   static ArrayAdapter arrayAdapter;
    static ListView listView;
    SharedPreferences sharedPreferences;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        sharedPreferences=this.getSharedPreferences("com.example.gauth.android_noteapp", Context.MODE_PRIVATE);
         builder = new AlertDialog.Builder(this);
        try {
            NoteTakingActivity.notes=  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.i("There is","ERROR!!");
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, NoteTakingActivity.notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(getApplicationContext(),NoteTakingActivity.class);
                intent.putExtra("noteNumber",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.i("Menu","LONG CLICKED");

//Delete starts here
                builder.setTitle("Delete the item!!")
                        .setMessage("Do you definately want to do this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("Yes", "Clicked");
                                updateList(i);
                            }
                        }).setCancelable(true)
                        .setNegativeButton("No",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,int which)
                            {
                                Log.i("No","Clicked");
                            }
                        }).setCancelable(true).
                        show();

//Delete ends here
                return true;

            }
        });

    }
    public void updateList(int i)
    {  NoteTakingActivity.notes.remove(i);
        try {
            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(NoteTakingActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            NoteTakingActivity.notes=  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.i("There is","ERROR!!");
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, NoteTakingActivity.notes);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      super.onOptionsItemSelected(item);
   //     notesList.add("new note");
        NoteTakingActivity.notes.add("New Note is...");
        listView.setAdapter(arrayAdapter);

        try {
            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(NoteTakingActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
//New item to list

        try {
            NoteTakingActivity.notes=  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.i("There is","ERROR!!");
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, NoteTakingActivity.notes);
        listView.setAdapter(arrayAdapter);

  //New item to list ends here

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Log.i("Menu","Clicked");

                Intent intent=new Intent(getApplicationContext(),NoteTakingActivity.class);
                intent.putExtra("noteNumber",i);
                startActivity(intent);

            }
        });
        return true;
    }
}