package com.example.nakniki_netflix.db.converters;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListConverter {
    @TypeConverter
    public static String fromList(List<String> list) {
        return String.join(",", list);
    }

    @TypeConverter
    public static List<String> fromString(String val) {
        return Arrays.asList(val.split(","));
    }
}
