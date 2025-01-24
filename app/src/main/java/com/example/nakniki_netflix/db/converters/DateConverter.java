package com.example.nakniki_netflix.db.converters;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.getDefault());

    @TypeConverter
    public static String fromDate(Date date) {
        return date.toString();
    }

    @TypeConverter
    public static Date fromString(String val) {
        LocalDate parse = (LocalDate) formatter.parse(val);
        return Date.from(Instant.from(parse));
    }
}
