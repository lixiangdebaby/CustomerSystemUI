package com.saicmotor.systemui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.saicmotor.systemui.slidinguppanel.SlidingUpPanelLayout;

import static android.content.Context.WINDOW_SERVICE;

public class NaviPanelUI {
    private WindowManager mWindowManage;
    private WindowManager.LayoutParams mLayoutParams;
    private SlidingUpPanelLayout mDisplayView;
    private ImageButton mHomeBtn;
    public static boolean mIsStarted = false;
    private Context mContext = SystemUIApplication.getInstance();
    private static NaviPanelUI mNaviPanelUI;
    private static final String TAG = NaviPanelUI.class.getSimpleName();
    private ViewGroup.LayoutParams mDispalyViewLayoutParams;
    private LinearLayout mLinear_sliddown;

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
        mLayoutParams.height = 60;

        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }
    private void showFloatingWindow() {
        if(Settings.canDrawOverlays(mContext)){
            LayoutInflater layoutInflater =LayoutInflater.from(mContext);
            mDisplayView = (SlidingUpPanelLayout)layoutInflater.inflate(R.layout.panel_view_layout,null);
            Log.d(TAG,"mIsExpand = "+getPanelState());
            mDispalyViewLayoutParams = mDisplayView.getLayoutParams();
            mDisplayView.setOnTouchListener(new NaviPanelUI.FloatingOnTouchListener());
            mWindowManage.addView(mDisplayView,mLayoutParams);
            mLinear_sliddown = (LinearLayout)mDisplayView.findViewById(R.id.linear_sliddown);
            if(mLinear_sliddown != null){
                mLinear_sliddown.setOnTouchListener(new PanelDownOnTouchListener());
            }
            mDisplayView.addPanelSlideListener(new DisplayViewPanelStateLister());
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
                    Log.d(TAG,"x = "+x+"  y = "+y);
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
    class  PanelDownOnTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.d(TAG,"getPanelState() = "+getPanelState()+ " motionEvent.event = "+motionEvent.getAction());
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                if(getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    setNaviPanelUICollaps(true);
                }
            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                if(getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
                    setNaviPanelUICollaps(false);
                }
            }
            return false;
        }
    }
    public void setNaviPanelUICollaps(boolean isExpand){
        Log.d(TAG,"isExpand() = "+isExpand);
        if(isExpand){
            mLayoutParams.height = 750;
            mWindowManage.updateViewLayout(mDisplayView, mLayoutParams);
        }else{
            mLayoutParams.height = 60;
            mWindowManage.updateViewLayout(mDisplayView, mLayoutParams);
        }
    }
    public SlidingUpPanelLayout.PanelState getPanelState(){
        return mDisplayView.getPanelState();
    }
    class DisplayViewPanelStateLister implements SlidingUpPanelLayout.PanelSlideListener{
        @Override
        public void onPanelSlide(View panel, float slideOffset) {

        }
        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                setNaviPanelUICollaps(false);
            }
        }
    }
}
