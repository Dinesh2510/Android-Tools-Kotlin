package com.android.tools.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.tools.R;


public class MaterialProgressDialog {

    private Activity activity;
    private Dialog dialog;
    private String message ;
    private float radius = 4f;
    private int textColor = R.color.colorPrimaryText;
    private int backgroundColor = R.color.colorDayNight;
    private boolean isCancelable = false;

    public MaterialProgressDialog(Activity activity) {
        this.activity = activity;
        this.message=activity.getString(R.string.loading);
    }

    public MaterialProgressDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public MaterialProgressDialog setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public MaterialProgressDialog setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public MaterialProgressDialog setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public MaterialProgressDialog setCancelable(boolean cancelable) {
        isCancelable = cancelable;
        return this;
    }

    public void show() {
        dialog = buildDialog();
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private Dialog buildDialog() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dailog_progress_message, null);
        CardView progressCard = contentView.findViewById(R.id.cardAd);
        TextView textViewMsg = contentView.findViewById(R.id.txtViewMsg);
        progressCard.setRadius(radius);
        progressCard.setCardBackgroundColor(ContextCompat.getColor(activity, backgroundColor));
        textViewMsg.setText(message);
        textViewMsg.setTextColor(ContextCompat.getColor(activity, textColor));
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(isCancelable);
        dialog.setContentView(contentView);
        return dialog;
    }

}
