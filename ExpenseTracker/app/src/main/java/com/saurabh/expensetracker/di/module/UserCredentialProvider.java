package com.saurabh.expensetracker.di.module;

import com.saurabh.expensetracker.db.entities.UserCredential;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserCredentialProvider {
    @Singleton
    @Provides
    public UserCredential getUserCredential() {
        return new UserCredential();
    }
}
