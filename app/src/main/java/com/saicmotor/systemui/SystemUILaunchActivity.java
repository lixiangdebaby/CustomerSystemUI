package com.saicmotor.systemui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.saicmotor.systemui.constant.CONST;
import com.saicmotor.systemui.service.SaicSystemUIService;

public class SystemUILaunchActivity extends AppCompatActivity {
    private static final String TAG = SystemUILaunchActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_test2);
        startNavigationSystemUIService(SystemUIApplication.getInstance());
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG,"requestCode = "+requestCode+"  resultCode = "+resultCode);
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
            startActivityForResult(intentRequestPermission, CONST.START_SAIC_SYSTEUI_SERVICE);
        }else{
            startService(new Intent(SystemUILaunchActivity.this, SaicSystemUIService.class));
        }
    }
}