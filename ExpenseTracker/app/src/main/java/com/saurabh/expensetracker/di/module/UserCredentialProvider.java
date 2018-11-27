package com.saurabh.expensetracker.di.module;

import com.saurabh.expensetracker.db.entities.UserCredential;

import dagger.Module;
import dagger.Provides;

@Module
public class UserCredentialProvider {
    @Provides
    public UserCredential getUserCredential() {
        return new UserCredential();
    }
}
