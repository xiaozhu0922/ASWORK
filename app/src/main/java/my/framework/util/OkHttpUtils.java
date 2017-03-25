package my.framework.util;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttpUtils
 */
public class OkHttpUtils {

    private static OkHttpClient client = new OkHttpClient();

    /**
     * get异步请求
     */

    public static void get() {


        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .get()
                .build();

        //发起异步请求，并加入回调
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功回调
                Log.e("okHttp", "get=" + response.body().string());
            }
        });

    }

    /**
     * post异步请求
     */

    public static void post() {

        //创建Form表单对象，可以add多个键值队
        FormBody formBody = new FormBody.Builder()
                .add("param", "value")
                .add("param", "value")
                .build();

        //创建一个Request
        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("okHttp", "post=" + response.body().string());
            }
        });


    }

    /**
     * 文件上传与多文件上传
     */

    public static void upload() {

        //多个文件集合
        List<File> list = new ArrayList<>();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置为表单类型
        builder.setType(MultipartBody.FORM);
        //添加表单键值
        builder.addFormDataPart("param", "value");

        for (File file : list) {
            //添加多个文件
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            builder.addFormDataPart("files", file.getName(), fileBody);
        }

        Request request = new Request.Builder()
                .url("http://192.168.1.8/upload/UploadServlet")
                .post(builder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("okHttp", "updLoad=" + response.body().string());
            }
        });
    }

    public static void setTimeout() {
        client.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//10秒连接超时
                .writeTimeout(10, TimeUnit.SECONDS)//10m秒写入超时
                .readTimeout(10, TimeUnit.SECONDS)//10秒读取超时
                .build();
    }


}
