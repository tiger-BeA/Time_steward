package com.example.jamesljk.project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamesljk on 2016/12/6.
 */
public class LoginActivity extends AppCompatActivity {
    private TextView id_dl,pwd_dl,id_zc,name_zc,pwd_zc;
    private Button main;
    private TextView turn;
    private MyViewpager myViewpager;
    //private userDB db = new userDB(LoginActivity.this);
    private FixedSpeedScroller mScroller;
    private View page1,page2;
    private List<View> pageList;
    private MyPagerAdapter myPagerAdapter;
    private int flag = 1;//1表示当前为登录界面，2表示为注册界面
    private static final String ip="192.168.23.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
       //StrictMode的线程策略主要用于检测磁盘IO和网络访问
        //当监视的线程发生策略的违例时，就可以获得警告
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        //db.insertDB("123","123","123");
        initView();

        final MyViewpager pager = (MyViewpager) findViewById(R.id.myViewPager);
        turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    EditText Id_dl = (EditText) pageList.get(0).findViewById(R.id.id_dl);
                    EditText Pwd_dl = (EditText) pageList.get(0).findViewById(R.id.pwd_dl);
                    Id_dl.addTextChangedListener(new MyTextWathcer());
                    Pwd_dl.addTextChangedListener(new MyTextWathcer());
                    pager.arrowScroll(66);
                    mScroller.setmDuration(500);
                    main.setText("注册");
                    turn.setText("已有账号？去登陆");
                    flag=2;
                    Id_dl.setText("");
                    Pwd_dl.setText("");
                }
                else{
                    EditText Id_zc = (EditText) pageList.get(1).findViewById(R.id.id_zc);
                    EditText Pwd_zc = (EditText) pageList.get(1).findViewById(R.id.pwd_zc);
                    EditText Name_zc = (EditText) pageList.get(1).findViewById(R.id.name_zc);
                    Id_zc.addTextChangedListener(new MyTextWathcer());
                    Pwd_zc.addTextChangedListener(new MyTextWathcer());
                    Name_zc.addTextChangedListener(new MyTextWathcer());
                    pager.arrowScroll(17);
                    mScroller.setmDuration(500);
                    main.setText("登录");
                    turn.setText("还没有账号？去注册");
                    flag=1;
                    Id_zc.setText("");
                    Name_zc.setText("");
                    Pwd_zc.setText("");
                }
            }
        });
        main.setEnabled(false);
        main.setBackgroundColor(Color.parseColor("#6495ED"));
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    Log.e("bug","不能显示？");
                    boolean flag2 = false;
                    EditText Id_dl = (EditText) pageList.get(0).findViewById(R.id.id_dl);
                    EditText Pwd_dl = (EditText) pageList.get(0).findViewById(R.id.pwd_dl);
                    id_dl.addTextChangedListener(new MyTextWathcer());
                    pwd_dl.addTextChangedListener(new MyTextWathcer());
                    final String str1 = id_dl.getText().toString();
                    final String str2 = pwd_dl.getText().toString();
                    //网络链接放在子线程里面
                    new Thread(){
                        @Override
                        public void run(){
                            try{
                                //通过socket与本地服务器通信，需要注意的ip地址是经过映射的，并不是直接的校园网物理ip地址
                                Socket s1=new Socket(ip,8888);
                                OutputStream os=s1.getOutputStream();
                                DataOutputStream dos=new DataOutputStream(os);
                                //dos.writeUTF("Login");
                                dos.writeUTF(str1+" "+str2+" "+"temp"+" "+"Login");// 向服务器传送登录账号和密码
                                InputStream is=s1.getInputStream();
                                DataInputStream dis=new DataInputStream(is);
                                String getStr=dis.readUTF();//YES或者NO
                                if(getStr.contains("YES")){
                                    String str[] = getStr.split("\\s");
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name",str[1]);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(getStr.equals("NO")){
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "登录失败",
                                            Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                dis.close();
                                dos.close();
                                s1.close();
                            }catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();


                    /*
                    Cursor c = db.queryDB();
                    while(c.moveToNext()){
                        String _id = c.getString(c.getColumnIndex("_id"));
                        String _pwd = c.getString(c.getColumnIndex("pwd"));
                        String _name = c.getString(c.getColumnIndex("name"));
                        if(_id.equals(str1)&&_pwd.equals(str2)){
                            flag2 = true;
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name",_name);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }
                    if(!flag2)Toast.makeText(LoginActivity.this,"用户名与密码不匹配",Toast.LENGTH_SHORT).show();
                    */
                }
                else{
                    Log.e("bug","输入内容");
                    EditText Id_zc = (EditText) pageList.get(1).findViewById(R.id.id_zc);
                    EditText Pwd_zc = (EditText) pageList.get(1).findViewById(R.id.pwd_zc);
                    EditText Name_zc = (EditText) pageList.get(1).findViewById(R.id.name_zc);
                    Id_zc.addTextChangedListener(new MyTextWathcer());
                    Pwd_zc.addTextChangedListener(new MyTextWathcer());
                    Name_zc.addTextChangedListener(new MyTextWathcer());
                    final String str1 = Id_zc.getText().toString();
                    final String str2 = Pwd_zc.getText().toString();
                    final String str3 = Name_zc.getText().toString();
                    Log.e("bug",str1+str2+str3);
                    new Thread(){
                        @Override
                        public void run(){
                            try{
                                Socket s1=new Socket(ip,8888);
                                OutputStream os=s1.getOutputStream();
                                DataOutputStream dos=new DataOutputStream(os);
                               // dos.writeUTF("Register");//向服务器传送Register字符
                                dos.writeUTF(str1+ " "
                                        + str3+" "+str2+" "+"Registered");
                                InputStream is=s1.getInputStream();
                                DataInputStream dis=new DataInputStream(is);
                                String getStr=dis.readUTF();//YES或者NO
                                if(getStr.equals("Duplicate")){
                                    Looper.prepare();
                                    Toast.makeText(LoginActivity.this,"该账户已被注册！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else{
                                    Looper.prepare();
                                    Toast.makeText(LoginActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name",str3);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                                dos.close();
                                s1.close();
                            }catch(UnknownHostException e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "注册失败",
                                        Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "注册失败 IOException"+e.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }.start();

                    /*
                    if(db.queryDB2(str1).getCount()>0){
                        Toast.makeText(LoginActivity.this,"当前手机号或邮箱已经注册",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.insertDB(str1,str3,str2);
                        Toast.makeText(LoginActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name",str3);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    */
                }
            }
        });


    }
    private class MyTextWathcer implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(flag == 1){
                EditText Id_dl = (EditText) pageList.get(0).findViewById(R.id.id_dl);
                EditText Pwd_dl = (EditText) pageList.get(0).findViewById(R.id.pwd_dl);
                String str1 = id_dl.getText().toString();
                String str2 = pwd_dl.getText().toString();
                if(!str1.isEmpty()&&!str2.isEmpty()){
                    main.setEnabled(true);
                    main.setBackgroundColor(Color.parseColor("#0000CD"));
                }
                else{
                    main.setEnabled(false);
                    main.setBackgroundColor(Color.parseColor("#6495ED"));
                }
            }
            if(flag == 2){
                EditText Id_zc = (EditText) pageList.get(1).findViewById(R.id.id_zc);
                EditText Pwd_zc = (EditText) pageList.get(1).findViewById(R.id.pwd_zc);
                EditText Name_zc = (EditText) pageList.get(1).findViewById(R.id.name_zc);
                String str1 = id_zc.getText().toString();
                String str2 = pwd_zc.getText().toString();
                String str3 = name_zc.getText().toString();
                if(!str1.isEmpty()&&!str2.isEmpty()&&!str3.isEmpty()){
                    main.setEnabled(true);
                    main.setBackgroundColor(Color.parseColor("#0000CD"));
                }else{
                    main.setEnabled(false);
                    main.setBackgroundColor(Color.parseColor("#6495ED"));
                }
            }
        }
        @Override
        public void afterTextChanged(Editable s) {  }
    }

    private void initView(){
        main =(Button) findViewById(R.id.MainButton);
        turn = (TextView) findViewById(R.id.MainText);
        myViewpager = (MyViewpager) findViewById(R.id.myViewPager);
        LayoutInflater inflater = getLayoutInflater().from(this); ;
        page1 = inflater.inflate(R.layout.dl,null);
        page2 = inflater.inflate(R.layout.zc,null);
        id_dl = (EditText) page1.findViewById(R.id.id_dl);
        pwd_dl = (EditText) page1.findViewById(R.id.pwd_dl);
        id_dl.addTextChangedListener(new MyTextWathcer());
        pwd_dl.addTextChangedListener(new MyTextWathcer());
        id_zc = (EditText) page2.findViewById(R.id.id_zc);
        pwd_zc = (EditText) page2.findViewById(R.id.pwd_zc);
        name_zc = (EditText) page2.findViewById(R.id.name_zc);
        id_zc.addTextChangedListener(new MyTextWathcer());
        pwd_zc.addTextChangedListener(new MyTextWathcer());
        name_zc.addTextChangedListener(new MyTextWathcer());
        pageList = new ArrayList<View>();
        pageList.add(page1);
        pageList.add(page2);

        myPagerAdapter = new MyPagerAdapter(pageList);
        myViewpager.setAdapter(myPagerAdapter);
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(myViewpager.getContext(),new AccelerateInterpolator());
            mField.set(myViewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

