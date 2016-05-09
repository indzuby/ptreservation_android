package com.fastandslow.ptreservation.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by user on 2016-01-11.
 */
public class ContextUtils {
    public static int pxFromDp(Context context, int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }
    public static int percentFromDp(Context context, float percent) {
        float pxWidth = context.getResources().getDisplayMetrics().widthPixels;
        return Math.round(percent*pxWidth);
    }
    public static Drawable getDrawable(Context context, int id) {
        return Build.VERSION.SDK_INT >= 21?context.getResources().getDrawable(id, context.getTheme()):context.getResources().getDrawable(id);
    }
    public static View getActionBar(Context context,ActionBar actionBar, int layout) {
        View v = LayoutInflater.from(context).inflate(layout,null);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        Toolbar parent =(Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        return v;
    }
}
