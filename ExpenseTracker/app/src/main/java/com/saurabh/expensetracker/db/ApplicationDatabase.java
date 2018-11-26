package com.saurabh.expensetracker.db;

import com.saurabh.expensetracker.db.converters.ObservableStringConverter;
import com.saurabh.expensetracker.db.dao.UserCredentialDao;
import com.saurabh.expensetracker.db.entities.UserCredential;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {UserCredential.class}, version = 1, exportSchema = false)
@TypeConverters({ObservableStringConverter.class})
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract UserCredentialDao userCredentialDao();
}
