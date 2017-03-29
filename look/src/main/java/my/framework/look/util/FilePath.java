package my.framework.look.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */
public class FilePath {

    private static Context mContext;

    public static void savePicture(Context context, Bitmap bm, String path) throws IOException {

        mContext = context;

        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }
        File myCaptureFile = new File(file, System.currentTimeMillis() + ".png");
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);

        bos.flush();
        bos.close();


    }


}
