package androidsamples.java.journalapp;

import android.app.Application;

public class JournalApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JournalRepository.init(this);
    }
}
