package com.example.chesserschedulingapp.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName="courses")
public class CourseEntity {
    @PrimaryKey
    private int courseId;

    private String courseName;
    private long startDate;
    private long endDate;
    private String status;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private String courseNotes;
    private int termId;

    @Override
    public String toString() {
        return "Course Details: " +
                "courseId: " + courseId +
                " courseName: " + courseName +
                " startDate: " + startDate +
                " endDate: " + endDate +
                " status: " + status +
                " mentorName " + mentorName +
                " mentorPhone " + mentorPhone +
                " mentorEmail " + mentorEmail +
                " courseNotes " + courseNotes +
                " termId " + termId;

    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public CourseEntity (int courseId, String courseName, long startDate, long endDate, String status, String mentorName, String mentorPhone, String mentorEmail, String courseNotes, int termId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseNotes = courseNotes;
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }
}
