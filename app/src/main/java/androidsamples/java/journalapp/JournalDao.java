package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface JournalDao {
    @Insert
    void insert(JournalEntry entry);

    @Query("SELECT * from journal_table ORDER BY date ASC")
    LiveData<List<JournalEntry>> getAllEntries();

    @Update
    void update(JournalEntry entry);

    @Query("SELECT * from journal_table WHERE id=(:id)")
    LiveData<JournalEntry> getEntry(UUID id);

    @Delete
    void delete(JournalEntry entry);
}