package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    protected UUID mUid;

    @ColumnInfo(name = "title")
    protected String mTitle;

    @ColumnInfo(name = "date")
    protected Date mDate;

    @ColumnInfo(name = "startTime")
    protected Time mStartTime;

    @ColumnInfo(name = "endTime")
    protected Time mEndTime;

    // Constructor with title, date, startTime, and endTime
    public JournalEntry(@NonNull String title, Date date, Time startTime, Time endTime) {
        mUid = UUID.randomUUID();
        mTitle = title;
        mDate = date;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    @NonNull
    public UUID getmUid() {
        return mUid;
    }

    public void setmUid(@NonNull UUID mUid) {
        this.mUid = mUid;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public Time getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(Time mStartTime) {
        this.mStartTime = mStartTime;
    }

    public Time getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(Time mEndTime) {
        this.mEndTime = mEndTime;
    }
}
