package com.example.acadroidaioadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addBookCode(View view) {
        Intent intent = new Intent(this,AddBookActivity.class);
        startActivity(intent);
    }

    public void addSubjectCode(View view) {
        Intent intent = new Intent(this,AddSubjectActivity.class);
        startActivity(intent);
    }

    public void viewSubjectCode(View view) {
        Intent intent = new Intent(this,ViewSubjectActivity.class);
        startActivity(intent);
    }
}