package com.example.jamesljk.project;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/14.
 */

public class Picture_Setting  extends AppCompatActivity {
    private TextView textView;
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";
    private Bitmap mbitmap;
    private String mFileName = "noteBackground.jpg";
    private Button SavePictureButton;
    private Button SetAsWallPaper;
    private Button CancelWallPaper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        textView = (TextView) findViewById(R.id.text_writer);
        SavePictureButton = (Button) findViewById(R.id.savePictureButton);
        SetAsWallPaper = (Button) findViewById(R.id.SetAsWallPaper);
        CancelWallPaper = (Button) findViewById(R.id.CancelWallPaper);

        Intent intent = getIntent();
        if (intent != null){
            textView.setText(intent.getStringExtra("TextWriter"));
        }

        SavePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePictureButton.setVisibility(View.GONE);
                View vTop = (ViewGroup)v.getParent().getParent().getParent();
                vTop.setDrawingCacheEnabled(true);
                mbitmap = Bitmap.createBitmap(vTop.getWidth(), vTop.getHeight(), Bitmap.Config.ARGB_8888);
                if (mbitmap != null) {
                    Canvas canvas = new Canvas(mbitmap);
                    vTop.draw(canvas);
                    new Thread(saveFileRunnable).start();
                    Toast.makeText(Picture_Setting.this, "图片保存成功！", Toast.LENGTH_SHORT).show();
                    SetAsWallPaper.setVisibility(View.VISIBLE);
                    CancelWallPaper.setVisibility(View.VISIBLE);
                }
                else{
                    Log.i("Debug","--------------------图片为空");
                }
            }
        });
        SetAsWallPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putWallpaper();
                Toast.makeText(Picture_Setting.this, "壁纸设置成功！", Toast.LENGTH_SHORT).show();
            }
        });
        CancelWallPaper.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SetAsWallPaper.setVisibility(View.GONE);
                CancelWallPaper.setVisibility(View.GONE);
                SavePictureButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void saveFile (Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()){
            dirFile.mkdir();
        }
        else{
            dirFile.delete();
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH+fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
    private Runnable saveFileRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                saveFile(mbitmap, mFileName);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    };

    private void putWallpaper (){
        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            wallpaperManager.setBitmap(mbitmap);
//            Class class1 = wallpaperManager.getClass();//获取类名
//            Log.i("debug","-----------------获取类名成功");
//            Method setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper", Bitmap.class);//获取设置锁屏壁纸的函数
//            Log.i("debug","-----------------获取函数成功");
//            setWallPaperMethod.invoke(wallpaperManager, BitmapFactory.decodeFile(ALBUM_PATH+mFileName));//调用锁屏壁纸的函数，并指定壁纸的路径imageFilesPath
//            Log.i("debug","-----------------设置成功");
        } catch (IOException e) {
            Log.i("Debug","-------------------------设置桌面背景发生异常");
        }
    }
}

