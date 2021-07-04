package com.example.chesserschedulingapp.Database;

import android.app.Application;

import com.example.chesserschedulingapp.DAO.AssessmentDAO;
import com.example.chesserschedulingapp.DAO.CourseDAO;
import com.example.chesserschedulingapp.DAO.TermDAO;
import com.example.chesserschedulingapp.Entity.AssessmentEntity;
import com.example.chesserschedulingapp.Entity.CourseEntity;
import com.example.chesserschedulingapp.Entity.TermEntity;

import java.util.List;

public class ScheduleRepository {
    private TermDAO mTermDAO;
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private List<TermEntity> mAllTerms;
    private List<CourseEntity> mAllCourses;
    private List<AssessmentEntity> mAllAssessments;
    private int termID;

    public ScheduleRepository(Application application) {
        ScheduleDatabase db = ScheduleDatabase.getDatabase(application);
        mTermDAO= db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<TermEntity> getAllTerms() {
        ScheduleDatabase.databaseWriter.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<CourseEntity> getAllCourses() {
        ScheduleDatabase.databaseWriter.execute(()->{
            mAllCourses=mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<AssessmentEntity> getAllAssessments() {
        ScheduleDatabase.databaseWriter.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert (TermEntity termEntity) {
        ScheduleDatabase.databaseWriter.execute(() ->{
            mTermDAO.insert(termEntity);
        });
    }

    public void insert (CourseEntity courseEntity) {
        ScheduleDatabase.databaseWriter.execute(() ->{
            mCourseDAO.insert(courseEntity);
        });
    }

    public void insert (AssessmentEntity assessmentEntity) {
        ScheduleDatabase.databaseWriter.execute(() ->{
            mAssessmentDAO.insert(assessmentEntity);
        });
    }

    public void delete (TermEntity termEntity) {
        ScheduleDatabase.databaseWriter.execute(() ->{
            mTermDAO.delete(termEntity);
        });
    }
    public void delete (CourseEntity courseEntity) {
        ScheduleDatabase.databaseWriter.execute(() ->{
            mCourseDAO.delete(courseEntity);
        });
    }

    public void delete (AssessmentEntity assessmentEntity) {
        ScheduleDatabase.databaseWriter.execute(() ->{
            mAssessmentDAO.delete(assessmentEntity);
        });
    }
}
