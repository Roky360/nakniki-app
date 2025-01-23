package com.example.nakniki_netflix.db;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class Converters {
    @TypeConverter
    public static String fromList(List<String> list) {
        return String.join(",", list);
    }

    @TypeConverter
    public static List<String> fromString(String val) {
        return Arrays.asList(val.split(","));
    }
}
