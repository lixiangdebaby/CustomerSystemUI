package com.saicmotor.systemui.utils;

import android.content.Context;
import android.content.Intent;


public class StartActivityUtils {
    public static void startActivityHome(Context context){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
