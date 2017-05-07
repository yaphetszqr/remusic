package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;

/**
 * Created by miracle on 2017/5/6.
 */

public class ColorStateListUtils {

    static ColorStateList createColorStateList(Context context, int resId){
        if (resId < 0){
            return null;
        }

        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(resId, typedValue, true);
        ColorStateList cl = null;
        if (typedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && typedValue.type <= TypedValue.TYPE_LAST_COLOR_INT){
            cl = ColorStateList.valueOf()
        }


    }
}
