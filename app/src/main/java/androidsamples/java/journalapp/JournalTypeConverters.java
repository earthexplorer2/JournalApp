package androidsamples.java.journalapp;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

public class JournalTypeConverters {

    // Convert UUID to String
    @TypeConverter
    public static String fromUUID(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }

    // Convert String to UUID
    @TypeConverter
    public static UUID toUUID(String uuid) {
        return uuid == null ? null : UUID.fromString(uuid);
    }

    // Convert Date to Long
    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    // Convert Long to Date
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    // Convert Time to Long
    @TypeConverter
    public static Long fromTime(Time time) {
        return time == null ? null : time.getTime();
    }

    // Convert Long to Time
    @TypeConverter
    public static Time toTime(Long timestamp) {
        return timestamp == null ? null : new Time(timestamp);
    }
}