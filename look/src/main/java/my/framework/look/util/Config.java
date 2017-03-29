package my.framework.look.util;

import android.os.Environment;

/**
 * Created by Administrator on 2017/3/29.
 */
public class Config {

    public static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() :
            "/mnt/sdcard";//保存到SD卡
    public static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/Look/savePic";//保存的确切位置

}
