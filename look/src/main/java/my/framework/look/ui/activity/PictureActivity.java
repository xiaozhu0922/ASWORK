package my.framework.look.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import my.framework.look.R;
import my.framework.look.util.Config;
import my.framework.look.util.FilePath;
import my.framework.look.util.MyOkhttp;

/**
 *
 */
public class PictureActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mBack;
    private Button mSave;
    private ImageView mImageView;
    private String imgUrl;
    private Bitmap saveBitmap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_picture);

        mContext = getApplicationContext();
        initView();
        initData();

    }

    public void initView() {

        mImageView = (ImageView) findViewById(R.id.imageView);
        mBack = (Button) findViewById(R.id.back);
        mSave = (Button) findViewById(R.id.save);

        mBack.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mImageView.setOnClickListener(this);

    }


    public void initData() {

        Intent intent = getIntent();
        if (intent != null) {
            imgUrl = intent.getStringExtra("url");
        }

        //下载图片
        MyOkhttp.getNetWorkBitmap(PictureActivity.this, imgUrl, new MyOkhttp.DownloadCallback() {
            @Override
            public void OnFinished(final Bitmap bitmap) {
                saveBitmap = bitmap;
                Glide.with(getApplicationContext()).load(imgUrl).into(mImageView);
            }

            @Override
            public void faild() {
                Log.e(PictureActivity.class.getName(), "err :download fialed");
            }
        });


    }


    @Override
    public void onClick(View v) {
        int item = v.getId();
        switch (item) {

            case R.id.back:
                finish();
                break;
            case R.id.imageView:
                finish();
                break;
            case R.id.save:
                try {
                    FilePath.savePicture(mContext, saveBitmap, Config.SAVE_PIC_PATH);
                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
