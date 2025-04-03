package androidsamples.java.journalapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.sql.Date;
import java.sql.Time;

@RunWith(RobolectricTestRunner.class)
public class JournalViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private JournalViewModel journalViewModel;

    @Mock
    private JournalRepository mockRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        JournalRepository.setInstance(mockRepository);
        journalViewModel = new JournalViewModel();
    }

    @Test
    public void testVerifyThrowsExceptionIfDateNotSet() {
        journalViewModel.setTitle("Sample Title");
        journalViewModel.setStartTime(new Time(9, 0, 0));
        journalViewModel.setEndTime(new Time(10, 0, 0));

        assertThrows(IllegalStateException.class, () -> journalViewModel.verify());
    }

    @Test
    public void testVerifyThrowsExceptionIfStartTimeNotSet() {
        journalViewModel.setDate(Date.valueOf("2024-10-28"));
        journalViewModel.setTitle("Sample Title");
        journalViewModel.setEndTime(new Time(10, 0, 0));

        assertThrows(IllegalStateException.class, () -> journalViewModel.verify());
    }

    @Test
    public void testVerifyThrowsExceptionIfEndTimeNotSet() {
        journalViewModel.setDate(Date.valueOf("2024-10-28"));
        journalViewModel.setTitle("Sample Title");
        journalViewModel.setStartTime(new Time(9, 0, 0));

        assertThrows(IllegalStateException.class, () -> journalViewModel.verify());
    }

    @Test
    public void testVerifyThrowsExceptionIfEndTimeBeforeStartTime() {
        journalViewModel.setDate(Date.valueOf("2024-10-28"));
        journalViewModel.setTitle("Sample Title");
        journalViewModel.setStartTime(new Time(10, 0, 0));
        journalViewModel.setEndTime(new Time(9, 0, 0));

        assertThrows(IllegalStateException.class, () -> journalViewModel.verify());
    }

    @Test
    public void testInsertEntry() {
        // Arrange
        journalViewModel.setTitle("Test Title");
        journalViewModel.setDate(Date.valueOf("2024-10-28"));
        journalViewModel.setStartTime(new Time(9, 0, 0));
        journalViewModel.setEndTime(new Time(10, 0, 0));

        // Act
        journalViewModel.insertEntry();

        // Assert
        verify(mockRepository).insert(any(JournalEntry.class));
    }

    @Test
    public void testSaveEntry() {
        // Arrange
        //UUID entryId = UUID.randomUUID();
        JournalEntry mockEntry = new JournalEntry("Original Title", Date.valueOf("2024-10-27"), new Time(9, 0, 0), new Time(10, 0, 0));

        when(mockRepository.getEntry(mockEntry.getmUid())).thenReturn(new MutableLiveData<>(mockEntry));
        journalViewModel.loadEntry(mockEntry.getmUid());

        journalViewModel.setmJournalEntry(mockEntry);
        journalViewModel.setTitle("Updated Title");
        journalViewModel.setDate(Date.valueOf("2024-10-28"));
        journalViewModel.setStartTime(new Time(10, 0, 0));
        journalViewModel.setEndTime(new Time(11, 0, 0));

        // Act
        journalViewModel.saveEntry();

        // Assert
        assertEquals("Updated Title", mockEntry.getmTitle());
        assertEquals(Date.valueOf("2024-10-28"), mockEntry.getmDate());
        assertEquals(new Time(10, 0, 0), mockEntry.getmStartTime());
        assertEquals(new Time(11, 0, 0), mockEntry.getmEndTime());

        verify(mockRepository).update(mockEntry);
    }

    @Test
    public void testReset() {
        journalViewModel.setTitle("Test Title");
        journalViewModel.setDate(Date.valueOf("2024-10-28"));
        journalViewModel.setStartTime(new Time(9, 0, 0));
        journalViewModel.setEndTime(new Time(10, 0, 0));

        journalViewModel.reset();

        //assertNull(journalViewModel.getTitle());
        assertNull(journalViewModel.getDate());
        assertNull(journalViewModel.getStartTime());
        assertNull(journalViewModel.getEndTime());
    }
    @Test
    public void testDeleteEntry() {
        // Arrange
        //UUID entryId = UUID.randomUUID();
        JournalEntry mockEntry = new JournalEntry("Title to Delete", Date.valueOf("2024-10-28"), new Time(9, 0, 0), new Time(10, 0, 0));

        // Load the entry to set it in ViewModel
        when(mockRepository.getEntry(mockEntry.getmUid())).thenReturn(new MutableLiveData<>(mockEntry));
        journalViewModel.loadEntry(mockEntry.getmUid());

        // Act
        journalViewModel.deleteEntry(mockEntry);

        // Assert
        verify(mockRepository).delete(mockEntry);
    }
}
