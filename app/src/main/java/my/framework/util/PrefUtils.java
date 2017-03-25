package my.framework.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences封装类
 */
public class PrefUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {

        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        return sp.getBoolean(key, defValue);

    }

    public static void setBoolean(Context ctx, String key, boolean defValue) {

        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        sp.edit().putBoolean(key, defValue).commit();

    }

    public static void setString(Context ctx, String key, String defValue) {

        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        sp.edit().putString(key, defValue).commit();

    }

    public static String getString(Context ctx, String key, String defValue) {

        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        return sp.getString(key, defValue);

    }

    public static void setInt(Context ctx, String key, int defValue) {

        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        sp.edit().putInt(key, defValue).commit();

    }

    public static int getInt(Context ctx, String key, int defValue) {

        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        return sp.getInt(key, defValue);

    }

}
