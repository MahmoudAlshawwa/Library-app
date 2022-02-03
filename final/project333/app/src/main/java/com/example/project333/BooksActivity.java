package com.example.project333;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.project333.adapter.bookAdapter;

public class BooksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    bookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new MyDatabaseHelper(this);
        Intent intent=getIntent();
        int catagoryID = Integer.parseInt(intent.getStringExtra("catagoryID"));
        String catTitle = intent.getStringExtra("catagoryName");
        this.setTitle(catTitle);
        bookAdapter = new bookAdapter(this,myDB.getCategoryBooks(catagoryID),catTitle);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
