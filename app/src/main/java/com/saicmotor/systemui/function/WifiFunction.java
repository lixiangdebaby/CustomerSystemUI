package com.saicmotor.systemui.function;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.saicmotor.systemui.R;
import com.saicmotor.systemui.SystemUIApplication;

public class WifiFunction implements View.OnClickListener{
    boolean mIsWifiSwitch = false;
    Context mContext = SystemUIApplication.getInstance();
    public static ImageButton mWifiBtn;
    @Override
    public void onClick(View view) {
        if(view instanceof ImageButton){
            if(!mIsWifiSwitch) {
                view.setBackground(mContext.getDrawable(R.drawable.systemui_imagebutton_background_opened));
                mIsWifiSwitch = true;
            }else{
                view.setBackground(mContext.getDrawable(R.drawable.systemui_imagebutton_background_normal));
                mIsWifiSwitch = false;
            }
        }
    }
}
