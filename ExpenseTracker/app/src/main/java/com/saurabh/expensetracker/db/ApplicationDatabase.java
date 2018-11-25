package com.saurabh.expensetracker.db;

import com.saurabh.expensetracker.db.dao.UserCredentialDao;
import com.saurabh.expensetracker.db.entities.UserCredential;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserCredential.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract UserCredentialDao userCredentialDao();
}
