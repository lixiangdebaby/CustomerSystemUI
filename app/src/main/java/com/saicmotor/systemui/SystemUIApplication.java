package com.saicmotor.systemui;

import android.app.Application;

public class SystemUIApplication extends Application {
    private static SystemUIApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static SystemUIApplication getInstance(){
        return mInstance;
    }
}
