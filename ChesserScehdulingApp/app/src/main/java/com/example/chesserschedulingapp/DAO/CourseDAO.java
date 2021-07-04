package com.example.chesserschedulingapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.chesserschedulingapp.Entity.CourseEntity;

import java.util.List;

@Dao
public interface CourseDAO {
    @Query("SELECT * FROM courses ORDER BY courseId ASC")
    List<CourseEntity> getAllCourses();

    @Query("DELETE FROM terms")
    void removeAllCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Delete
    void delete(CourseEntity courseEntity);
}
