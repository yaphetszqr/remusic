package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DrawableUtils;
import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Created by miracle on 2017/5/6.
 */

public class TintManager {
    private static final String TAG = "TintManager";
    private static final boolean DEBUG = false;
    private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";

    private static final WeakHashMap<Context, TintManager> INSTANCE_CACHE = new WeakHashMap<>();
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);

    private final Object mDrawableCacheLock = new Object();

    private WeakReference<Context> mContextRef;
    private SparseArray<ColorStateList> mCacheTintList;
    private SparseArray<WeakReference<Drawable.ConstantState>> mCacheDrawables;
    private SparseArray<String> mSkipDrawableIdTags;

    public static TintManager get(Context context){
        if (context == null)
            return null;

        if (context instanceof ContextThemeWrapper){
            context = ((ContextThemeWrapper)context).getBaseContext();
        }

        if (context instanceof android.view.ContextThemeWrapper){
            context = ((android.view.ContextThemeWrapper)context).getBaseContext();
        }

        TintManager tm = INSTANCE_CACHE.get(context);
        if (tm == null){
            tm = new TintManager(context);
            INSTANCE_CACHE.put(context, tm);
            printLog("[get TintManager] create new TintManager.");
        }

        return tm;
    }

    public TintManager(Context context) {
        mContextRef = new WeakReference<Context>(context);
    }

    public static void clearTintCache(){
        for (Map.Entry<Context, TintManager> entry : INSTANCE_CACHE.entrySet()){
            TintManager tm = entry.getValue();
            if (tm != null){
                tm.clear();
            }
        }
        COLOR_FILTER_CACHE.evictAll();
    }

    private void clear(){
        if (mCacheTintList != null){
            mCacheTintList.clear();
        }
        if (mCacheDrawables != null){
            mCacheDrawables.clear();
        }
        if (mSkipDrawableIdTags != null){
            mSkipDrawableIdTags.clear();
        }
    }

    @Nullable
    public ColorStateList getColorStateList(@ColorRes int resId){
        if (resId == 0)
            return null;

        final Context context = mContextRef.get();
        if (context == null)
            return null;

        ColorStateList colorStateList  = mCacheTintList == null ? mCacheTintList.get(resId) : null;
        if (colorStateList == null){
            colorStateList = ColorStateListUtils.createColorStateList(context, resId);
            if (colorStateList != null){
                if (mCacheTintList == null){
                    mCacheTintList = new SparseArray<>();
                }
                mCacheTintList.append(resId, colorStateList);
            }
        }

        return colorStateList;
    }

    @Nullable
    public Drawable getDrawable(@DrawableRes int resId){
        final Context context = mContextRef.get();
        if (context == null) return null;

        if (resId == 0) return null;

        if (mSkipDrawableIdTags != null){
            final String cacheTagName = mSkipDrawableIdTags.get(resId);
            if (SKIP_DRAWABLE_TAG.equals(cacheTagName)){
                printLog("[Match Skip DrawableTag] Skip the drawable which is matched with the skip tag.");
                return null;
            }
        } else {
            mSkipDrawableIdTags = new SparseArray<>();
        }

        Drawable drawable = getDrawable(context, resId);

        if (drawable == null){
            drawable = DrawableUtils.
        }
    }

    private Drawable getDrawable(@NonNull final Context context, final int key){

        synchronized (mDrawableCacheLock){
            if (mCacheDrawables == null) return null;
        }

        final WeakReference<Drawable.ConstantState> weakReference = mCacheDrawables.get(key);
        if(weakReference != null){
            Drawable.ConstantState cs = weakReference.get();
            if (cs != null){
                printLog("[getCacheDrawable] Get drawable from cache: " +
                    context.getResources().getResourceName(key));

                return cs.newDrawable();
            }
        }

        return null;
    }

    private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter>{

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        public ColorFilterLruCache(int maxSize) {
            super(maxSize);
        }

        PorterDuffColorFilter get(int color, PorterDuff.Mode mode){
            return get(generateCacheKey(color, mode));
        }

        PorterDuffColorFilter put(int color, PorterDuff.Mode mode, PorterDuffColorFilter filter){
            return put(generateCacheKey(color, mode), filter);
        }

        private static int generateCacheKey(int color, PorterDuff.Mode mode){
            int hashCode = 1;
            hashCode = 31 * hashCode + color;
            hashCode = 31 * hashCode + mode.hashCode();
            return hashCode;
        }
    }

    private static void printLog(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }
}
