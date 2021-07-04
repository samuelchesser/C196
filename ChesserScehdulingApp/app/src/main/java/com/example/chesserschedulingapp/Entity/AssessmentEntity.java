package com.example.chesserschedulingapp.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

import java.sql.Date;

@Entity(tableName="assessments")
public class AssessmentEntity {
    @PrimaryKey
    private int assessmentId;
    private String assessmentName;
    private long goalStartDate;
    private long goalEndDate;
    private int courseId;
    private String assessmentTitle;




    @Override
    public String toString() {
        return "Assessment Details: " +
                "assessmentId: " + assessmentId +
                " assessmentName: " + assessmentName +
                " assessmentTitle " + assessmentTitle +
                " goalStartDate: " + goalStartDate +
                " goalEndDate: " + goalEndDate +
                " courseId: " + courseId;

    }

    public AssessmentEntity (int assessmentId, String assessmentName, String assessmentTitle, long goalStartDate, long goalEndDate, int courseId) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.assessmentTitle = assessmentTitle;
        this.goalStartDate = goalStartDate;
        this.goalEndDate = goalEndDate;
        this.courseId = courseId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public long getGoalStartDate() {
        return goalStartDate;
    }

    public long getGoalEndDate() {
        return goalEndDate;
    }

    public void setGoalEndDate(long goalEndDate) {
        this.goalEndDate = goalEndDate;
    }

    public void setGoalStartDate(long goalStartDate) {
        this.goalStartDate = goalStartDate;
    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
