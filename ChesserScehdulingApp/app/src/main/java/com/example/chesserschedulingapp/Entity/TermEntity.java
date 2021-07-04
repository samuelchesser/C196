package com.example.chesserschedulingapp.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

import java.sql.Date;

@Entity(tableName="terms")
public class TermEntity {
    @PrimaryKey
    private int termId;

    private String termName;
    private long startDate;
    private long endDate;

    @Override
    public String toString() {
        return "Term Details: " +
                "termId: " + termId +
                " termName: " + termName +
                " startDate: " + startDate +
                " endDate: " + endDate;
    }

    public TermEntity (int termId, String termName, long startDate, long endDate) {
        this.termId = termId;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
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
}
