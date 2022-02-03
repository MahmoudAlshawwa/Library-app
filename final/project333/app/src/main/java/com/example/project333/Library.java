package com.example.project333;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project333.adapter.catagoryAdapter;
import com.example.project333.model.Category;

import java.util.ArrayList;

public class Library extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<Category> categorieslist;
    catagoryAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        recyclerView = findViewById(R.id.lv);
        myDB = new MyDatabaseHelper(this);
        categorieslist = new ArrayList<Category>();
        this.setTitle("My Library");
        storeDataInArrays();

        customAdapter = new catagoryAdapter(this,categorieslist);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.getAllCategories1();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                categorieslist.add(new Category(cursor.getInt(0),cursor.getString(1)));
            }

        }
    }
}