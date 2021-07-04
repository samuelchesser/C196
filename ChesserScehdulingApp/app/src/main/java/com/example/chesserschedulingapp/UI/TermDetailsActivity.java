package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.Entity.TermEntity;
import com.example.chesserschedulingapp.Entity.CourseEntity;
import com.example.chesserschedulingapp.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TermDetailsActivity extends AppCompatActivity {
    private ScheduleRepository scheduleRepository;
    int termId;
    String name;
    String start;
    String end;
    EditText termName;
    EditText termStart;
    EditText termEnd;
    Long termStartLong;
    Long termEndLong;
    TermEntity selectedTerm;
    public static int courseCount;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        List<CourseEntity> linkedCourses=new ArrayList<>();
        List<TermEntity> termList=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        scheduleRepository = new ScheduleRepository(getApplication());
        termId = getIntent().getIntExtra("termId", -1);
        if (termId == -1) {
            termId = CourseDetailsActivity.tempId;
        }
        List<TermEntity> allTerms = scheduleRepository.getAllTerms();

        for (TermEntity term : allTerms) {
            if (term.getTermId() == termId) {
                selectedTerm = term;
            }
        }

        termName = findViewById(R.id.termName);
        termStart = findViewById(R.id.termStart);
        termEnd = findViewById(R.id.termEnd);

        if (selectedTerm != null) {
            name = selectedTerm.getTermName();
            start = dateFormat.format(selectedTerm.getStartDate());
            end = dateFormat.format(selectedTerm.getEndDate());
        }
        if (termId != -1) {
            termName.setText(name);
            termStart.setText(start);
            termEnd.setText(end);
        }
        RecyclerView view = findViewById(R.id.termCourseList);
        view.setAdapter(courseAdapter);
        view.setLayoutManager(new LinearLayoutManager(this));
        for (CourseEntity course : scheduleRepository.getAllCourses()) {
            if (course.getTermId() == termId) {
                linkedCourses.add(course);
            }
            //Log.d("course debug", "Courses: " + scheduleRepository.getAllCourses());
            //Log.d("course debug", "Course Term ID: " + course.getTermId());
            //Log.d("course debug", "Term ID: " + termId);
        }
        courseAdapter.setCourses(linkedCourses);
        courseCount = linkedCourses.size();
    }

    public void addCourseToTerm(View view) {
        Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }


    public void saveTerm(View view) throws ParseException {
        TermEntity term;
        if (termId != -1) {
            //Log.d("Terms debug", "Printing term ID: " + termId);
            termStartLong = dateFormat.parse(termStart.getText().toString()).getTime();
            termEndLong = dateFormat.parse(termEnd.getText().toString()).getTime();
            term = new TermEntity(termId, termName.getText().toString(), termStartLong, termEndLong);
            Intent intent = new Intent(TermDetailsActivity.this, TermActivity.class);
            startActivity(intent);
        }
        else {
            List<TermEntity> allTerms=scheduleRepository.getAllTerms();
            if (allTerms.size() != 0) {
                termId = allTerms.get(allTerms.size() - 1).getTermId();
            }
            else {
                termId = 1;
            }
            //Log.d("Date debug", "termStart = " + termStart.getText().toString());
           // Log.d("Date debug", "termEnd = " + termEnd.getText().toString());
            termStartLong = dateFormat.parse(termStart.getText().toString()).getTime();
            termEndLong = dateFormat.parse(termEnd.getText().toString()).getTime();
            term = new TermEntity(++termId,termName.getText().toString(), termStartLong, termEndLong);
            //Log.d("Terms debug", "Printing term ID from Else: " + termId);
            Intent intent = new Intent(TermDetailsActivity.this, TermActivity.class);
            startActivity(intent);
        }
        scheduleRepository.insert(term);
    }
    public void deleteTerm(View view) {
        if(courseCount==0 && selectedTerm != null) {
            scheduleRepository.delete(selectedTerm);
            Intent intent = new Intent(TermDetailsActivity.this, TermActivity.class);
            startActivity(intent);
        }
        else if (courseCount != 0) {
            Context context = getApplicationContext();
            CharSequence text = "Warning! You cannot delete a term with courses attached; remove all courses before deleting the term.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Term has not been created; please use the back button to exit creation.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}