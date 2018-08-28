package com.yutou.ui.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

/**
 * 描    述：App全局工具类 （图片加载 界面跳转......）
 * 创 建 人：ZJY
 * 创建日期：2017/4/10 8:59
 * 修订历史：
 * 修 改 人：
 */

public class AppUtils {
    /**
     * 复制功能
     * @param context
     * @param data
     * @return
     */
    public static Boolean copy(Context context, String data) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt > Build.VERSION_CODES.HONEYCOMB) {// api11
            ClipboardManager copy = (ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            copy.setText(data);
            return true;
        } else if (sdkInt <= Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager copyq = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            copyq.setText(data);
            return true;
        }
        return false;
    }
}
