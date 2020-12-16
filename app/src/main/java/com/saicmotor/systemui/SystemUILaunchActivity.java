package com.saicmotor.systemui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.saicmotor.systemui.constant.CONST;
import com.saicmotor.systemui.service.FloatingSystemUIService;

public class SystemUILaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startFloatingSystemUIService();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void startFloatingSystemUIService(){
        Intent intentRequestPermission = new Intent();
        //Toast.makeText(this, "当前无权限，请授权:"+FloatingSystemUIService.mIsStarted, Toast.LENGTH_SHORT);
        if(FloatingSystemUIService.mIsStarted){
            return;
        }
        if(!Settings.canDrawOverlays(this)){
            intentRequestPermission.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intentRequestPermission.setData(Uri.parse("package:"+getPackageName()));
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
            startActivityForResult(intentRequestPermission, CONST.START_FLOATING_SYSTEMUI_SERVICE);
        }else{
            startService(new Intent(SystemUILaunchActivity.this, FloatingSystemUIService.class));
        }
    }
}