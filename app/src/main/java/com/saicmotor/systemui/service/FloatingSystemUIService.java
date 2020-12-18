package com.saicmotor.systemui.service;

import android.app.Service;
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

import com.saicmotor.systemui.R;
import com.saicmotor.systemui.utils.StartActivityUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class FloatingSystemUIService extends Service {
    public static boolean mIsStarted = false;
    private static final String TAG = FloatingSystemUIService.class.getSimpleName();
    private WindowManager mWindowManage;
    private WindowManager.LayoutParams mLayoutParams;
    private View displayView;
    private ImageButton mHomeBtn;
    public FloatingSystemUIService() {
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
        mIsStarted = true;
        mWindowManage = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        //mLayoutParams.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = 800;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
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
    private void showFloatingWindow() {
        if(Settings.canDrawOverlays(this)){
            LayoutInflater layoutInflater =LayoutInflater.from(this);
            displayView = layoutInflater.inflate(R.layout.systemui_main_layout,null);
            displayView.setOnTouchListener(new FloatingOnTouchListener());
            mWindowManage.addView(displayView,mLayoutParams);
        }
    }
    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    mLayoutParams.height = nowY;
                    if(mLayoutParams.height <= 20){
                        mLayoutParams.height = 20;
                    }
                    if(mLayoutParams.height >= 800){
                        mLayoutParams.height = 800;
                    }
                    mWindowManage.updateViewLayout(view, mLayoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}