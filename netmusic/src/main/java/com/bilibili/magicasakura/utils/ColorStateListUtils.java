package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;

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
            cl = ColorStateList.valueOf(ThemeUtils.replaceColor(context, typedValue.resourceId));
        } else {
            final String file = typedValue.string.toString();
            if (file.endsWith("xml")){
                try {
                    final XmlResourceParser rp = context.getResources().getAssets().openXmlResourceParser(typedValue.assetCookie,
                            file);
                    final AttributeSet attrs = Xml.asAttributeSet(rp);
                    int type;
                    while ((type = rp.next()) != XmlPullParser.START_TAG
                            && type != XmlPullParser.END_DOCUMENT){

                    }

                    if (type != XmlPullParser.START_TAG){
                        throw new XmlPullParserException("No start tag found");
                    }

                    cl = createFromXmlInner(context, rp, attrs);
                    rp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        }

        return cl;
    }

    static ColorStateList createFromXmlInner(Context context, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        final String name = parser.getName();
        if (!name.equals("selector")){
            throw new XmlPullParserException(parser.getPositionDescription()
                        + ": invalid color state list tag " + name);
        }

        return inflateColorStateList(context, parser, attrs);
    }

    static ColorStateList inflateColorStateList(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException {

        final int innerDepth = parser.getDepth() + 1;
        int depth;
        int type;

        LinkedList<int[]> stateList = new LinkedList<>();
        LinkedList<Integer> colorList = new LinkedList<>();

        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                && ((depth = parser.getDepth()) >= innerDepth || type != XmlPullParser.END_TAG)){

            continue;
        }

        TypedArray a1 = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.color});
        final int value = a1.getResourceId(0, Color.MAGENTA);
        final int baseColor = value == Color.MAGENTA ? Color.MAGENTA : ThemeUtils.replaceColorById(context, value);
        a1.recycle();
        TypedArray a2 = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.alpha});
        final float alphaMod = a2.getFloat(0, 1.0f);
        a2.recycle();
        colorList.add(alphaMod != 1.0f ? ColorUtils.setAlphaComponent(baseColor, Math.round(Color.alpha(baseColor) * alphaMod)) :
                                baseColor);

        stateList.add(extractStateSet(attrs));

        if (stateList.size() > 0 && stateList.size() == colorList.size()){
            int[] colors = new int[colorList.size()];
            for (int i = 0; i < colorList.size(); i++){
                colors[i] = colorList.get(i);
            }

            return new ColorStateList(stateList.toArray(new int[stateList.size()][]), colors);
        }

        return null;
    }

    protected static int[] extractStateSet(AttributeSet attrs){
        int j = 0;
        final int numAttrs = attrs.getAttributeCount();
        int[] states = new int[numAttrs];
        for (int i = 0; i < numAttrs; i++){
            final int stateResId = attrs.getAttributeNameResource(i);
            switch (stateResId){
                case 0:
                    break;
                case android.R.attr.color:
                case android.R.attr.alpha:
                    continue;
                default:
                    states[j++] = attrs.getAttributeBooleanValue(i, false) ? stateResId : -stateResId;
            }
        }

        states = StateSet.trimStateSet(states, j);
        return states;
    }
}
