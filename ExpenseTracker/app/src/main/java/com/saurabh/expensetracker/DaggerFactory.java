package com.saurabh.expensetracker;

import android.content.Context;

import com.saurabh.expensetracker.di.component.AppContextComponent;
import com.saurabh.expensetracker.di.component.DaggerAppContextComponent;
import com.saurabh.expensetracker.di.module.ApplicationContextModule;

public class DaggerFactory {

    private static AppContextComponent appContextComponent;

    public static void initialize(Context context) {
        appContextComponent = DaggerAppContextComponent.builder().applicationContextModule(new ApplicationContextModule(context)).build();
    }

    public static AppContextComponent getAppContextComponent() {
        return appContextComponent;
    }
}
