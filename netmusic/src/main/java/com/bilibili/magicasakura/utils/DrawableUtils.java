package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by miracle on 2017/5/17.
 */

public abstract class DrawableUtils {

    protected abstract Drawable inflateDrawable(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException;

    static Drawable createDrawable(Context context, int resId) {
        if (resId < 0) return null;

        final TypedValue typedValue = new TypedValue();
        final Resources resources = context.getResources();
        resources.getValue(resId, typedValue, true);
        Drawable dr = null;

        if (typedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && typedValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            dr = new ColorDrawable(ThemeUtils.replaceColorById(context, resId));
        } else {


            try {
                if (typedValue.string != null && typedValue.string.toString().endsWith("xml")) {
                    final XmlResourceParser rp = resources.getXml(resId);
                    final AttributeSet attrs = Xml.asAttributeSet(rp);

                    int type;

                    while ((type = rp.next()) != XmlPullParser.START_TAG &&
                            type != XmlPullParser.END_DOCUMENT) {

                    }

                    if (type != XmlPullParser.START_TAG){
                        throw new XmlPullParserException("No start tag found");
                    }

                    dr =
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    static Drawable createFromXmlInner(Context context, XmlPullParser parser, AttributeSet attrs){
        final DrawableUtils drawableUtils;

        final String name = parser.getName();
        switch (name){
            case "selector":
                drawableUtils = null;
                break;
        }
    }

    static Drawable getAttrDrawable(Context context, AttributeSet attrs, int attr){
        final TypedArray a = obtainAttributes(context.getResources(), context.getTheme(), attrs, new int[attr]);
        int resId = a.getResourceId(0, 0);

        Drawable drawable = null;
        if (resId != 0){
            drawable = createDrawable(context, resId);
            if (drawable == null){
                drawable = a.getDrawable(0);
            }
        }
        a.recycle();
        return drawable;

    }

    static TypedArray obtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs){
        if (theme == null){
            return res.obtainAttributes(set, attrs);
        }

        return theme.obtainStyledAttributes(set, attrs, 0, 0);
    }
}
