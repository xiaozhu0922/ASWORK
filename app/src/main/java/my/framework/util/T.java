package my.framework.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class T {

    private T() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;


    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(Context context, CharSequence text) {
        if (isShow)
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


}
