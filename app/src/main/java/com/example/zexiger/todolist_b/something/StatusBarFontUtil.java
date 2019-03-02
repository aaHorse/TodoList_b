package com.example.zexiger.todolist_b.something;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/*
* 自定义状态栏
* */
public class StatusBarFontUtil {
    /*
    * 设置状态栏的背景颜色
    * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(int statusColor, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏为白色
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
            /*
            * 设置状态栏的字体颜色
            * 百度说这里设置的状态栏，需要兼容不同的手机，如果运行出错，
            * 出现不匹配的问题，原因在这里，因为这里没有做兼容
            * */
            View view=window.getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
