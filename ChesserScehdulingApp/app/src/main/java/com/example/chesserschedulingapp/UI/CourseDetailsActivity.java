package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.Entity.AssessmentEntity;
import com.example.chesserschedulingapp.Entity.CourseEntity;
import com.example.chesserschedulingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ScheduleRepository scheduleRepository;
    static int tempId;
    int courseId;
    int termId;
    public String selectedStatus;
    String name;
    String start;
    String end;
    String status;
    String mentorName;
    String mentorPhone;
    String mentorEmail;
    String notes;
    EditText courseName;
    EditText courseStart;
    EditText courseEnd;
    Spinner courseStatus;
    EditText courseMentorName;
    EditText courseMentorPhone;
    EditText courseMentorEmail;
    EditText courseNotes;
    Long courseStartLong;
    Long courseEndLong;
    CourseEntity selectedCourse;
    public static int assessmentCount;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        List<CourseEntity> courseList = new ArrayList<>();
        List<AssessmentEntity> linkedAssessments = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Spinner statusSpinner = findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setOnItemSelectedListener(this);

        scheduleRepository = new ScheduleRepository(getApplication());
        courseId = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("termId", -1);
        tempId = termId;
        List<CourseEntity> allCourses = scheduleRepository.getAllCourses();

        for (CourseEntity course : allCourses) {
            if (course.getCourseId() == courseId) {
                selectedCourse = course;
            }
        }

        courseName = findViewById(R.id.courseName);
        courseStart = findViewById(R.id.courseStart);
        courseEnd = findViewById(R.id.courseEnd);
        courseStatus = findViewById(R.id.courseStatusSpinner);
        courseMentorName = findViewById(R.id.courseMentorName);
        courseMentorEmail = findViewById(R.id.courseMentorEmail);
        courseMentorPhone = findViewById(R.id.courseMentorPhone);
        courseNotes = findViewById(R.id.courseNotes);


        if (selectedCourse != null) {
            name = selectedCourse.getCourseName();
            start = dateFormat.format(selectedCourse.getStartDate());
            end = dateFormat.format(selectedCourse.getEndDate());
            status = selectedCourse.getStatus();
            mentorName = selectedCourse.getMentorName();
            mentorEmail = selectedCourse.getMentorEmail();
            mentorPhone = selectedCourse.getMentorPhone();
            notes = selectedCourse.getCourseNotes();
        }
        if (courseId != -1) {
            courseName.setText(name);
            courseStart.setText(start);
            courseEnd.setText(end);
            courseStatus.setSelection(adapter.getPosition(status));
            courseMentorName.setText(mentorName);
            courseMentorEmail.setText(mentorEmail);
            courseMentorPhone.setText(mentorPhone);
            courseNotes.setText(notes);
        }
        RecyclerView view = findViewById(R.id.courseAssessmentList);
        view.setAdapter(assessmentAdapter);
        view.setLayoutManager(new LinearLayoutManager(this));
        for (AssessmentEntity assessment : scheduleRepository.getAllAssessments()) {
            if (assessment.getCourseId() == courseId) {
                linkedAssessments.add(assessment);
            }
            //Log.d("assessment debug", "Assessments: " + scheduleRepository.getAllAssessments());
            //Log.d("assessment debug", "Assessment ID: " + assessment.getAssessmentId());
            //Log.d("assessment debug", "Course ID: " + courseId);
        }
        assessmentAdapter.setAssessments(linkedAssessments);
        assessmentCount = linkedAssessments.size();

    }

    public void addAssessmentToCourse(View view) {
        Intent intent = new Intent(CourseDetailsActivity.this, AssessmentDetailsActivity.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }



    public void saveCourse(View view) throws ParseException {
        CourseEntity course;
        if (courseId != -1) {
            Log.d("Courses debug", "Printing course ID: " + courseId);
            courseStartLong = dateFormat.parse(courseStart.getText().toString()).getTime();
            courseEndLong = dateFormat.parse(courseEnd.getText().toString()).getTime();
            course = new CourseEntity(courseId, courseName.getText().toString(), courseStartLong, courseEndLong, selectedStatus, courseMentorName.getText().toString(), courseMentorPhone.getText().toString(), courseMentorEmail.getText().toString(), courseNotes.getText().toString(), termId);
        } else {
            List<CourseEntity> allCourses = scheduleRepository.getAllCourses();
            if (allCourses.size() != 0) {
                courseId = allCourses.get(allCourses.size() - 1).getCourseId();
            }
            else {
                courseId = 1;
            }

            Log.d("Date debug", "courseStart = " + courseStart.getText().toString());
            Log.d("Date debug", "courseEnd = " + courseEnd.getText().toString());
            courseStartLong = dateFormat.parse(courseStart.getText().toString()).getTime();
            courseEndLong = dateFormat.parse(courseEnd.getText().toString()).getTime();
            course = new CourseEntity(++courseId, courseName.getText().toString(), courseStartLong, courseEndLong, selectedStatus, courseMentorName.getText().toString(), courseMentorPhone.getText().toString(), courseMentorEmail.getText().toString(), courseNotes.getText().toString(), termId);
        }
        scheduleRepository.insert(course);
        Intent intent = new Intent(CourseDetailsActivity.this, CourseActivity.class);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
        if (selectedCourse != null) {
            scheduleRepository.delete(selectedCourse);
            Intent intent = new Intent(CourseDetailsActivity.this, CourseActivity.class);
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Course has not been saved yet; it cannot and does not need to be deleted.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sharing_alerts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        try {
            courseStartLong = dateFormat.parse(courseStart.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            courseEndLong = dateFormat.parse(courseEnd.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (id == R.id.share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, selectedCourse.getCourseName() + "\n Course Notes : " + selectedCourse.getCourseNotes());
            shareIntent.putExtra(Intent.EXTRA_TITLE, "Course " + selectedCourse.getCourseName() + " Notes");
            shareIntent.setType("text/plain");
            Intent sendIntent = Intent.createChooser(shareIntent, null);
            startActivity(sendIntent);
            return true;
        }

        if (id == R.id.alert_start) {
            Intent alertIntent = new Intent(CourseDetailsActivity.this,TACReceiver.class);
            alertIntent.putExtra("message", selectedCourse.getCourseName() + " is starting today. Crack those books!");
            PendingIntent alertDispatch = PendingIntent.getBroadcast(CourseDetailsActivity.this,++AssessmentDetailsActivity.alertCount,alertIntent,0);
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, courseStartLong, alertDispatch);
            return true;
        }

        if (id == R.id.alert_end) {
            Intent alertIntent = new Intent(CourseDetailsActivity.this,TACReceiver.class);
            alertIntent.putExtra("message", selectedCourse.getCourseName() +  " is ending today! Get any assessments done.");
            PendingIntent alertDispatch = PendingIntent.getBroadcast(CourseDetailsActivity.this,++AssessmentDetailsActivity.alertCount,alertIntent,0);
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, courseEndLong, alertDispatch);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedStatus = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}