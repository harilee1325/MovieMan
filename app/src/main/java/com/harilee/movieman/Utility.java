package com.harilee.movieman;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

public class Utility {


    private static Utility utilityInstance;

    private Utility() {
    }

    public static synchronized Utility getUtilityInstance() {
        if (null == utilityInstance) {
            utilityInstance = new Utility();
        }
        return utilityInstance;
    }

    public void setPreference(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("movieman", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();

    }

    public String getPreference(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("movieman", Context.MODE_PRIVATE);
        String result = prefs.getString(key, "");
        //String a = prefs.getString(key,"");
        return result;
    }


    public void showGifPopup(final Context mContext, boolean show, Dialog dialog, String title) {
        dialog.setContentView(R.layout.gif_popup);
        dialog.setCancelable(false);
        AVLoadingIndicatorView imageView = dialog.findViewById(R.id.avi);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if (mContext != null) {
            if (!((Activity) mContext).isFinishing()) {
                try {
                    if (show) {
                        dialog.show();
                    } else {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
