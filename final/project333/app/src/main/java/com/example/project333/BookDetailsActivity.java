package com.example.project333;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project333.model.Book;

public class BookDetailsActivity
        extends AppCompatActivity {
    Button btnEditBook;
    Book currentBook = null;
    ImageView ivBookImage;
    TextView tvBookAuthorName;
    TextView tvBookCategory;
    TextView tvBookName;
    TextView tvBookNumberOfPages;
    TextView tvBookReleaseYear;
    MyDatabaseHelper myDB;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_book);
        myDB = new MyDatabaseHelper(this);

        ivBookImage = findViewById(R.id.ivBookImage);
        btnEditBook = findViewById(R.id.btnEditBook);
        tvBookAuthorName = findViewById(R.id.tvBookAuthorName);
        tvBookCategory = findViewById(R.id.tvBookCategory);
        tvBookName = findViewById(R.id.tvBookName);
        tvBookNumberOfPages = findViewById(R.id.tvBookNumberOfPages);
        tvBookReleaseYear = findViewById(R.id.tvBookReleaseYear);
        initData();

    }
            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + tvBookName.getText().toString() + " ?");
        builder.setMessage("Are you sure you want to delete " + tvBookName.getText().toString() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB =  MyDatabaseHelper.getInstance(BookDetailsActivity.this);
                myDB.deleteOneRow(BookDetailsActivity.this,getIntent().getStringExtra("id"));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                confirmDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initData() {
        if(getIntent().hasExtra("id")){
            currentBook= myDB.getBook(Integer.parseInt(getIntent().getStringExtra("id")));
            tvBookName.setText(currentBook.getName());
            tvBookAuthorName.setText(currentBook.getAuthorName());
            tvBookNumberOfPages.setText(currentBook.getPagesNumber()+"");
            ivBookImage.setImageURI(Uri.parse(currentBook.getImageURL()+""));
            tvBookReleaseYear.setText(currentBook.getReleaseYear()+"");
            tvBookCategory.setText(getIntent().getStringExtra("category"));
            btnEditBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BookDetailsActivity.this, UpdateActivity.class);
                    intent.putExtra("id", currentBook.getId()+"");
                    intent.putExtra("category",getIntent().getStringExtra("category"));
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

        this.setTitle((CharSequence) currentBook.getName());
    }

}

