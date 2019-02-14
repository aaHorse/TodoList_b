///*
//package com.example.zexiger.todolist_b.QQ_register;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class Utils {
//    public static String TAG="hzq";
//    /*
//    * 将腾讯返回的头像字符串转化为图片，并且返回给调用的函数
//    * */
//    public static Bitmap getbitmap(String imageUri) {
//        Log.v(TAG, "getbitmap:" + imageUri);
//        // 显示网络上的图片
//        Bitmap bitmap = null;
//        try {
//            URL myFileUrl = new URL(imageUri);
//            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//
//            Log.v(TAG, "image download finished." + imageUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.v(TAG, "getbitmap bmp fail---");
//            return null;
//        }
//        return bitmap;
//    }
//}
//
//
//*/
