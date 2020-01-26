package com.diana.televizor.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConvertor {
    @TypeConverter
    public static Date fromTimestap(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date != null ? date.getTime() : null;
    }
}
