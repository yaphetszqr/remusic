package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.SparseArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by miracle on 2017/5/17.
 */

public class StateListDrawableUtils extends DrawableUtils{

    @Override
    protected Drawable inflateDrawable(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException {

        StateListDrawable sd = null;
        ArrayList<int[]> states = new ArrayList<>();
        ArrayList<Drawable> drawables = new ArrayList<>();

        SparseArray<ColorFilter> mColorFilterMap = null;
        final int innerDepth = parser.getDepth() + 1;

        int type;
        int depth;

        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT &&
                ((depth = parser.getDepth()) >= innerDepth ||
                type != XmlPullParser.END_TAG)){
            if (type != XmlPullParser.START_TAG){
                continue;
            }

            if (depth > innerDepth || !parser.getName().equals("item")){
                continue;
            }
            Drawable dr =
        }



        return null;
    }
}
