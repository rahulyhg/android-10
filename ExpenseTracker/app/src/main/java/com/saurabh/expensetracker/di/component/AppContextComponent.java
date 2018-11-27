package com.saurabh.expensetracker.di.component;

import android.content.Context;

import com.saurabh.expensetracker.db.DatabaseProvider;
import com.saurabh.expensetracker.db.entities.UserCredential;
import com.saurabh.expensetracker.di.interfaces.ApplicationContext;
import com.saurabh.expensetracker.di.module.ApplicationContextModule;
import com.saurabh.expensetracker.di.module.DatabaseProviderModule;
import com.saurabh.expensetracker.di.module.UserCredentialProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationContextModule.class, DatabaseProviderModule.class, UserCredentialProvider.class})
public interface AppContextComponent {
    @ApplicationContext
    Context getContext();

    DatabaseProvider getDatabaseProvider();

    UserCredential getUserCredential();
}
