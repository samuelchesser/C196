package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.Entity.TermEntity;
import com.example.chesserschedulingapp.R;

import java.util.Date;

public class TermActivity extends AppCompatActivity {

    private ScheduleRepository scheduleRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CourseDetailsActivity.tempId=-1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        scheduleRepo = new ScheduleRepository(getApplication());
        scheduleRepo.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.term_list);

        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(scheduleRepo.getAllTerms());

    }

    public void addTerm(View view) {
        Intent intent = new Intent(TermActivity.this, TermDetailsActivity.class);
        startActivity(intent);
    }
}