package com.saicmotor.systemui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class StatusBarUI {
    RelativeLayout mStatuBarView;
    public StatusBarUI(Context context, LayoutInflater layoutInflater){
        if(context!=null && layoutInflater!=null){
            createStatusBarView(context,layoutInflater);
        }
    }
    public  void createStatusBarView(Context context,LayoutInflater layoutInflater){
        mStatuBarView = (RelativeLayout)layoutInflater.inflate(R.layout.systemui_status_bar_layout,null);
    }
    public  RelativeLayout getStatusBarView(){
        return mStatuBarView;
    }
}
