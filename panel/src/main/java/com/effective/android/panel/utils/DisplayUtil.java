package com.effective.android.panel.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.effective.android.panel.Constants;

public class DisplayUtil {

    /**
     * 获取toolar的高度，但是这个方法仅仅在非沉浸下才有用。
     *
     * @param window
     * @return
     */
    public static int getToolbarHeight(Window window) {
        return window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    public static int[] getLocationOnScreen(View view) {
        int[] contentViewLocationInScreen = new int[2];
        view.getLocationOnScreen(contentViewLocationInScreen);
        return contentViewLocationInScreen;
    }

    public static boolean contentViewCanDrawStatusBarArea(Window window) {
        return getLocationOnScreen(window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT))[1] == 0;
    }

    /**
     * 对应 id 为 @Android：id/content 的 FrameLayout 所加载的布局。
     * 也就是我们 setContentView 的布局高度
     *
     * @param window
     * @return
     */
    public static int getContentViewHeight(Window window) {
        return window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
    }


    /**
     * 实际上获取的是DecorView的布局高度，是一个 FrameLayout，其内置布局 id 为 com.android.internal.R.layout.screen_simple 的 LinearLayout
     * 包含 id为 @+id/action_mode_bar_stub_ViewStub 的 ViewStub 还有 id 为 @Android：id/content 的 FrameLayout。
     *
     * @param window
     * @return
     */
    public static int getScreenHeightWithSystemUI(Window window) {
        return window.getDecorView().getHeight();
    }

    public static int getScreenHeightWithoutNavigationBar(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenHeightWithoutSystemUI(Window window) {
        Rect r = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(r);
        return r.bottom - r.top;
    }

    /**
     * 获取当前界面系统UI：包含状态栏+盗汗栏
     *
     * @param context
     * @param window
     * @return
     */
    public static int getSystemUI(Context context, Window window) {
        int systemUIHeight = 0;
        if (!isFullScreen(window)) {
            //get statusBar 和 navigationBar height
            int statusBarHeight = getStatusBarHeight(context);
            int navigationBatHeight = getNavigationBarHeight(context);
            if (isPortrait(context)) {
                systemUIHeight = isNavigationBarShow(context, window) ? statusBarHeight + navigationBatHeight : statusBarHeight;
            } else {
                systemUIHeight = statusBarHeight;
            }
        }
        return systemUIHeight;
    }


    public static boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    public static boolean isFullScreen(Window window) {
        return (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }


    public static int getStatusBarHeight(Context context) {
        return getInternalDimensionSize(context.getResources(), Constants.STATUS_BAR_HEIGHT_RES_NAME);
    }

    public static int getNavigationBarHeight(Context context) {
        return getInternalDimensionSize(context.getResources(), Constants.NAVIGATION_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, Constants.DIMEN, Constants.ANDROID);
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static boolean isPortrait(@NonNull Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT: {
                return true;
            }
            case Configuration.ORIENTATION_LANDSCAPE: {
                return false;
            }
            default: {
                Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                if (point.x <= point.y) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }


    @TargetApi(14)
    public static boolean isNavigationBarShow(Context context, Window window) {
        return isNavBarVisible(context, window);
    }

    /**
     * Decorview 源码
     * public static final ColorViewAttributes NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES =
     * new ColorViewAttributes(
     * SYSTEM_UI_FLAG_HIDE_NAVIGATION, FLAG_TRANSLUCENT_NAVIGATION,
     * Gravity.BOTTOM, Gravity.RIGHT, Gravity.LEFT,
     * Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME,
     * com.android.internal.R.id.navigationBarBackground,
     * 0 /* hideWindowFlag
     *
     * @param context
     * @param window
     * @return
     */
    public static boolean isNavBarVisible(Context context, @NonNull final Window window) {
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                int id = viewGroup.getChildAt(i).getId();
                if (id != View.NO_ID) {
                    String resourceEntryName = context.getResources()
                            .getResourceEntryName(id);
                    if ("navigationBarBackground".equals(resourceEntryName)
                            && viewGroup.getChildAt(i).getVisibility() == View.VISIBLE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
