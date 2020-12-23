package com.saicmotor.systemui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.saicmotor.systemui.constant.CONST;
import com.saicmotor.systemui.service.SaicSystemUIService;

public class SystemUIApplication extends Application {
    private static SystemUIApplication mInstance = null;
    private static final String TAG = SystemUIApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(TAG," onCreate mInstance = "+mInstance);
        startNavigationSystemUIService(this);
    }
    public static SystemUIApplication getInstance(){
        return mInstance;
    }
    public void startNavigationSystemUIService(Context context){
        Intent intentRequestPermission = new Intent();
        if(NaviPanelUI.mIsStarted){
            return;
        }
        if(!Settings.canDrawOverlays(this)){
            intentRequestPermission.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intentRequestPermission.setData(Uri.parse("package:"+getPackageName()));
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_LONG).show();
            //startActivityForResult(intentRequestPermission, CONST.START_SAIC_SYSTEUI_SERVICE);
        }else{
            startService(new Intent(context, SaicSystemUIService.class));
        }
    }
}
