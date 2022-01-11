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


public class MaterialAlertDialog {

    private Activity activity;
    private Dialog dialog;
    private String title;
    private String message;
    private String positiveText;
    private String negativeText = null;
    private float radius = 8f;
    private int textColor;
    private int backgroundColor;
    private int contextTextColor = android.R.color.black;
    private PositiveClickListener positiveClickListener;
    private NegativeClickListener negativeClickListener;

    public MaterialAlertDialog(Activity activity) {
        this.activity = activity;
        title = activity.getString(R.string.oops);
        message = activity.getString(R.string.something_went_wrong_please_check_your_internet_connection_and_try_again);
        positiveText = activity.getString(R.string.close);
        this.textColor = R.color.colorPrimaryText;
        this.backgroundColor = R.color.colorDayNight;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPositiveClick(String positiveText, PositiveClickListener positiveClickListener) {
        this.positiveText = positiveText;
        this.positiveClickListener = positiveClickListener;
    }

    public void setNegativeClick(String negativeText, NegativeClickListener negativeClickListener) {
        this.negativeText = negativeText;
        this.negativeClickListener = negativeClickListener;
    }

    public void setContentTextColor(int color) {
        this.contextTextColor = color;
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }


    public void show() {
        dialog = buildDialog();
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private Dialog buildDialog() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dialog_alert, null);
        CardView alertCard = contentView.findViewById(R.id.cardAd);
        alertCard.setCardBackgroundColor(ContextCompat.getColor(activity, backgroundColor));
        alertCard.setRadius(radius);

        TextView tvTitle = contentView.findViewById(R.id.tv_title);
        tvTitle.setText(title);

        TextView tvDescription = contentView.findViewById(R.id.tv_description);
        tvDescription.setText(message);

        TextView btnPositive = contentView.findViewById(R.id.btnPositive);
        btnPositive.setText(positiveText);
        btnPositive.setOnClickListener(v -> {
            if (positiveClickListener == null)
                return;
            positiveClickListener.onClick(dialog);
        });

        TextView btnNegative = contentView.findViewById(R.id.btnNegative);
        if (negativeClickListener != null) {
            btnNegative.setText(negativeText);
            btnNegative.setOnClickListener(v -> negativeClickListener.onClick(dialog));
        } else {
            btnNegative.setVisibility(View.GONE);
        }

        dialog.setContentView(contentView);
        return dialog;

    }

    public interface PositiveClickListener {
        void onClick(Dialog dialog);
    }

    public interface NegativeClickListener {
        void onClick(Dialog dialog);
    }
}
