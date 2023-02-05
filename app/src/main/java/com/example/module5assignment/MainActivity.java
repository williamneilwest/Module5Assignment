package com.example.module5assignment;

import android.Manifest;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_WRITE_CODE = 0;
    private static final String TAG = "MainActivity";
    private static final int STORAGE_PERMISSION_CODE = 101;
    TextView text;
    DatabaseHelper mDatabaseHelper;
    private Button btnViewData;
    private Button btnDelete;
    private ListView mListView;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        editText = (EditText) findViewById(R.id.editText);
        mListView = (ListView) findViewById(R.id.listView);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        //btnViewData = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    AddData(newEntry);
                    populateListView();

                    editText.setText("");
                }
                else{
                    toastMessage("You must enter something into the field!");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    DeleteData(newEntry);
                    populateListView();

                    editText.setText("");
                }
                else{
                    toastMessage("You must enter something into the field!");
                }
            }
        });
    }

    public void checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if(insertData) {
            toastMessage("Data Successfully Inserted!");
        }
        else {
            toastMessage("Something went wrong!");
        }

    }
    public void DeleteData(String name){
        boolean deleteData = mDatabaseHelper.deleteData(name);
        if(deleteData) {
            toastMessage("Data Successfully Deleted!");
        }
        else {
            toastMessage("Something went wrong!");
        }

    }



    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Toast.makeText(this, "populateListView: Displaying data in the ListView.", Toast.LENGTH_SHORT).show();


        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listData.sort(String.CASE_INSENSITIVE_ORDER);
        mListView.setAdapter(adapter);
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }

}
