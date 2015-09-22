package com.jdamcd.threads;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class BackgroundService extends IntentService {

    private static final String NAME = "BackgroundWork";

    public BackgroundService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(NAME, Example.blocking());
    }

}
