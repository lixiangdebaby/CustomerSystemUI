package com.saicmotor.systemui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.saicmotor.systemui.service.SaicSystemUIService;

import static android.content.Context.WINDOW_SERVICE;

public class NaviPanelUI {
    private WindowManager mWindowManage;
    private WindowManager.LayoutParams mLayoutParams;
    private View displayView;
    private ImageButton mHomeBtn;
    public static boolean mIsStarted = false;
    private Context mContext = SystemUIApplication.getInstance();
    private static NaviPanelUI mNaviPanelUI;

    public static NaviPanelUI getInstance(Context context){
        if(mNaviPanelUI == null){
            mNaviPanelUI = new NaviPanelUI(context);
        }
        return  mNaviPanelUI;
    }
    public NaviPanelUI(Context context){
        mContext =  context;
        createFloatingView();
        showFloatingWindow();
    }
    private void createFloatingView(){
        mIsStarted = true;
        mWindowManage = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = 800;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }
    private void showFloatingWindow() {
        if(Settings.canDrawOverlays(mContext)){
            LayoutInflater layoutInflater =LayoutInflater.from(mContext);
            displayView = layoutInflater.inflate(R.layout.activity_main_test2,null);
            displayView.setOnTouchListener(new NaviPanelUI.FloatingOnTouchListener());
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
                   /* int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    mLayoutParams.height = nowY;
                    if(mLayoutParams.height <= 20){
                        mLayoutParams.height = 20;
                    }
                    if(mLayoutParams.height >= 800){
                        mLayoutParams.height = 800;
                    }
                    mWindowManage.updateViewLayout(view, mLayoutParams);*/
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
