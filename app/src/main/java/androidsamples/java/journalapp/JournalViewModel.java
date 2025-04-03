package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

public class JournalViewModel extends ViewModel {
    private final JournalRepository mRepository;
    private JournalEntry mJournalEntry;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String title;
    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();
    public JournalViewModel() {
        mRepository = JournalRepository.getInstance();
    }
    LiveData<JournalEntry> getEntryLiveData() {
        return Transformations.switchMap(entryIdLiveData, mRepository::getEntry);
    }
    void saveEntry(JournalEntry entry) { mRepository.update(entry); }
    void loadEntry(UUID entryId) { entryIdLiveData.setValue(entryId); }
    public LiveData<List<JournalEntry>> getAllEntries() {
        return mRepository.getAllEntries();
    }
    public void insert(JournalEntry entry) {
        mRepository.insert(entry);
    }
    public void deleteEntry(JournalEntry entry) {
        mRepository.delete(entry);
    }

    public Date getDate() {
        if(date==null){
            if(mJournalEntry==null) return null;
            return mJournalEntry.getmDate();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        if(startTime==null) {
            if(mJournalEntry==null) return null;
            return mJournalEntry.getmStartTime();
        }
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        if (endTime==null){
            if(mJournalEntry==null) return null;
            return mJournalEntry.getmEndTime();
        }
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void verify(){
        if (this.date==null){
            throw new IllegalStateException("Date not set");
        }else if(startTime==null){
            throw new IllegalStateException("Start Time not set");
        }else if(endTime==null){
            throw new IllegalStateException("End Time not set");
        }
        if (endTime.before(startTime)){
            throw new IllegalStateException("Start Time should occur after End Time");
        }
        if(title=="" || title==null){
            throw new IllegalStateException("Title cannot be empty");
        }
    }
    public void insertEntry(){
        verify();
        JournalEntry entry = new JournalEntry(title,date,startTime,endTime);
        reset();
        this.insert(entry);
    }

    public void saveEntry(){
        if (mJournalEntry==null) throw new IllegalStateException("No entry to save");
        if(date==null) date = mJournalEntry.getmDate();
        if(startTime==null) startTime = mJournalEntry.getmStartTime();
        if(endTime==null) endTime = mJournalEntry.getmEndTime();
        verify();
        //JournalEntry entry = getEntryLiveData().getValue();
        mJournalEntry.setmTitle(title);
        mJournalEntry.setmDate(date);
        mJournalEntry.setmStartTime(startTime);
        mJournalEntry.setmEndTime(endTime);
        reset();
        saveEntry(mJournalEntry);
        mJournalEntry = null;
    }

    public JournalEntry getmJournalEntry() {
        return mJournalEntry;
    }

    public void setmJournalEntry(JournalEntry mJournalEntry) {
        this.mJournalEntry = mJournalEntry;
    }

    public void reset(){
        //loadEntry(null);
        //mJournalEntry = null;
        title = "";
        date = null;
        startTime = null;
        endTime = null;
    }
}
