package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.R;

public class CourseActivity extends AppCompatActivity {

    private ScheduleRepository scheduleRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        scheduleRepo = new ScheduleRepository(getApplication());
        scheduleRepo.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.course_list);

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(scheduleRepo.getAllCourses());
    }
}