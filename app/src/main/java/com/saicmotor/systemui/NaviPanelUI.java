package com.saicmotor.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
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

import com.saicmotor.systemui.function.BlueToothFunction;
import com.saicmotor.systemui.function.SceneModeChange;
import com.saicmotor.systemui.function.WifiFunction;
import com.saicmotor.systemui.slidinguppanel.SlidingUpPanelLayout;
import com.saicmotor.systemui.utils.StartActivityUtils;

import static android.content.Context.WINDOW_SERVICE;

public class NaviPanelUI {
    private WindowManager mWindowManage;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager.LayoutParams mStatusBarLayout;
    private SlidingUpPanelLayout mDisplayView;
    private ImageButton mHomeBtn;
    private ImageButton mBlueToothBtn;
    private ImageButton mWifiBtn;
    public static boolean mIsStarted = false;
    private Context mContext = SystemUIApplication.getInstance();
    private static NaviPanelUI mNaviPanelUI;
    private static final String TAG = NaviPanelUI.class.getSimpleName();
    private ViewGroup.LayoutParams mDispalyViewLayoutParams;
    private LinearLayout mLinear_sliddown;
    private LinearLayout mDragView;
    private LinearLayout mSceneModeView;
    private Drawable mDragViewdrawable;
    private static final int COLLAPSED_MSG = 10;
    private static final int EXPANDED_MSG = 12;
    private UpdateUIHandler mUpdateUIHandler;
    private StatusBarUI mStatusBarUI;
    private static final int SYSTEMUI_PANEL_WINDOW_HEIGHT_DEFAULT = 27;
    private static final int SYSTEMUI_PANEL_WINDOW_HEIGHT_EXPANDED = 750;
    private static final int SYSTEMUI_STATUSBAR_WINDOW_HEIGHT_DEFAULT = 56;
    public static NaviPanelUI getInstance(Context context){
        if(mNaviPanelUI == null){
            mNaviPanelUI = new NaviPanelUI(context);
        }
        return  mNaviPanelUI;
    }
    void init(){
        mDragViewdrawable = mContext.getResources().getDrawable(R.drawable.dragview_bg);
    }
    public NaviPanelUI(Context context){
        mContext =  context;
        mWindowManage = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        mUpdateUIHandler = new UpdateUIHandler();
        createFloatingView();
        showFloatingWindow();
        createStatusBarWindow(mWindowManage);
        showStatusBarView();
        init();
    }
    private void createFloatingView(){
        mIsStarted = true;
        mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = 2024;// WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;//NAVIGATION_BAR_PANEL=2024
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
        mLayoutParams.height = SYSTEMUI_PANEL_WINDOW_HEIGHT_DEFAULT;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }
    private void createStatusBarWindow(WindowManager windowManager){
        if(windowManager == null){
            return;
        }
        mStatusBarLayout = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mStatusBarLayout.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;//NAVIGATION_BAR_PANEL=2024
        } else {
            mStatusBarLayout.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mStatusBarLayout.format = PixelFormat.RGBA_8888;
        mStatusBarLayout.gravity = Gravity.LEFT | Gravity.TOP;
        mStatusBarLayout.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mStatusBarLayout.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mStatusBarLayout.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mStatusBarLayout.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mStatusBarLayout.width = WindowManager.LayoutParams.MATCH_PARENT;
        mStatusBarLayout.height = SYSTEMUI_STATUSBAR_WINDOW_HEIGHT_DEFAULT;
        mStatusBarLayout.x = 0;
        mStatusBarLayout.y = SYSTEMUI_PANEL_WINDOW_HEIGHT_DEFAULT;
    }
    private void showFloatingWindow() {
        if(Settings.canDrawOverlays(mContext)){
            LayoutInflater layoutInflater =LayoutInflater.from(mContext);
            mDisplayView = (SlidingUpPanelLayout)layoutInflater.inflate(R.layout.panel_view_layout,null);
            Log.d(TAG,"mIsExpand = "+getPanelState());
            mDragView = (LinearLayout)mDisplayView.findViewById(R.id.dragView);
            mDispalyViewLayoutParams = mDisplayView.getLayoutParams();
            //mDisplayView.setOnTouchListener(new NaviPanelUI.FloatingOnTouchListener());
            mWindowManage.addView(mDisplayView,mLayoutParams);
            mLinear_sliddown = (LinearLayout)mDisplayView.findViewById(R.id.linear_sliddown);
            if(mLinear_sliddown != null){
                mLinear_sliddown.setOnTouchListener(new PanelDownOnTouchListener());
            }
            mDisplayView.addPanelSlideListener(new DisplayViewPanelStateLister());
            mHomeBtn = (ImageButton)mDisplayView.findViewById(R.id.btn_home);
            if(mHomeBtn != null){
                mHomeBtn.setOnClickListener(new HomeBtnOnClickListener());
            }
            mBlueToothBtn=(ImageButton)mDisplayView.findViewById(R.id.btn_bt);
            mBlueToothBtn.setOnClickListener(new BlueToothFunction());
            mWifiBtn=(ImageButton)mDisplayView.findViewById(R.id.btn_wifi);
            mWifiBtn.setOnClickListener(new WifiFunction());
            mSceneModeView = (LinearLayout)mDisplayView.findViewById(R.id.linear_layout_mode1);
            mSceneModeView.setOnClickListener(new SceneModeChange());
        }
    }
    private void showStatusBarView(){
        if(Settings.canDrawOverlays(mContext)) {
            LayoutInflater layoutInflater =LayoutInflater.from(mContext);
            if (mDisplayView != null) {
                mStatusBarUI = new StatusBarUI(mContext, layoutInflater);
                if(mStatusBarLayout !=null && mStatusBarUI!=null){
                    mWindowManage.addView(mStatusBarUI.getStatusBarView(),mStatusBarLayout);
                }
            }
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
                    //setNaviPanelUICollaps(false);
                }
            }
            return false;
        }
    }
    public void setNaviPanelUICollaps(boolean isExpand){
        Log.d(TAG,"isExpand() = "+isExpand);
        if(isExpand){
            mLayoutParams.height = SYSTEMUI_PANEL_WINDOW_HEIGHT_EXPANDED;
            mWindowManage.updateViewLayout(mDisplayView, mLayoutParams);
        }else{
            mLayoutParams.height = SYSTEMUI_PANEL_WINDOW_HEIGHT_DEFAULT;
            mWindowManage.updateViewLayout(mDisplayView, mLayoutParams);
        }
    }
    public void setDisplayViewBgColor(float offset){
        int alpha = (int)(offset*255);
        mDragViewdrawable.setAlpha(alpha);
        mDragView.setBackground(mDragViewdrawable);
    }
    public SlidingUpPanelLayout.PanelState getPanelState(){
        return mDisplayView.getPanelState();
    }
    class DisplayViewPanelStateLister implements SlidingUpPanelLayout.PanelSlideListener{
        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            Log.d(TAG,"slideOffset = "+slideOffset);
            setDisplayViewBgColor(slideOffset);
        }
        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                setNaviPanelUICollaps(false);
            }else if(newState == SlidingUpPanelLayout.PanelState.EXPANDED){

            }
        }
    }
    class HomeBtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            StartActivityUtils.startActivityHome(mContext);
            mUpdateUIHandler.removeMessages(COLLAPSED_MSG);
            mUpdateUIHandler.sendEmptyMessage(COLLAPSED_MSG);
        }
    }
    class UpdateUIHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            int i = msg.what;
            if(i == COLLAPSED_MSG){
                mDisplayView.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }else if(i == EXPANDED_MSG){
                mDisplayView.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        }
    }

}
