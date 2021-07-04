package com.example.chesserschedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chesserschedulingapp.Database.ScheduleRepository;
import com.example.chesserschedulingapp.Entity.AssessmentEntity;
import com.example.chesserschedulingapp.Entity.CourseEntity;
import com.example.chesserschedulingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity {
    private ScheduleRepository scheduleRepository;
    static int tempId;
    int assessmentId;
    int courseId;
    String name;
    String title;
    String dueDate;
    String goalStartDate;
    String goalEndDate;
    String notes;
    EditText assessmentName;
    EditText assessmentTitle;
    TextView courseDate;
    EditText assessmentGoalStartDate;
    EditText assessmentGoalEndDate;
    TextView courseNotes;
    Long dueDateLong;
    Long goalStartLong;
    Long goalEndLong;
    AssessmentEntity selectedAssessment;
    CourseEntity selectedCourse;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static int alertCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        scheduleRepository = new ScheduleRepository(getApplication());
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        List<AssessmentEntity> allAssessments = scheduleRepository.getAllAssessments();
        List<CourseEntity> allCourses = scheduleRepository.getAllCourses();

        for (AssessmentEntity assessment : allAssessments) {
            if (assessment.getAssessmentId() == assessmentId) {
                selectedAssessment = assessment;
            }
        }

        assessmentName = findViewById(R.id.assessmentName);
        assessmentTitle = findViewById(R.id.assessmentTitle);
        courseDate = findViewById(R.id.courseDue);
        assessmentGoalStartDate = findViewById(R.id.assessmentGoalStartDate);
        assessmentGoalEndDate = findViewById(R.id.assessmentGoalEndDate);
        courseNotes = findViewById(R.id.courseNotesText);

        if (selectedAssessment != null) {
            name = selectedAssessment.getAssessmentName();
            title = selectedAssessment.getAssessmentTitle();
            goalStartDate = dateFormat.format(selectedAssessment.getGoalStartDate());
            goalEndDate = dateFormat.format(selectedAssessment.getGoalEndDate());
            courseId = selectedAssessment.getCourseId();
            for (CourseEntity course : allCourses) {
                if (selectedAssessment.getCourseId() == courseId) {
                    selectedCourse = course;
                }
            }
        }
        if (selectedCourse != null) {
            dueDate = dateFormat.format(selectedCourse.getEndDate());
            notes = selectedCourse.getCourseNotes();
        }
        if (assessmentId != -1) {
            assessmentName.setText(name);
            assessmentTitle.setText(title);
            courseDate.setText(dueDate);
            assessmentGoalStartDate.setText(goalStartDate);
            assessmentGoalEndDate.setText(goalEndDate);
            courseNotes.setText(notes);
        }

    }

    public void saveAssessment(View view) throws ParseException {
        AssessmentEntity assessment;
        if (assessmentId != -1) {
            Log.d("Assessments debug", "Printing assessment ID: " + assessmentId);
            goalStartLong = dateFormat.parse(assessmentGoalStartDate.getText().toString()).getTime();
            goalEndLong = dateFormat.parse(assessmentGoalEndDate.getText().toString()).getTime();
            assessment = new AssessmentEntity(assessmentId, assessmentName.getText().toString(), assessmentTitle.getText().toString(), goalStartLong, goalEndLong, courseId);
        } else {
            List<AssessmentEntity> allAssessments = scheduleRepository.getAllAssessments();
            if (allAssessments.size() != 0) {
                assessmentId = allAssessments.get(allAssessments.size() - 1).getAssessmentId();
            }
            else {
                assessmentId = 1;
            }
            //Start here
            goalStartLong = dateFormat.parse(assessmentGoalStartDate.getText().toString()).getTime();
            goalEndLong = dateFormat.parse(assessmentGoalEndDate.getText().toString()).getTime();
            assessment = new AssessmentEntity(++assessmentId, assessmentName.getText().toString(), assessmentTitle.getText().toString(), goalStartLong, goalEndLong, courseId);
        }
        scheduleRepository.insert(assessment);
        Intent intent = new Intent(AssessmentDetailsActivity.this, AssessmentActivity.class);
        startActivity(intent);
    }

    public void deleteAssessment(View view) {
        if (selectedAssessment != null) {
            scheduleRepository.delete(selectedAssessment);
            Intent intent = new Intent(AssessmentDetailsActivity.this, AssessmentActivity.class);
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Assessment has not been saved yet; it cannot and does not need to be deleted.";
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
            goalStartLong = dateFormat.parse(assessmentGoalStartDate.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            goalEndLong = dateFormat.parse(assessmentGoalEndDate.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (id == R.id.share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, selectedAssessment.getAssessmentName() + "\n Goal Start: " + dateFormat.format(selectedAssessment.getGoalStartDate()) + "\n Goal End: " + dateFormat.format(selectedAssessment.getGoalEndDate()));
            shareIntent.putExtra(Intent.EXTRA_TITLE, "Assessment " + selectedAssessment.getAssessmentTitle() + " Details");
            shareIntent.setType("text/plain");
            Intent sendIntent = Intent.createChooser(shareIntent, null);
            startActivity(sendIntent);
            return true;
        }

        if (id == R.id.alert_start) {
            Intent alertIntent = new Intent(AssessmentDetailsActivity.this,TACReceiver.class);
            alertIntent.putExtra("message", "Assessment goal start for today! " + selectedAssessment.getAssessmentName());
            PendingIntent alertDispatch = PendingIntent.getBroadcast(AssessmentDetailsActivity.this,++alertCount,alertIntent,0);
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, goalStartLong, alertDispatch);
            return true;
        }

        if (id == R.id.alert_end) {
            Intent alertIntent = new Intent(AssessmentDetailsActivity.this,TACReceiver.class);
            alertIntent.putExtra("message", "Hello! You had a goal set to complete the " + selectedAssessment.getAssessmentName() + " assessment today!");
            PendingIntent alertDispatch = PendingIntent.getBroadcast(AssessmentDetailsActivity.this,++alertCount,alertIntent,0);
            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, goalEndLong, alertDispatch);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}