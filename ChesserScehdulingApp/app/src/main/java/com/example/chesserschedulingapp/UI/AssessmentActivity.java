package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.R;

public class AssessmentActivity extends AppCompatActivity {

    private ScheduleRepository scheduleRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        scheduleRepo = new ScheduleRepository(getApplication());
        scheduleRepo.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessment_list);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(scheduleRepo.getAllAssessments());
    }
}