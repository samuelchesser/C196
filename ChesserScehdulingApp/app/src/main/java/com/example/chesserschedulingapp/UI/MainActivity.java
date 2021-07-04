package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void termScreen(View view) {
        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);
    }

    public void coursesScreen(View view) {
        Intent intent = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(intent);
    }

    public void assessmentsScreen(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentActivity.class);
        startActivity(intent);
    }

    public void progressScreen(View view) {
        Intent intent = new Intent(MainActivity.this, ProgressTrackerActivity.class);
        startActivity(intent);
    }
}