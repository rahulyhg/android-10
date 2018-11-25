package com.saurabh.expensetracker.di.module;

import android.content.Context;

import com.saurabh.expensetracker.di.interfaces.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationContextModule {

    private Context mContext;

    public ApplicationContextModule(Context mContext) {
        this.mContext = mContext;
    }

    @ApplicationContext
    @Provides
    public Context getContext() {
        return mContext.getApplicationContext();
    }
}
