package com.example.project333;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project333.model.Category;

public class CategoryActivity extends AppCompatActivity {

    Button AddCategory;
    EditText CategoryInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        AddCategory= findViewById(R.id.create_category);
        CategoryInput= findViewById(R.id.category_input);
        this.setTitle("Add Category");
        AddCategory.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(CategoryInput.getText().toString())){
                    MyDatabaseHelper dbHelper= new MyDatabaseHelper(CategoryActivity.this);
                    Category category = new Category(CategoryInput.getText().toString());
                    dbHelper.insertCategory(category);
                    Intent i = new Intent(CategoryActivity.this,MainActivity.class);
                    Toast.makeText(getApplicationContext(),"Category Added Successfull ",Toast.LENGTH_SHORT).show();
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }

            }
        });

    }
}