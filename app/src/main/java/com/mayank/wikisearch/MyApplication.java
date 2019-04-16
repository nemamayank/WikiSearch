package com.mayank.wikisearch;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication MY_WIKI_APP;
    private Activity currentActivity;

    @Override
    public void onCreate() {
        MY_WIKI_APP = this;
        super.onCreate();
    }

    public static MyApplication GET_MY_WIKI_APP_INSTANCE() {
        return MY_WIKI_APP;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }
}
