package com.saicmotor.systemui.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.saicmotor.systemui.NaviPanelUI;
import com.saicmotor.systemui.R;
import com.saicmotor.systemui.SystemUIApplication;
import com.saicmotor.systemui.utils.StartActivityUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class SaicSystemUIService extends Service {
    private static final String TAG = SaicSystemUIService.class.getSimpleName();
    private Context mContext = SystemUIApplication.getInstance();
    private NaviPanelUI mNaviPanelUI;
    public SaicSystemUIService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"FloatingSystemUIService start");
        mNaviPanelUI =  NaviPanelUI.getInstance(getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }


}