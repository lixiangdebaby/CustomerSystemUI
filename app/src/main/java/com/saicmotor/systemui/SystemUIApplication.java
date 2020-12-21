package com.saicmotor.systemui;

import android.app.Application;
import android.util.Log;

public class SystemUIApplication extends Application {
    private static SystemUIApplication mInstance = null;
    private static final String TAG = SystemUIApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(TAG," onCreate mInstance = "+mInstance);
    }
    public static SystemUIApplication getInstance(){
        return mInstance;
    }
}
