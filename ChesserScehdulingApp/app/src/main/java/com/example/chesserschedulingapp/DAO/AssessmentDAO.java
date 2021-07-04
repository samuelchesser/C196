package com.example.chesserschedulingapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.chesserschedulingapp.Entity.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Query("DELETE FROM assessments")
    void removeAllAssessments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Delete
    void delete(AssessmentEntity assessmentEntity);

    @Query("SELECT * FROM assessments ORDER BY assessmentId ASC")
    List<AssessmentEntity> getAllAssessments();
}
