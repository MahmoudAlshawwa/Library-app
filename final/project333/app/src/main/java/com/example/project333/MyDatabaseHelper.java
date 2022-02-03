package com.example.project333;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.project333.model.Book;
import com.example.project333.model.Category;

import java.util.ArrayList;

class MyDatabaseHelper extends SQLiteOpenHelper {


    public static Context context;
    public static final String BOOK_COLUMN_AUTHOR_NAME = "author_name";
    public static final String BOOK_COLUMN_CATEGORY_ID = "category_id";
    public static final String BOOK_COLUMN_ID = "id";
    public static final String BOOK_COLUMN_IMAGE_URL = "image_url";
    public static final String BOOK_COLUMN_NAME = "name";
    public static final String BOOK_COLUMN_PAGES_NUMBER = "pages_number";
    public static final String BOOK_COLUMN_RELEASE_YEAR = "release_year";
    public static final String BOOK_TABLE_NAME = "books";
    public static final String CATEGORY_TABLE_NAME = "categories";
    public static final String CATEGORY_COLUMN_NAME = "name";
    public static final String DATABASE_NAME = "BookLibraryDB.db";
    private static final int DATABASE_VERSION = 1;
    static MyDatabaseHelper instance;

    MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static MyDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyDatabaseHelper(context);
        }

        return instance;
    }

    void addBook(Context context, String title, String author, int categoryID, int pages, int release, String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_COLUMN_NAME, title);
        cv.put(BOOK_COLUMN_AUTHOR_NAME, author);
        cv.put(BOOK_COLUMN_PAGES_NUMBER, pages);
        cv.put(BOOK_COLUMN_RELEASE_YEAR, release);
        cv.put(BOOK_COLUMN_CATEGORY_ID, categoryID);
        cv.put(BOOK_COLUMN_IMAGE_URL, path);
        long result = db.insert(BOOK_TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getAllCategories1() {
        String query = "SELECT * FROM " + CATEGORY_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public ArrayList<Category> getAllCategoriess() {
        ArrayList<Category> arrayList = new ArrayList();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM categories", null);
        cursor.moveToFirst();
        int n = cursor.getColumnIndex("id");
        int n2 = cursor.getColumnIndex("name");
        while (!cursor.isAfterLast()) {
//            int n3 = cursor.getInt(n);
            Log.e("mah", "iddddd " + cursor.getInt(n));
            String string2 = cursor.getString(n2);
            arrayList.add(new Category(cursor.getInt(n), string2));
            cursor.moveToNext();
        }
        return arrayList;
    }

    void updateData(Context context1, String row_id, String title, String author, String pages, String release, int category, String imageurl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BOOK_COLUMN_NAME, title);
        cv.put(BOOK_COLUMN_AUTHOR_NAME, author);
        cv.put(BOOK_COLUMN_PAGES_NUMBER, pages);
        cv.put(BOOK_COLUMN_RELEASE_YEAR, release);
        cv.put(BOOK_COLUMN_CATEGORY_ID, category);
        cv.put(BOOK_COLUMN_IMAGE_URL, imageurl);
        Log.e("mah", "CAT" + category + " ->   rowID " + row_id);

        long result = db.update(BOOK_TABLE_NAME, cv, "id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context1, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context1, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(Context context, String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(BOOK_TABLE_NAME, "id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean insertCategory(Category category) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(CATEGORY_COLUMN_ID, category.getId());
        contentValues.put(CATEGORY_COLUMN_NAME, category.getName());
        long result = sQLiteDatabase.insert(CATEGORY_TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Data Not Add ", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public ArrayList<Book> getCategoryBooks(int n) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        String[] arrstring = new String[]{Integer.toString((int) n)};
        Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM books WHERE category_id  = ? ", arrstring);
        //   Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM books",null);
        cursor.moveToFirst();
        int n2 = cursor.getColumnIndex(BOOK_COLUMN_ID);
        int n3 = cursor.getColumnIndex(BOOK_COLUMN_NAME);
        int n4 = cursor.getColumnIndex(BOOK_COLUMN_AUTHOR_NAME);
        int n5 = cursor.getColumnIndex(BOOK_COLUMN_RELEASE_YEAR);
        int n6 = cursor.getColumnIndex(BOOK_COLUMN_IMAGE_URL);
        int n7 = cursor.getColumnIndex(BOOK_COLUMN_PAGES_NUMBER);
        int n8 = cursor.getColumnIndex(BOOK_COLUMN_CATEGORY_ID);
        while (!cursor.isAfterLast()) {
            Book book = new Book();
            book.setId(cursor.getInt(n2));
            book.setName(cursor.getString(n3));
            book.setAuthorName(cursor.getString(n4));
            book.setReleaseYear(cursor.getInt(n5));
            book.setImageURL(cursor.getString(n6));
            book.setPagesNumber(cursor.getInt(n7));
            book.setCategoryId(cursor.getInt(n8));
            Log.e("mah", cursor.getString(n2));
            Log.e("mah", cursor.getString(n3));
            Log.e("mah", "BOOK_COLUMN_CATEGORY_ID   " + cursor.getString(n8));
            arrayList.add((Book) book);
            cursor.moveToNext();
        }
        return arrayList;
    }

    public Book getBook(int n) {
        // Book Book1 = new Book();
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        String[] arrstring = new String[]{Integer.toString((int) n)};
        Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM books WHERE id  = ? ", arrstring);
        //   Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM books",null);
        cursor.moveToFirst();
        int n2 = cursor.getColumnIndex(BOOK_COLUMN_ID);
        int n3 = cursor.getColumnIndex(BOOK_COLUMN_NAME);
        int n4 = cursor.getColumnIndex(BOOK_COLUMN_AUTHOR_NAME);
        int n5 = cursor.getColumnIndex(BOOK_COLUMN_RELEASE_YEAR);
        int n6 = cursor.getColumnIndex(BOOK_COLUMN_IMAGE_URL);
        int n7 = cursor.getColumnIndex(BOOK_COLUMN_PAGES_NUMBER);
        int n8 = cursor.getColumnIndex(BOOK_COLUMN_CATEGORY_ID);
        Book book = new Book();
        while (!cursor.isAfterLast()) {

            book.setId(cursor.getInt(n2));
            book.setName(cursor.getString(n3));
            book.setAuthorName(cursor.getString(n4));
            book.setReleaseYear(cursor.getInt(n5));
            book.setImageURL(cursor.getString(n6));
            book.setPagesNumber(cursor.getInt(n7));
            book.setCategoryId(cursor.getInt(n8));
            Log.e("mah", cursor.getString(n2));
            Log.e("mah", cursor.getString(n3));
            Log.e("mah", "BOOK_COLUMN_CATEGORY_ID  get book  " + cursor.getString(n8));
            //   arrayList.add((Book) book);
            cursor.moveToNext();
        }
        return book;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE categories(id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT )");
        sQLiteDatabase.execSQL("CREATE TABLE books(id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT ,author_name TEXT ,image_url TEXT ,release_year INTEGER ,pages_number INTEGER ,is_favourite INTEGER ,category_id INTEGER ,FOREIGN KEY (category_id) REFERENCES name(id) ON DELETE CASCADE)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS categories");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS books");
        this.onCreate(sQLiteDatabase);
    }

}
