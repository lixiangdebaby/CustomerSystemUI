package com.saicmotor.systemui.function;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.saicmotor.systemui.R;
import com.saicmotor.systemui.SystemUIApplication;

public class BlueToothFunction implements View.OnClickListener{
    boolean mIsBtSwitch = false;
    Context mContext = SystemUIApplication.getInstance();
    public static ImageButton mBlueToothBtn;
    @Override
    public void onClick(View view) {
        if(view instanceof ImageButton){
            if(!mIsBtSwitch) {
                view.setBackground(mContext.getDrawable(R.drawable.systemui_imagebutton_background_opened));
                mIsBtSwitch = true;
            }else{
                view.setBackground(mContext.getDrawable(R.drawable.systemui_imagebutton_background_normal));
                mIsBtSwitch = false;
            }
        }
    }
}
