package com.saurabh.expensetracker.db;

import android.content.Context;

import com.saurabh.expensetracker.di.interfaces.ApplicationContext;

import javax.inject.Inject;

import androidx.room.Room;

public class DatabaseProvider {
    private final static String DATABASE_NAME = "application-db";

    @Inject
    public ApplicationDatabase getApplicationDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,ApplicationDatabase.class,DATABASE_NAME).fallbackToDestructiveMigration().build();
    }
}
