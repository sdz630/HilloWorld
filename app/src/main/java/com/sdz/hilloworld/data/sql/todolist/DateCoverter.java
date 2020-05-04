package com.sdz.hilloworld.data.sql.todolist;

import androidx.room.TypeConverter;

import java.sql.Date;

public class DateCoverter {
    @TypeConverter
    public static Date revertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long converterDate(Date value) {
        return value.getTime();
    }
}
