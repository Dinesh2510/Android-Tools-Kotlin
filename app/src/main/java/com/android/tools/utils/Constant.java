package com.android.tools.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static String ImageUpload =  "http://pixeldev.in/webservices/e_commerce/upload_base.php";
    public  static  String Convert_Bitmap_to_base64(Bitmap bitmap){


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return  encoded;
    }

    //storage folder path
    public static String appStorage = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;

    //Change WebView text color light and dark mode
    public static String webViewText = "#8b8b8b;";
    public static String webViewTextDark = "#FFFFFF;";

    //Change WebView link color light and dark mode
    public static String webViewLink = "#0782C1;";
    public static String webViewLinkDark = "#a9793b;";

    public static int AD_COUNT = 0;
    public static int AD_COUNT_SHOW = 0;

    public static String stringLatitude = "";
    public static String stringLongitude = "";

}
