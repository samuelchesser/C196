package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.Entity.AssessmentEntity;
import com.example.chesserschedulingapp.Entity.CourseEntity;
import com.example.chesserschedulingapp.Entity.TermEntity;
import com.example.chesserschedulingapp.R;
import android.widget.TextView;
import java.util.Date;
import java.util.List;

public class ProgressTrackerActivity extends AppCompatActivity {
    private ScheduleRepository scheduleRepository;
    int totalTerms;
    int completedTerms;
    Double termPercentage;
    int totalCourses;
    int completedCourses;
    Double coursePercentage;
    int totalAssessments;
    int completedAssessments;
    Double assessmentPercentage;
    TextView terms;
    TextView termsDone;
    TextView termsPercent;
    TextView courses;
    TextView coursesDone;
    TextView coursesPercent;
    TextView assessments;
    TextView assessmentsDone;
    TextView assessmentsPercent;
    Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracker);
        scheduleRepository = new ScheduleRepository(getApplication());
        List<TermEntity> allTerms = scheduleRepository.getAllTerms();
        List<CourseEntity> allCourses = scheduleRepository.getAllCourses();
        List<AssessmentEntity> allAssessments = scheduleRepository.getAllAssessments();
        terms = findViewById(R.id.termCount);
        termsDone = findViewById(R.id.completedTermCount);
        termsPercent = findViewById(R.id.termPercent);
        courses = findViewById(R.id.courseCount);
        coursesDone = findViewById(R.id.completedCourseCount);
        coursesPercent = findViewById(R.id.coursePercent);
        assessments = findViewById(R.id.assessmentCount);
        assessmentsDone = findViewById(R.id.completedAssessmentCount);
        assessmentsPercent = findViewById(R.id.assessmentPercent);
        totalTerms = allTerms.size();
        terms.setText(String.valueOf(totalTerms));
        totalCourses = allCourses.size();
        courses.setText(String.valueOf(totalCourses));
        totalAssessments = allAssessments.size();
        assessments.setText(String.valueOf(totalAssessments));

        for (TermEntity term : allTerms) {
            if (term.getEndDate() < date.getTime()) {
                completedTerms++;
            }
        }

        for (CourseEntity course : allCourses) {
            if (course.getStatus().equals("Passed")) {
                completedCourses++;
                for (AssessmentEntity assessment : allAssessments) {
                    if (assessment.getCourseId() == course.getCourseId()) {
                        completedAssessments++;
                    }
                }
            }
        }
        termsDone.setText(String.valueOf(completedTerms));
        coursesDone.setText(String.valueOf(completedCourses));
        assessmentsDone.setText(String.valueOf(completedAssessments));

        if (totalTerms > 0 && completedTerms > 0 ) {
                termPercentage = (Double.valueOf(completedTerms) / Double.valueOf(totalTerms)) * 100;
                //Log.d("terms", "Total terms: " + totalTerms + " Completed: " + completedTerms + " Percentage: " + termPercentage);
                termsPercent.setText(String.format("%.2f",termPercentage));
        }

        if (totalCourses > 0 && completedCourses > 0) {
            coursePercentage = (Double.valueOf(completedCourses) / Double.valueOf(totalCourses)) * 100;
            coursesPercent.setText(String.format("%.2f",coursePercentage));
        }

        if (totalAssessments > 0 && completedAssessments > 0) {
            assessmentPercentage = (Double.valueOf(completedAssessments) / Double.valueOf(totalAssessments)) * 100 ;
            assessmentsPercent.setText(String.format("%.2f",assessmentPercentage));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_progress_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.showTerms) {
            Intent intent = new Intent(ProgressTrackerActivity.this,TermActivity.class);
            startActivity(intent);
        }

        if (id == R.id.showCourses) {
            Intent intent = new Intent(ProgressTrackerActivity.this,CourseActivity.class);
            startActivity(intent);
        }

        if (id == R.id.showAssessments) {
            Intent intent = new Intent(ProgressTrackerActivity.this,AssessmentActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}