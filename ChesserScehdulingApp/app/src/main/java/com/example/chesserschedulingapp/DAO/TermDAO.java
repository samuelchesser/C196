package com.example.chesserschedulingapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.chesserschedulingapp.Entity.TermEntity;

import java.util.List;
@Dao
public interface TermDAO {
    @Query("SELECT * FROM terms ORDER BY termId ASC")
    List<TermEntity> getAllTerms();

    @Query("DELETE FROM terms")
    void removeAllTerms();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity term);

    //Do I need to pull the ID or anything, or does Rooms do that?
    @Delete
    void delete(TermEntity termEntity);
}
