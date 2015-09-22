package com.jdamcd.threads;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int LOADER_ID = 0;

    @Bind(R.id.textview_updates) TextView updateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_anr)
    void causeAnr() {
        updateUi(Example.blocking());
    }

    @OnClick(R.id.button_thread)
    void useThread() {
        new Thread() {
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                final String result = Example.blocking();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUi(result);
                    }
                });
            }
        }.start();
    }

    @OnClick(R.id.button_async_task)
    void useAsyncTask() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return Example.blocking();
            }

            @Override
            protected void onPostExecute(String result) {
                updateUi(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @OnClick(R.id.button_loader)
    void useLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<String>() {
            @Override
            public Loader<String> onCreateLoader(int id, Bundle args) {
                return new BackgroundLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(Loader<String> loader, String text) {
                updateUi(text);
            }

            @Override
            public void onLoaderReset(Loader<String> loader) {
                loader.reset();
            }
        });
    }

    @OnClick(R.id.button_intent_service)
    void useIntentService() {
        startService(new Intent(this, BackgroundService.class));
    }

    @OnClick(R.id.button_rx)
    void useRxJava() {
        Example.observable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UpdateSubscriber());
    }

    private void updateUi(String text) {
        updateView.setVisibility(View.VISIBLE);
        updateView.setText(text);
        updateView.setBackgroundColor(Util.generateRandomColor());
    }

    private class UpdateSubscriber extends DefaultSubscriber<String> {
        @Override
        public void onNext(String text) {
            updateUi(text);
        }
    }

    private static class BackgroundLoader extends AsyncTaskLoader<String> {

        public BackgroundLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public String loadInBackground() {
            return Example.blocking();
        }
    }

}
