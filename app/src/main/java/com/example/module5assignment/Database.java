package com.example.module5assignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Database extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    private Button btnAdd;
    private EditText editText;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        editText = (EditText) findViewById(R.id.editText);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                Log.d(TAG, "populateListView: Displaying data in the ListView.");

                if (editText.length() != 0) {
                    Log.d(TAG, "populateListView: Displaying data in the ListView.");


                    AddData(newEntry);
                    populateListView();
                    toastMessage("List Populated");
                    editText.setText("");
                } else {
                    toastMessage("You must enter something into the field!");
                }
            }
        });


    }

    public void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Toast.makeText(this, "populateListView: Displaying data in the ListView.", Toast.LENGTH_SHORT).show();


        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if(insertData) {
            toastMessage("Data Successfully Inserted!");}
        else {
            toastMessage("Something went wrong!");
        }

    }


}
