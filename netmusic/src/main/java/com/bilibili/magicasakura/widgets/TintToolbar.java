package com.bilibili.magicasakura.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.miracle.netmusic.R;

/**
 * Created by miracle on 2017/5/6.
 */

public class TintToolbar extends Toolbar{

    public TintToolbar(Context context) {
        this(context, null);
    }

    public TintToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public TintToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()){
            return;
        }


    }


}
