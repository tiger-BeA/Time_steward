package com.example.jamesljk.project;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

public class GankActivity extends AppCompatActivity {

    private static final String source_url = "http://gank.io/api/data/Android/10/1 ";
    private Handler mHandler;

    private void fetchGankData(final String _url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GankData> data;
                try {
                    //链接url
                    URL url = new URL(_url);
                    URLConnection conn = url.openConnection();
                    conn.connect();

                    //读取返回的字符串
                    BufferedReader sr = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "utf-8"));
                    String temp;
                    StringBuilder sb = new StringBuilder();
                    while((temp = sr.readLine()) != null) sb.append(temp);

                    //解析json
                    data = GankData.Parse(sb.toString());

                    //发送messsage给主线程更新ui
                    if (data != null) {
                        Message msg = new Message();
                        msg.obj = data;
                        mHandler.sendMessage(msg);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GankActivity.this,"干货已刷新~",Toast.LENGTH_SHORT).show();
                fetchGankData(source_url);
            }
        });

        mHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                //随机选择一个网站加载
                List<GankData> data = (List<GankData>)msg.obj;
                WebView web = (WebView)GankActivity.this.findViewById(R.id.webContent);
                web.loadUrl(data.get(new Random().nextInt(data.size())).getUrl());
            }
        };

        fetchGankData(source_url);
    }

}
