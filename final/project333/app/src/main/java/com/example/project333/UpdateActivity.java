package com.example.project333;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.project333.model.Book;
import com.example.project333.model.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input, release_input;
    Button update_button, delete_button;
    FloatingActionButton changeImage;
    MyDatabaseHelper myDB;
    String id, title, author, pages, release;
    int category;
    List<Integer> ids;
    List<String> setcardCategories;
    Spinner category_input;
    ImageView imageView;
    String imagePath = "";
    File imageFile;
    int singleId = 0;
    int RESULT_LOAD_IMAGE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        myDB = new MyDatabaseHelper(this);
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        release_input = findViewById(R.id.release_input);
        category_input = findViewById(R.id.sp_input);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        imageView = findViewById(R.id.ivProfileImage);
        changeImage = findViewById(R.id.btnChangeProfileImage);
        id = getIntent().getStringExtra("id");

        //First we call this
        getAndSetIntentData();
        title = title_input.getText().toString().trim();
        this.setTitle("Edit " + title + " Details.");
        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (title_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Invalid Book Name", Toast.LENGTH_SHORT).show();
                } else if (author_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Invalid Author Name", Toast.LENGTH_SHORT).show();
                } else if (release_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Invalid Release Date", Toast.LENGTH_SHORT).show();
                } else if (singleId < 0) {
                    Toast.makeText(UpdateActivity.this, "Invalid Book Category", Toast.LENGTH_SHORT).show();
                } else {


                    //And only then we call this
                    MyDatabaseHelper myDB = MyDatabaseHelper.getInstance(UpdateActivity.this);
                    title = title_input.getText().toString().trim();
                    author = author_input.getText().toString().trim();
                    pages = pages_input.getText().toString().trim();
                    release = release_input.getText().toString().trim();
                    category = singleId;

                    myDB.updateData(UpdateActivity.this, id, title, author, pages, release, category, imagePath);

                }
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        category_input.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                singleId = ids.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    void getAndSetIntentData() {

        final List<Category> Categories = myDB.getAllCategoriess();
        setcardCategories = new ArrayList<String>();
        ids = new ArrayList<Integer>();
        for (Category data : Categories) {
            ids.add(data.getId());
            setcardCategories.add(data.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, setcardCategories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int spinnerPosition = 0;
        if (getIntent().getStringExtra("category") != null) {
            spinnerPosition = arrayAdapter.getPosition(getIntent().getStringExtra("category"));
        }
        category_input.setAdapter(arrayAdapter);
        category_input.setSelection(spinnerPosition);
        if (getIntent().hasExtra("id")) {
            Book book1 = myDB.getBook(Integer.parseInt(getIntent().getStringExtra("id") + ""));
            title_input.setText(book1.getName());
            author_input.setText(book1.getAuthorName());
            pages_input.setText(book1.getPagesNumber() + "");
            imageView.setImageURI(Uri.parse(book1.getImageURL() + ""));
            release_input.setText(book1.getReleaseYear() + "");
            imagePath = (book1.getImageURL() + "");
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = MyDatabaseHelper.getInstance(UpdateActivity.this);
                myDB.deleteOneRow(UpdateActivity.this, id);
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

                imageView.setImageBitmap(imageBitmap);
            }

        } else {
            Toast.makeText(this, "You have not selected and image", Toast.LENGTH_SHORT).show();
        }
    }
}
