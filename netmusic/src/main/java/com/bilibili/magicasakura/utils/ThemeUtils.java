package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;

/**
 * Created by miracle on 2017/5/7.
 */

public class ThemeUtils {

    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();

    public static final int[] DISABLED_STATE_SET = new int[]{-android.R.attr.state_enabled};
    public static final int[] ENABLED_STATE_SET = new int[]{android.R.attr.state_enabled};
    public static final int[] FOCUSED_STATE_SET = new int[]{android.R.attr.state_focused};
    public static final int[] ACTIVATED_STATE_SET = new int[]{android.R.attr.state_activated};
    public static final int[] PRESSED_STATE_SET = new int[]{android.R.attr.state_pressed};
    public static final int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};
    public static final int[] SELECTED_STATE_SET = new int[]{android.R.attr.state_selected};
    public static final int[] EMPTY_STATE_SET = new int[]{0};

    private static final int[] TEMP_ARRAY = new int[]{1};

    public static Drawable tintDrawable(Drawable drawable, @ColorInt int color, PorterDuff.Mode mode){
        if (drawable == null){
            return null;
        }

        Drawable wrapper = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTint(wrapper, color);
        DrawableCompat.setTintMode(drawable, mode);
        return wrapper;
    }

    public static Drawable tintDrawable(Context context, @DrawableRes int resId, @ColorRes int colorId){
        if (resId <= 0 || colorId <= 0)
            return null;

        Drawable drawable = context.getResources().getDrawable(resId);
        return tintDrawableByColorId(context, drawable, colorId);
    }

    public static Drawable tintDrawableByColorId(Context context, Drawable drawable, @ColorRes int colorId){
        if (drawable == null) return null;
        if (colorId <= 0) return null;
        return tintDrawable(drawable, replaceColor(context, context.getResources().getColor(colorId)));
    }

    public static Drawable tintDrawable(Drawable drawable, @ColorInt int color){
        return tintDrawable(drawable, color, PorterDuff.Mode.SRC_IN);
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList csl, PorterDuff.Mode mode){
        if (drawable == null) return null;
        Drawable wrapper = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrapper, csl);
        DrawableCompat.setTintMode(drawable, mode);
        return wrapper;
    }

    public static switchColor mSwitchColor;

    static @ColorInt
    int replaceColor(Context context, @ColorInt int color){
        return mSwitchColor == null ? Color.TRANSPARENT : mSwitchColor.replaceColor(context, color);
    }

    static @ColorInt
    int replaceColorById(Context context, @ColorRes int colorId){
        return mSwitchColor == null ? Color.TRANSPARENT : mSwitchColor.replaceColorById(context, colorId);
    }

    public interface switchColor{
        @ColorInt
        int replaceColorById(Context context, @ColorRes int colorId);

        @ColorInt
        int replaceColor(Context context, @ColorInt int color);
    }
}
