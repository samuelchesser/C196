package com.example.chesserschedulingapp.Database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chesserschedulingapp.DAO.AssessmentDAO;
import com.example.chesserschedulingapp.DAO.CourseDAO;
import com.example.chesserschedulingapp.DAO.TermDAO;
import com.example.chesserschedulingapp.Entity.AssessmentEntity;
import com.example.chesserschedulingapp.Entity.CourseEntity;
import com.example.chesserschedulingapp.Entity.TermEntity;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Increment version number when testing and adding or changing DB features
@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 9 )

public abstract class ScheduleDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    //Set to debug to populate test database and remove data on each launch
    public static String mode = "production";

    static final ExecutorService databaseWriter =
            Executors.newFixedThreadPool(6);

    private static volatile ScheduleDatabase DB_INSTANCE;

    static ScheduleDatabase getDatabase(final Context context) {
        if (DB_INSTANCE == null) {
            synchronized (ScheduleDatabase.class) {
                if (DB_INSTANCE == null) {
                    DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabase.class, "schedule_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(DBCallback)
                            .build();
                }
            }
        }
        return DB_INSTANCE;
    }

    private static RoomDatabase.Callback DBCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //Used to populate test data when in debug; this will erase the DB, so be careful!
            if (mode == "debug") {
                databaseWriter.execute(() -> {
                    TermDAO mTermDao = DB_INSTANCE.termDAO();
                    CourseDAO mCourseDao = DB_INSTANCE.courseDAO();
                    AssessmentDAO mAssessmentDao = DB_INSTANCE.assessmentDAO();

                    mTermDao.removeAllTerms();
                    mCourseDao.removeAllCourses();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    mAssessmentDao.removeAllAssessments();
                    String termStart = "01/01/2021";
                    String termEnd = "06/30/2021";
                    Date startDate = null;
                    Date endDate = null;
                    try {
                        startDate = (Date) dateFormat.parse(termStart);
                    } catch (ParseException ex) {
                        Log.v("Date Exception", ex.getLocalizedMessage());
                    }
                    try {
                        endDate = (Date) dateFormat.parse(termEnd);
                    } catch (ParseException ex) {
                        Log.v("Date Exception", ex.getLocalizedMessage());
                    }
                    String courseStart = "01/01/2021";
                    String courseEnd = "06/30/2021";
                    Date courseStartDate = null;
                    Date courseEndDate = null;
                    try {
                        courseStartDate = (Date) dateFormat.parse(courseStart);
                    } catch (ParseException ex) {
                        Log.v("Date Exception", ex.getLocalizedMessage());
                    }
                    try {
                        courseEndDate = (Date) dateFormat.parse(courseEnd);
                    } catch (ParseException ex) {
                        Log.v("Date Exception", ex.getLocalizedMessage());
                    }
                    String assessmentGoalStart = "04/04/2021";
                    Date assessmentGoalStartDate = null;
                    try {
                        assessmentGoalStartDate = (Date) dateFormat.parse(assessmentGoalStart);
                    } catch (ParseException ex) {
                        Log.v("Date Exception", ex.getLocalizedMessage());
                    }
                    String assessmentGoalEnd = "06/04/2021";
                    Date assessmentGoalEndDate = null;
                    try {
                        assessmentGoalEndDate = (Date) dateFormat.parse(assessmentGoalEnd);
                    } catch (ParseException ex) {
                        Log.v("Date Exception", ex.getLocalizedMessage());
                    }
                    TermEntity term = new TermEntity(1, "Term 1", startDate.getTime(), endDate.getTime());
                    mTermDao.insert(term);
                    CourseEntity course = new CourseEntity(1,"Course 1",courseStartDate.getTime(),courseEndDate.getTime(),"Active","Rusty Shackleford","720-420-1234","rshackleford@hotmail.com","This course is haaaaaaard",1);
                    mCourseDao.insert(course);
                    AssessmentEntity assessment = new AssessmentEntity(1,"Objective", "Database Objective Assessment", assessmentGoalStartDate.getTime(),assessmentGoalEndDate.getTime(), 1);
                    mAssessmentDao.insert(assessment);
                });
            }
        }
    };
}
