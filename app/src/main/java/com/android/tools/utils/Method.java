package com.android.tools.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.tools.R;
import com.android.tools.SplashScreen;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Method {

    private Activity activity;
    private OnClick onClick;
    public static boolean loginBack = false, personalizationAd = false;

    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private final String myPreference = "EventApp";
    public String pref_login = "pref_login";
    private String firstTime = "firstTime";
    public String profileId = "profileId";
    public String userImage = "userImage";
    public String userEmail = "userEmail";
    public String loginType = "loginType";
    public String show_login = "show_login";
    public String notification = "notification";
    public String themSetting = "them";


    public Method(Activity activity) {
        this.activity = activity;
        pref = activity.getSharedPreferences(myPreference, 0); // 0 - for private mode
        editor = pref.edit();
    }

    public Method(Activity activity, OnClick onClick) {
        this.activity = activity;
        this.onClick = onClick;
        pref = activity.getSharedPreferences(myPreference, 0); // 0 - for private mode
        editor = pref.edit();
    }

    public void login() {
        if (!pref.getBoolean(firstTime, false)) {
            editor.putBoolean(pref_login, false);
            editor.putBoolean(firstTime, true);
            editor.commit();
        }
    }

    //user login or not
    public boolean isLogin() {
        return pref.getBoolean(pref_login, false);
    }

    //get login type
    public String getLoginType() {
        return pref.getString(loginType, null);
    }

    //get user id
    public String userId() {
        return pref.getString(profileId, null);
    }

    //get device id
    @SuppressLint("HardwareIds")
    public String getDevice() {
        String deviceId;
        try {
            deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            deviceId = "Not Found";
        }
        return deviceId;
    }

    //rtl


    //them mode
    public String themMode() {
        return pref.getString(themSetting, "system");
    }

    //google map application installation or not check
    public boolean isAppInstalled() {
        String packageName = "com.google.android.apps.maps";
        Intent mIntent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }

    public void changeStatusBarColor() {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    //network check
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        columnWidth = point.x;
        return columnWidth;
    }

    //add to favourite

    //---------------Interstitial Ad---------------//


    //---------------Interstitial Ad---------------//

    //---------------Banner Ad---------------//


    //---------------Banner Ad---------------//


    //--------------Event Format---------//

    //year
    public String monthYear(int monthOfYear) {
        String monthYear;
        if (monthOfYear + 1 < 10) {
            monthYear = "0" + (monthOfYear + 1);
        } else {
            monthYear = String.valueOf(monthOfYear + 1);
        }
        return monthYear;
    }

    //month
    public String dayMonth(int dayOfMonth) {
        String dayMonth;
        if (dayOfMonth < 10) {
            dayMonth = "0" + dayOfMonth;
        } else {
            dayMonth = String.valueOf(dayOfMonth);
        }
        return dayMonth;
    }

    //date
    public String userViewDate(int dayOfMonth, int monthOfYear, int year, int dayOfMonthEnd, int monthOfYearEnd, int yearEnd) {
        return dayMonth(dayOfMonth) + "/" + monthYear(monthOfYear) + "/" + year
                + " " + activity.getResources().getString(R.string.to)
                + " " + dayMonth(dayOfMonthEnd) + "/" + monthYear(monthOfYearEnd) + "/" + yearEnd;
    }

    //time
    public String timeFormat(String hourString, String minuteString) {

        String hour = null;
        boolean PM = false;
        int hours_int = Integer.parseInt(hourString);

        if (hours_int > 11) {
            PM = true;
            if (hours_int > 12) {
                hour = String.valueOf(hours_int - 12);
            } else {
                hour = "12";
            }
        } else {
            if (hourString.equals("00")) {
                hour = "12";
            } else {
                hour = hourString;
            }
        }

        if (PM) {
            return hour + ":" + minuteString + " PM";
        } else {
            return hour + ":" + minuteString + " AM";
        }

    }

    //checking date start date is not greater than end date
    public boolean checkDatesBefore(String startDate, String endDate) {
        boolean isDate = false;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                isDate = true;// If start date is before end date
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                isDate = true;// If two dates are equal
            } else {
                isDate = false; // If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDate;
    }

    //--------------Event Format---------//

    //account suspend


    //alert message box
    public void alertBox(String message) {

        try {
            if (activity != null) {
                if (!activity.isFinishing()) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage(Html.fromHtml(message));
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

                    builder.show();


                }
            }
        } catch (Exception e) {
            Log.e("error_message", e.toString());
        }

    }

    public String webViewText() {
        String color;
        if (isDarkMode()) {
            color = Constant.webViewTextDark;
        } else {
            color = Constant.webViewText;
        }
        return color;
    }

    public String webViewLink() {
        String color;
        if (isDarkMode()) {
            color = Constant.webViewLinkDark;
        } else {
            color = Constant.webViewLink;
        }
        return color;
    }

    //webview rtl formate

    //check dark mode or not
    public boolean isDarkMode() {
        int currentNightMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                return false;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                return true;
            default:
                return false;
        }
    }

    public void click(final int position, final String type, final String title, final String id) {
        Log.e("type", "click: "+type );

        onClick.click(position, type, title, id);


    }
}
