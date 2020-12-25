package com.saicmotor.systemui.function;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saicmotor.systemui.R;
import com.saicmotor.systemui.SystemUIApplication;

import java.util.ArrayList;

public class SceneModeChange implements View.OnClickListener{
    private static final String TAG = SceneModeChange.class.getSimpleName();
    private boolean mIsSceneModeChange = false;
    Context mContext = SystemUIApplication.getInstance();
    public static  LinearLayout mSceneModeView1;
    public static  LinearLayout mSceneModeView2;
    public static  LinearLayout mSceneModeView3;
    public static  LinearLayout mSceneModeView4;
    public static ArrayList<SceneModeStatus> mSceneModeStatus;
    public void setSceneModeStatus(int currentIndex,boolean isOpen,ImageView imageView,TextView textView){
        if(isOpen){
            imageView.setBackground(mContext.getDrawable(R.drawable.scene_mode_enabled_bg));
            textView.setTextColor(mContext.getResources().getColor(R.color.systemui_btn_select));
            textView.setAlpha(1);
        }else{
            imageView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            textView.setTextColor(mContext.getResources().getColor(R.color.systemui_scene_text_normal_color));
            textView.setAlpha(0.4f);
        }
        mSceneModeStatus.get(currentIndex).sceneStatus = isOpen;
    }
    public void setOthersCeneModeStatus(int currentIndex,boolean currentModeStatus){
        for(int i = 0;i<mSceneModeStatus.size();i++){
            ImageView imageViewTemp = (ImageView) ((LinearLayout) (mSceneModeStatus.get(i).sceneLayout)).getChildAt(0);
            TextView textViewTemp = (TextView)((LinearLayout) (mSceneModeStatus.get(i).sceneLayout)).getChildAt(1);
            if(i == currentIndex){
                continue;
            }
            setSceneModeStatus(currentIndex,currentModeStatus,imageViewTemp,textViewTemp);
        }
    }
    @Override
    public void onClick(View view) {
        int currentIndex = 0;
        int viewID = view.getId();
        Log.d(TAG,"viewID = "+viewID);
        for(int i = 0;i<mSceneModeStatus.size();i++){
            if(viewID == mSceneModeStatus.get(i).LinearLayoutId){
                currentIndex = i;
                Log.d(TAG,"currentIndex = "+currentIndex);
                break;
            }
        }
        if(view instanceof LinearLayout){
            ImageView imageView = (ImageView) ((LinearLayout) view).getChildAt(0);
            TextView textView = (TextView)((LinearLayout) view).getChildAt(1);
            Log.d(TAG,"mSceneModeStatus.get(currentIndex).sceneStatus = "+mSceneModeStatus.get(currentIndex).sceneStatus);
            if(mSceneModeStatus.get(currentIndex).sceneStatus){
                Log.d(TAG,"current sceneMode is true,please select the others");
            }else{
                setSceneModeStatus(currentIndex,!(mSceneModeStatus.get(currentIndex).sceneStatus),imageView,textView);
                setOthersCeneModeStatus(currentIndex,false);
            }
        }
    }
}
