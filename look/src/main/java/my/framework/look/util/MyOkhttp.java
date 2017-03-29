package my.framework.look.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 网络请求
 */
public class MyOkhttp {

    public static OkHttpClient client = new OkHttpClient();
    public static Bitmap bitmap = null;

    public static String get(String url) {
        try {
            client.newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS);
            Request request = new Request.Builder().url(url).build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 异步getNetWorkBitmap
     */
    public static Bitmap getNetWorkBitmap(final Context context,String imgurl, final  DownloadCallback callback) {

        final Request request = new Request.Builder().get()
                .url(imgurl)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(is);
                if(null==callback){
                    return;
                }
                if(null!=bitmap){
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.OnFinished(bitmap);
                        }
                    });

                }else {
                    callback.faild();
                }

            }

        });


        return bitmap;
    }

public  interface  DownloadCallback {

    public void OnFinished(Bitmap bitmap);

    public  void faild();
}
}
