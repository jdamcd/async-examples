package com.jdamcd.threads;

import android.app.Application;
import android.util.Log;

public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, Util.getThreadInfo());
    }

}
