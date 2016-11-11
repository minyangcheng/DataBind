package com.min.databind.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * ScreenUtils
 */
public class ScreenUtils {

	public static float dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static float pxToDp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}

	public static int dpToPxInt(Context context, float dp) {
		return (int) (dpToPx(context, dp) + 0.5f);
	}

	public static int pxToDpInt(Context context, float px) {
		return (int) (pxToDp(context, px) + 0.5f);
	}

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null){
            mResources = Resources.getSystem();
            
        }else{
            mResources = context.getResources();
        }
        //DisplayMetrics{density=2.0, width=720, height=1280, scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }
    
    /** 
     * 获得屏幕高度 
     *  
     * @param context 
     * @return 
     */  
    public static int getScreenWidth(Context context)  
    {  
        WindowManager wm = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);  
        DisplayMetrics outMetrics = new DisplayMetrics();  
        wm.getDefaultDisplay().getMetrics(outMetrics);  
        return outMetrics.widthPixels;  
    }  
  
    /** 
     * 获得屏幕宽度 
     *  
     * @param context 
     * @return 
     */  
    public static int getScreenHeight(Context context)  
    {  
        WindowManager wm = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);  
        DisplayMetrics outMetrics = new DisplayMetrics();  
        wm.getDefaultDisplay().getMetrics(outMetrics);  
        return outMetrics.heightPixels;  
    }  
  
    /** 
     * 获得状态栏的高度 
     *  
     * @param context 
     * @return 
     */  
    public static int getStatusHeight(Context context)  
    {  
  
        int statusHeight = -1;  
        try  
        {  
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");  
            Object object = clazz.newInstance();  
            int height = Integer.parseInt(clazz.getField("status_bar_height")  
                    .get(object).toString());  
            statusHeight = context.getResources().getDimensionPixelSize(height);  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        return statusHeight;  
    }  
  
    /** 
     * 获取当前屏幕截图，包含状态栏 
     *  
     * @param activity 
     * @return 
     */  
    public static Bitmap snapShotWithStatusBar(Activity activity)  
    {  
        View view = activity.getWindow().getDecorView();  
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap bmp = view.getDrawingCache();  
        int width = getScreenWidth(activity);  
        int height = getScreenHeight(activity);  
        Bitmap bp = null;  
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);  
        view.destroyDrawingCache();  
        return bp;  
  
    }  
  
    /** 
     * 获取当前屏幕截图，不包含状态栏 
     *  
     * @param activity 
     * @return 
     */  
    public static Bitmap snapShotWithoutStatusBar(Activity activity)  
    {  
        View view = activity.getWindow().getDecorView();  
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap bmp = view.getDrawingCache();  
        Rect frame = new Rect();  
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
  
        int width = getScreenWidth(activity);  
        int height = getScreenHeight(activity);  
        Bitmap bp = null;  
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height  
                - statusBarHeight);  
        view.destroyDrawingCache();  
        return bp;  
  
    }  
}
