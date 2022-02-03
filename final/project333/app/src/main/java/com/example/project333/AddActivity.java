package com.example.project333;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.project333.model.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    List<Integer> ids;
    EditText title_input, author_input, pages_input, release_input;
    Button add_button;
    FloatingActionButton icoGallery;
    ImageView ivProfileImage;
    Spinner category_input;
    MyDatabaseHelper myDB;
    String imagePath = "";
    File imageFile;
    int singleId = 0;
    int RESULT_LOAD_IMAGE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        release_input = findViewById(R.id.release_input);
        category_input = findViewById(R.id.spin);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        add_button = findViewById(R.id.add_button);
        icoGallery = findViewById(R.id.btnChangeProfileImage);
        this.setTitle("Add Book");
        myDB = new MyDatabaseHelper(this);

        final List<Category> Categories = myDB.getAllCategoriess();
        List<String> setcardCategories = new ArrayList<String>();
        ids = new ArrayList<Integer>();
        for (Category data : Categories) {
            ids.add(data.getId());
            setcardCategories.add(data.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, setcardCategories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_input.setAdapter(arrayAdapter);
        icoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        category_input.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                singleId = ids.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Message", "Nothing is selected");
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Invalid Book Name", Toast.LENGTH_SHORT).show();
                } else if (author_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Invalid Author Name", Toast.LENGTH_SHORT).show();
                } else if (release_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Invalid Release Date", Toast.LENGTH_SHORT).show();
                } else if (singleId < 0) {
                    Toast.makeText(AddActivity.this, "Invalid Book Category", Toast.LENGTH_SHORT).show();
                } else {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                    myDB.addBook(AddActivity.this, title_input.getText().toString().trim(),
                            author_input.getText().toString().trim(),
                            singleId,
                            Integer.parseInt(release_input.getText().toString().trim()),
                            Integer.parseInt(pages_input.getText().toString().trim()), imagePath
                    );
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
            cursor.moveToFirst();

            int column_index = cursor.getColumnIndex(projection[0]);
            imagePath = cursor.getString(column_index);
            cursor.close();

            imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                ivProfileImage.setImageBitmap(imageBitmap);
            }

        } else {
            Toast.makeText(this, "You have not selected and image", Toast.LENGTH_SHORT).show();
        }
    }

}
