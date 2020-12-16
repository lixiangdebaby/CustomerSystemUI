package com.saicmotor.systemui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.saicmotor.systemui.constant.CONST;
import com.saicmotor.systemui.service.FloatingSystemUIService;
import com.w4lle.library.PullDownView;
public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private PullDownView pullDownView;

    @Override
    public void onClick(View view) {
        if (view instanceof TextView) {
            Toast.makeText(this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }

        //if (view.getId() == R.id.image) {
            //Toast.makeText(this, "image onClick" ,Toast.LENGTH_SHORT).show();
       // }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        startFloatingSystemUIService();
       // findViewById(R.id.image).setOnClickListener(this);

        /*findViewById(R.id.text7).setOnClickListener(this);
        findViewById(R.id.text8).setOnClickListener(this);
        findViewById(R.id.text9).setOnClickListener(this);
        findViewById(R.id.text10).setOnClickListener(this);
        findViewById(R.id.text11).setOnClickListener(this);

        pullDownView = (PullDownView) findViewById(R.id.layout_pull);
        pullDownView.setOnPullChangeListerner(new PullDownView.OnPullChangeListerner() {
            @Override
            public void onPullDown() {
                Toast.makeText(MainActivity.this, "onPullDown", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPullUp() {
                Toast.makeText(MainActivity.this, "onPullUp", Toast.LENGTH_SHORT).show();
            }
        });
        */
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
            startService(new Intent(MainActivity.this, FloatingSystemUIService.class));
        }
    }
}