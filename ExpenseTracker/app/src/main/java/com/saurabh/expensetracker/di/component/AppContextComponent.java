package com.saurabh.expensetracker.di.component;

import android.content.Context;

import com.saurabh.expensetracker.db.DatabaseProvider;
import com.saurabh.expensetracker.di.interfaces.ApplicationContext;
import com.saurabh.expensetracker.di.module.ApplicationContextModule;
import com.saurabh.expensetracker.di.module.DatabaseProviderModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationContextModule.class, DatabaseProviderModule.class})
public interface AppContextComponent {
    @ApplicationContext
    Context getContext();

    DatabaseProvider getDatabaseProvider();
}
