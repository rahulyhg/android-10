package com.saurabh.expensetracker.db.converters;

import androidx.databinding.ObservableField;
import androidx.room.TypeConverter;

public class ObservableStringConverter {

    @TypeConverter
    public static String fromObservableField(ObservableField<String> string) {
        return string.get();
    }

    @TypeConverter
    public static ObservableField<String> toObservableField(String string) {
        return new ObservableField<>(string);
    }
}
