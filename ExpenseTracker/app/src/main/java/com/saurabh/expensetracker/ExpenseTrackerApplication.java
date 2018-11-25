package com.saurabh.expensetracker;

import android.app.Application;

import com.saurabh.expensetracker.di.component.DaggerAppContextComponent;
import com.saurabh.expensetracker.di.module.ApplicationContextModule;

public class ExpenseTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerFactory.initialize(this);
    }

}
