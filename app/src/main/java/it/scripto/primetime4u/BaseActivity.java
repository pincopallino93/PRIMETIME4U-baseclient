package it.scripto.primetime4u;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Base actionbar activity with logging.
 *
 * @author pincopallino93
 * @version 1.0
 * @since 08 nov 2014
 */
public abstract class BaseActivity extends ActionBarActivity {

    public final String TAG = getTagLog();

    protected abstract String getTagLog();

    protected abstract int getLayoutResourceId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResourceId());

        if (BuildConfig.DEBUG) Log.v(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) Log.v(TAG, "onStart");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        if (BuildConfig.DEBUG) Log.v(TAG, "onRestart");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) Log.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (BuildConfig.DEBUG) Log.v(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) Log.v(TAG, "onDestroy");
    }
}