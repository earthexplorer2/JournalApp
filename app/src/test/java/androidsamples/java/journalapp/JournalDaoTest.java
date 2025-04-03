package androidsamples.java.journalapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Config(manifest=Config.NONE)
@RunWith(AndroidJUnit4.class)
public class JournalDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private JournalDatabase db;
    private JournalDao journalDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, JournalDatabase.class)
                .allowMainThreadQueries() // For testing purposes only
                .build();
        journalDao = db.journalDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertAndGetAllEntries() throws InterruptedException {
        JournalEntry entry = new JournalEntry("Test Title", Date.valueOf("2023-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"));
        journalDao.insert(entry);

        List<JournalEntry> entries = getValue(journalDao.getAllEntries());
        assertEquals(1, entries.size());
        assertEquals("Test Title", entries.get(0).getmTitle());
    }

    @Test
    public void testUpdateEntry() throws InterruptedException {
        JournalEntry entry = new JournalEntry("Initial Title", Date.valueOf("2023-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"));
        journalDao.insert(entry);

        entry.setmTitle("Updated Title");
        journalDao.update(entry);

        JournalEntry updatedEntry = getValue(journalDao.getEntry(entry.getmUid()));
        assertNotNull(updatedEntry);
        assertEquals("Updated Title", updatedEntry.getmTitle());
    }

    @Test
    public void testDeleteEntry() throws InterruptedException {
        JournalEntry entry = new JournalEntry("Delete Me", Date.valueOf("2023-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"));
        journalDao.insert(entry);

        journalDao.delete(entry);

        JournalEntry deletedEntry = getValue(journalDao.getEntry(entry.getmUid()));
        assertNull(deletedEntry);
    }

    @Test
    public void testGetEntryById() throws InterruptedException {
        JournalEntry entry = new JournalEntry("Find Me", Date.valueOf("2023-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"));
        journalDao.insert(entry);

        JournalEntry foundEntry = getValue(journalDao.getEntry(entry.getmUid()));
        assertNotNull(foundEntry);
        assertEquals("Find Me", foundEntry.getmTitle());
    }

    // Helper method to get the value from LiveData
    private static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}

