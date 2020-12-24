package com.saicmotor.systemui.function;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saicmotor.systemui.R;
import com.saicmotor.systemui.SystemUIApplication;

public class SceneModeChange implements View.OnClickListener{
    private boolean mIsSceneModeChange = false;
    Context mContext = SystemUIApplication.getInstance();
    @Override
    public void onClick(View view) {
        if(view instanceof LinearLayout){
            ImageView imageView = (ImageView) ((LinearLayout) view).getChildAt(0);
            TextView textView = (TextView)((LinearLayout) view).getChildAt(1);
            if(!mIsSceneModeChange) {
                imageView.setBackground(mContext.getDrawable(R.drawable.scene_mode_enabled_bg));
                textView.setTextColor(mContext.getResources().getColor(R.color.systemui_btn_select));
                textView.setAlpha(1);
                mIsSceneModeChange = true;
            }else{
                imageView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                textView.setTextColor(mContext.getResources().getColor(R.color.systemui_scene_text_normal_color));
                textView.setAlpha(0.4f);
                mIsSceneModeChange = false;
            }
        }
    }
}
