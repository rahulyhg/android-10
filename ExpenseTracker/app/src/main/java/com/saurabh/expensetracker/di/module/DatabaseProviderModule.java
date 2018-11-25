package com.saurabh.expensetracker.di.module;

import com.saurabh.expensetracker.db.DatabaseProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseProviderModule {

    @Singleton
    @Provides
    public DatabaseProvider getDatabaseProvider() {
        return new DatabaseProvider();
    }
}
