package com.example.project333;

import android.app.Activity;
import android.os.Bundle;

public class Favorites extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        this.setTitle("Favorites");
    }
}
