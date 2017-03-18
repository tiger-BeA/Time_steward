package com.example.jamesljk.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamesljk.project.bean.DietBean;
import com.example.jamesljk.project.bean.PickerViewData;
import com.example.jamesljk.project.model.IPickerViewData;
import com.example.jamesljk.project.pickerview.OptionsPickerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private List<String> mDatas;
    private List<String> mDatas_id;
    private userDB db = new userDB(MainActivity.this);
    //////////////////////////
    private ArrayList<DietBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    OptionsPickerView pvOptions;

    private ArrayList<DietBean> options1Items2 = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items2 = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items2 = new ArrayList<>();
    OptionsPickerView pvOptions2;

    private ArrayList<DietBean> options1Items3 = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items3 = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items3 = new ArrayList<>();
    OptionsPickerView pvOptions3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CardView card1 = (CardView) findViewById(R.id.card1);
        final TextView cardtext1 =(TextView)findViewById(R.id.cardtext1) ;
        final SeekBar seekBar1 =(SeekBar)findViewById(R.id.seekBar1);
        final CardView card2 = (CardView) findViewById(R.id.card2);
        final TextView cardtext2 =(TextView)findViewById(R.id.cardtext2) ;
        final SeekBar seekBar2 =(SeekBar)findViewById(R.id.seekBar2);
        final CardView card3 = (CardView) findViewById(R.id.card3);
        final TextView cardtext3 =(TextView)findViewById(R.id.cardtext3) ;
        final SeekBar seekBar3 =(SeekBar)findViewById(R.id.seekBar3);
        final TextView seekbar1_start =(TextView)findViewById(R.id.seekBar1_start); final TextView seekbar1_end =(TextView)findViewById(R.id.seekBar1_end);
        final TextView seekbar2_start =(TextView)findViewById(R.id.seekBar2_start); final TextView seekbar2_end =(TextView)findViewById(R.id.seekBar2_end);
        final TextView seekbar3_start =(TextView)findViewById(R.id.seekBar3_start); final TextView seekbar3_end =(TextView)findViewById(R.id.seekBar3_end);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实现对Content_main布局里的作息计划的修改
                seekBar1.setProgress(0);seekBar2.setProgress(0);seekBar3.setProgress(0);
                seekBar1.setMax(100);seekBar2.setMax(100);seekBar3.setMax(100);
                seekbar1_start.setText("进度条开始");seekbar2_start.setText("进度条开始");seekbar3_start.setText("进度条开始");
                seekbar1_end.setText("进度条结束");seekbar2_end.setText("进度条结束");seekbar3_end.setText("进度条结束");
                cardtext1.setText("You haven't logged any meals today");
                cardtext2.setText("You haven't logged any working today");
                cardtext3.setText("You haven't logged any exercise today");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //获取NavigationView的头部，设定头像点击登录事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageButton turn  = (ImageButton) headerView.findViewById(R.id.Login);
        turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        //接收从登录界面传来的用户名
        final Intent intent = this.getIntent();
        String _name=intent.getStringExtra("name");
        TextView _user = (TextView) headerView.findViewById(R.id.user_info);
        _user.setText(_name);


//////////////////////////////////////////////////////////////////////////////////
        //选项选择器1

        pvOptions = new OptionsPickerView(this);

        //选项1
        options1Items.add(new DietBean(0,"Meat","鱼油，牛油和其他","其他数据"));
        options1Items.add(new DietBean(1,"Main","米饭或者面粉",""));
        options1Items.add(new DietBean(3,"Dissert","嗯～～",""));

        //选项2
        ArrayList<String> options2Items_01=new ArrayList<>();
        options2Items_01.add("food1");options2Items_01.add("food2");
        options2Items_01.add("food3");options2Items_01.add("food4");
        ArrayList<String> options2Items_02=new ArrayList<>();
        options2Items_02.add("food1");options2Items_02.add("food2");
        options2Items_02.add("food3");options2Items_02.add("food4");
        ArrayList<String> options2Items_03=new ArrayList<>();
        options2Items_03.add("food1");options2Items_03.add("food2");
        options2Items_03.add("food3");options2Items_03.add("food4");
        options2Items.add(options2Items_01);options2Items.add(options2Items_02);options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_03 = new ArrayList<>();
        ArrayList<IPickerViewData> options3Items_01_01=new ArrayList<>();
        options3Items_01_01.add(new PickerViewData("1汤勺"));options3Items_01_01.add(new PickerViewData("2汤勺"));
        options3Items_01_01.add(new PickerViewData("3汤勺"));options3Items_01_01.add(new PickerViewData("4汤勺"));
        options3Items_01.add(options3Items_01_01);
        ArrayList<IPickerViewData> options3Items_01_02=new ArrayList<>();
        options3Items_01_02.add(new PickerViewData("1汤勺"));options3Items_01_02.add(new PickerViewData("2汤勺"));
        options3Items_01_02.add(new PickerViewData("3汤勺"));options3Items_01_02.add(new PickerViewData("4汤勺"));
        options3Items_01.add(options3Items_01_02);
        ArrayList<IPickerViewData> options3Items_01_03=new ArrayList<>();
        options3Items_01_03.add(new PickerViewData("1汤勺"));options3Items_01_03.add(new PickerViewData("2汤勺"));
        options3Items_01_03.add(new PickerViewData("3汤勺"));options3Items_01_03.add(new PickerViewData("4汤勺"));
        options3Items_01.add(options3Items_01_03);
        ArrayList<IPickerViewData> options3Items_01_04=new ArrayList<>();
        options3Items_01_04.add(new PickerViewData("1汤勺"));options3Items_01_04.add(new PickerViewData("2汤勺"));
        options3Items_01_04.add(new PickerViewData("3汤勺"));
        options3Items_01.add(options3Items_01_04);

        ArrayList<IPickerViewData> options3Items_02_01=new ArrayList<>();
        options3Items_02_01.add(new PickerViewData("1碗"));options3Items_02_01.add(new PickerViewData("2碗"));
        options3Items_02_01.add(new PickerViewData("3碗"));
        options3Items_02.add(options3Items_02_01);
        ArrayList<IPickerViewData> options3Items_02_02=new ArrayList<>();
        options3Items_02_02.add(new PickerViewData("1碗"));options3Items_02_02.add(new PickerViewData("2碗"));
        options3Items_02_02.add(new PickerViewData("3碗"));
        options3Items_02.add(options3Items_02_02);
        ArrayList<IPickerViewData> options3Items_02_03=new ArrayList<>();
        options3Items_02_03.add(new PickerViewData("1碗"));options3Items_02_03.add(new PickerViewData("2碗"));
        options3Items_02_03.add(new PickerViewData("3碗"));
        options3Items_02.add(options3Items_02_03);
        ArrayList<IPickerViewData> options3Items_02_04=new ArrayList<>();
        options3Items_02_04.add(new PickerViewData("1碗"));options3Items_02_04.add(new PickerViewData("2碗"));
        options3Items_02_04.add(new PickerViewData("3碗"));
        options3Items_02.add(options3Items_02_04);

        ArrayList<IPickerViewData> options3Items_03_01=new ArrayList<>();
        options3Items_03_01.add(new PickerViewData("1碗"));options3Items_03_01.add(new PickerViewData("2碗"));
        options3Items_03_01.add(new PickerViewData("3碗"));
        options3Items_03.add(options3Items_03_01);
        ArrayList<IPickerViewData> options3Items_03_02=new ArrayList<>();
        options3Items_03_02.add(new PickerViewData("1碗"));options3Items_03_02.add(new PickerViewData("2碗"));
        options3Items_03_02.add(new PickerViewData("3碗"));
        options3Items_03.add(options3Items_03_02);
        ArrayList<IPickerViewData> options3Items_03_03=new ArrayList<>();
        options3Items_03_03.add(new PickerViewData("1碗"));options3Items_03_03.add(new PickerViewData("2碗"));
        options3Items_03_03.add(new PickerViewData("3碗"));
        options3Items_03.add(options3Items_03_03);
        ArrayList<IPickerViewData> options3Items_03_04=new ArrayList<>();
        options3Items_03_04.add(new PickerViewData("1碗"));options3Items_03_04.add(new PickerViewData("2碗"));
        options3Items_03_04.add(new PickerViewData("3碗"));
        options3Items_03.add(options3Items_03_04);

        options3Items.add(options3Items_01);options3Items.add(options3Items_02);options3Items.add(options3Items_03);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("碗", "汤勺", "份");
        pvOptions.setTitle("Food");
        pvOptions.setCyclic(false, true, true);

        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + ": " + options2Items.get(options1).get(option2)
                        + "(" + options3Items.get(options1).get(option2).get(options3).getPickerViewText() + ")";
                cardtext1.setText(tx);
                seekBar1.setProgress((seekBar1.getProgress() + 1000));
            }
        });
        ///////////////////////////////////////////////////////////
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (seekBar1.getMax()==100){//若目标值仍没有设定，弹出消息框进行设定
                LayoutInflater factory =LayoutInflater.from(MainActivity.this);
                View view2=factory.inflate(R.layout.dialog_layout,null);
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                builder.setView(view2);

                final EditText dialog_diet=(EditText) view2.findViewById(R.id.dialog_diet);
                final EditText dialog_work=(EditText) view2.findViewById(R.id.dialog_work);
                final EditText dialog_exercise=(EditText) view2.findViewById(R.id.dialog_exercise);

                builder.setTitle("完善你的健康规划目标").setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dialog_diet.getText().toString().isEmpty()||dialog_work.getText().toString().isEmpty()||dialog_exercise.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "选项不能有空", Toast.LENGTH_SHORT).show();
                        }else{
                            int diet_max=Integer.parseInt(dialog_diet.getText().toString());
                            int work_max=Integer.parseInt(dialog_work.getText().toString());
                            int exercise_max =Integer.parseInt(dialog_exercise.getText().toString());
                            seekBar1.setMax(diet_max);seekBar2.setMax(work_max);seekBar3.setMax(exercise_max);
                            seekbar1_end.setText(dialog_diet.getText().toString()+"卡路里");
                            seekbar2_end.setText(dialog_work.getText().toString()+"小时");
                            seekbar3_end.setText(dialog_exercise.getText().toString()+"小时");
                            seekbar1_start.setText("0");seekbar2_start.setText("0");seekbar3_start.setText("0");
                            seekBar1.setProgress(0);seekBar2.setProgress(0);seekBar3.setProgress(0);
                        }
                    }
                }).setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();

            }else{
                pvOptions.show();
                Toast.makeText(MainActivity.this,"card1被点击", Toast.LENGTH_SHORT).show();
            }
            }
        });




        //////////////////////////////////////////////////////////////////////////////////
        //选项选择器2
        pvOptions2 = new OptionsPickerView(this);

        //选项1
        options1Items2.add(new DietBean(0,"Learn","学习，看书","其他数据"));
        options1Items2.add(new DietBean(1,"Work","工作",""));

        //选项2
        ArrayList<String> options2Items2_01=new ArrayList<>();
        options2Items2_01.add("看书");options2Items2_01.add("做作业");
        options2Items2_01.add("预习");options2Items2_01.add("复习");
        ArrayList<String> options2Items2_02=new ArrayList<>();
        options2Items2_02.add("Work1");options2Items2_02.add("Work2");
        options2Items2_02.add("Work3");options2Items2_02.add("Work4");
        options2Items2.add(options2Items2_01);options2Items2.add(options2Items2_02);


        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items2_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items2_02 = new ArrayList<>();
        ArrayList<IPickerViewData> options3Items2_01_01=new ArrayList<>();
        options3Items2_01_01.add(new PickerViewData("1h"));options3Items2_01_01.add(new PickerViewData("2h"));
        options3Items2_01_01.add(new PickerViewData("3h"));options3Items2_01_01.add(new PickerViewData("4h"));
        options3Items_01.add(options3Items2_01_01);
        ArrayList<IPickerViewData> options3Items2_01_02=new ArrayList<>();
        options3Items2_01_02.add(new PickerViewData("1h"));options3Items2_01_02.add(new PickerViewData("2h"));
        options3Items2_01_02.add(new PickerViewData("3h"));options3Items2_01_02.add(new PickerViewData("4h"));
        options3Items2_01.add(options3Items2_01_02);
        ArrayList<IPickerViewData> options3Items2_01_03=new ArrayList<>();
        options3Items2_01_03.add(new PickerViewData("1h"));options3Items2_01_03.add(new PickerViewData("2h"));
        options3Items2_01_03.add(new PickerViewData("3h"));options3Items2_01_03.add(new PickerViewData("4h"));
        options3Items2_01.add(options3Items2_01_03);
        ArrayList<IPickerViewData> options3Items2_01_04=new ArrayList<>();
        options3Items2_01_04.add(new PickerViewData("1h"));options3Items2_01_04.add(new PickerViewData("2h"));
        options3Items2_01_04.add(new PickerViewData("3h"));
        options3Items2_01.add(options3Items2_01_04);

        ArrayList<IPickerViewData> options3Items2_02_01=new ArrayList<>();
        options3Items2_02_01.add(new PickerViewData("1h"));options3Items2_02_01.add(new PickerViewData("2h"));
        options3Items2_02_01.add(new PickerViewData("3h"));
        options3Items2_02.add(options3Items2_02_01);
        ArrayList<IPickerViewData> options3Items2_02_02=new ArrayList<>();
        options3Items2_02_02.add(new PickerViewData("1h"));options3Items2_02_02.add(new PickerViewData("2h"));
        options3Items2_02_02.add(new PickerViewData("3h"));
        options3Items2_02.add(options3Items2_02_02);
        ArrayList<IPickerViewData> options3Items2_02_03=new ArrayList<>();
        options3Items2_02_03.add(new PickerViewData("1h"));options3Items2_02_03.add(new PickerViewData("2h"));
        options3Items2_02_03.add(new PickerViewData("3h"));
        options3Items2_02.add(options3Items2_02_03);
        ArrayList<IPickerViewData> options3Items2_02_04=new ArrayList<>();
        options3Items2_02_04.add(new PickerViewData("1h"));options3Items2_02_04.add(new PickerViewData("2h"));
        options3Items2_02_04.add(new PickerViewData("3h"));
        options3Items2_02.add(options3Items2_02_04);

        options3Items2.add(options3Items2_01);options3Items2.add(options3Items2_02);

        //三级联动效果
        pvOptions2.setPicker(options1Items2, options2Items2, options3Items2, true);
        pvOptions2.setTitle("Study&Work");
        pvOptions2.setCyclic(false, true, true);

        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions2.setSelectOptions(1, 1, 1);
        pvOptions2.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx2 = options1Items2.get(options1).getPickerViewText()
                        + ": " +options2Items2.get(options1).get(option2)
                        + "(" +options3Items2.get(options1).get(option2).get(options3).getPickerViewText()+")";
                cardtext2.setText(tx2);
                if(options3Items2.get(options1).get(option2).get(options3).getPickerViewText().toString().equals("1h")){
                    seekBar2.setProgress((seekBar2.getProgress()+1));
                }else if(options3Items2.get(options1).get(option2).get(options3).getPickerViewText().toString().equals("2h")){
                    seekBar2.setProgress((seekBar2.getProgress()+2));
                }else if(options3Items2.get(options1).get(option2).get(options3).getPickerViewText().toString().equals("3h")){
                    seekBar2.setProgress((seekBar2.getProgress()+3));
                }
            }
        });
        ///////////////////////////////////////////////////////////
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seekBar2.getMax()==100){//若目标值仍没有设定，弹出消息框进行设定
                    LayoutInflater factory =LayoutInflater.from(MainActivity.this);
                    View view2=factory.inflate(R.layout.dialog_layout,null);
                    AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                    builder.setView(view2);

                    final EditText dialog_diet=(EditText) view2.findViewById(R.id.dialog_diet);
                    final EditText dialog_work=(EditText) view2.findViewById(R.id.dialog_work);
                    final EditText dialog_exercise=(EditText) view2.findViewById(R.id.dialog_exercise);

                    builder.setTitle("完善你的健康规划目标").setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(dialog_diet.getText().toString().isEmpty()||dialog_work.getText().toString().isEmpty()||dialog_exercise.getText().toString().isEmpty()){
                                Toast.makeText(MainActivity.this, "选项不能有空", Toast.LENGTH_SHORT).show();
                            }else{
                                int diet_max=Integer.parseInt(dialog_diet.getText().toString());
                                int work_max=Integer.parseInt(dialog_work.getText().toString());
                                int exercise_max =Integer.parseInt(dialog_exercise.getText().toString());
                                seekBar1.setMax(diet_max);seekBar2.setMax(work_max);seekBar3.setMax(exercise_max);
                                seekbar1_end.setText(dialog_diet.getText().toString()+"卡路里");
                                seekbar2_end.setText(dialog_work.getText().toString()+"小时");
                                seekbar3_end.setText(dialog_exercise.getText().toString()+"小时");
                                seekbar1_start.setText("0");seekbar2_start.setText("0");seekbar3_start.setText("0");
                                seekBar1.setProgress(0);seekBar2.setProgress(0);seekBar3.setProgress(0);
                                seekBar1.setProgress(0);seekBar2.setProgress(0);seekBar3.setProgress(0);
                            }
                        }
                    }).setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();

                }else{
                    pvOptions2.show();
                    Toast.makeText(MainActivity.this, "card2被点击", Toast.LENGTH_SHORT).show();
                }
            }
        });




        //////////////////////////////////////////////////////////////////////////////////
        //选项选择器1
        pvOptions3 = new OptionsPickerView(this);

        //选项1
        options1Items3.add(new DietBean(0,"Sports","",""));
        options1Items3.add(new DietBean(1,"Exercise","",""));


        //选项2
        ArrayList<String> options2Items3_01=new ArrayList<>();
        options2Items3_01.add("足球");options2Items3_01.add("篮球");
        options2Items3_01.add("羽毛球");options2Items3_01.add("乒乓球");
        ArrayList<String> options2Items3_02=new ArrayList<>();
        options2Items3_02.add("跑步");options2Items3_02.add("健身操");
        options2Items3_02.add("体能");options2Items3_02.add("太极拳");

        options2Items3.add(options2Items3_01);options2Items3.add(options2Items3_02);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items3_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items3_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items3_03 = new ArrayList<>();
        ArrayList<IPickerViewData> options3Items3_01_01=new ArrayList<>();
        options3Items3_01_01.add(new PickerViewData("1h"));options3Items3_01_01.add(new PickerViewData("2h"));
        options3Items3_01_01.add(new PickerViewData("3k"));options3Items3_01_01.add(new PickerViewData("4k"));
        options3Items3_01.add(options3Items3_01_01);
        ArrayList<IPickerViewData> options3Items3_01_02=new ArrayList<>();
        options3Items3_01_02.add(new PickerViewData("1h"));options3Items3_01_02.add(new PickerViewData("2h"));
        options3Items3_01_02.add(new PickerViewData("3h"));options3Items3_01_02.add(new PickerViewData("4h"));
        options3Items_01.add(options3Items_01_02);
        ArrayList<IPickerViewData> options3Items3_01_03=new ArrayList<>();
        options3Items3_01_03.add(new PickerViewData("1h"));options3Items3_01_03.add(new PickerViewData("2h"));
        options3Items3_01_03.add(new PickerViewData("3h"));options3Items3_01_03.add(new PickerViewData("4h"));
        options3Items3_01.add(options3Items3_01_03);
        ArrayList<IPickerViewData> options3Items3_01_04=new ArrayList<>();
        options3Items3_01_04.add(new PickerViewData("1h"));options3Items3_01_04.add(new PickerViewData("2h"));
        options3Items3_01_04.add(new PickerViewData("3h"));
        options3Items3_01.add(options3Items3_01_04);

        ArrayList<IPickerViewData> options3Items3_02_01=new ArrayList<>();
        options3Items3_02_01.add(new PickerViewData("1h"));options3Items3_02_01.add(new PickerViewData("2h"));
        options3Items3_02_01.add(new PickerViewData("3h"));
        options3Items3_02.add(options3Items3_02_01);
        ArrayList<IPickerViewData> options3Items3_02_02=new ArrayList<>();
        options3Items3_02_02.add(new PickerViewData("1h"));options3Items3_02_02.add(new PickerViewData("2h"));
        options3Items3_02_02.add(new PickerViewData("3h"));
        options3Items3_02.add(options3Items3_02_02);
        ArrayList<IPickerViewData> options3Items3_02_03=new ArrayList<>();
        options3Items3_02_03.add(new PickerViewData("1h"));options3Items3_02_03.add(new PickerViewData("2h"));
        options3Items3_02_03.add(new PickerViewData("3h"));
        options3Items3_02.add(options3Items3_02_03);
        ArrayList<IPickerViewData> options3Items3_02_04=new ArrayList<>();
        options3Items3_02_04.add(new PickerViewData("1h"));options3Items3_02_04.add(new PickerViewData("2h"));
        options3Items3_02_04.add(new PickerViewData("3h"));
        options3Items3_02.add(options3Items3_02_04);

        options3Items3.add(options3Items3_01);
        options3Items3.add(options3Items3_02);


        //三级联动效果
        pvOptions3.setPicker(options1Items3, options2Items3, options3Items3, true);
        pvOptions3.setTitle("Exercise");
        pvOptions3.setCyclic(false, true, true);

        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions3.setSelectOptions(1, 1, 1);
        pvOptions3.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx3 = options1Items3.get(options1).getPickerViewText()
                        + ": " +options2Items3.get(options1).get(option2)
                        + "(" +options3Items3.get(options1).get(option2).get(options3).getPickerViewText()+")";
                cardtext3.setText(tx3);
                if(options3Items3.get(options1).get(option2).get(options3).getPickerViewText().toString().equals("1h")){
                    seekBar3.setProgress((seekBar2.getProgress()+1));
                }else if(options3Items3.get(options1).get(option2).get(options3).getPickerViewText().toString().equals("2h")){
                    seekBar3.setProgress((seekBar2.getProgress()+2));
                }else if(options3Items3.get(options1).get(option2).get(options3).getPickerViewText().toString().equals("3h")){
                    seekBar3.setProgress((seekBar2.getProgress()+3));
                }
            }
        });
        ///////////////////////////////////////////////////////////
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seekBar3.getMax()==100){//若目标值仍没有设定，弹出消息框进行设定
                    LayoutInflater factory =LayoutInflater.from(MainActivity.this);
                    View view2=factory.inflate(R.layout.dialog_layout,null);
                    AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                    builder.setView(view2);

                    final EditText dialog_diet=(EditText) view2.findViewById(R.id.dialog_diet);
                    final EditText dialog_work=(EditText) view2.findViewById(R.id.dialog_work);
                    final EditText dialog_exercise=(EditText) view2.findViewById(R.id.dialog_exercise);

                    builder.setTitle("完善你的健康规划目标").setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(dialog_diet.getText().toString().isEmpty()||dialog_work.getText().toString().isEmpty()||dialog_exercise.getText().toString().isEmpty()){
                                Toast.makeText(MainActivity.this, "选项不能有空", Toast.LENGTH_SHORT).show();
                            }else{
                                int diet_max=Integer.parseInt(dialog_diet.getText().toString());
                                int work_max=Integer.parseInt(dialog_work.getText().toString());
                                int exercise_max =Integer.parseInt(dialog_exercise.getText().toString());
                                seekBar1.setMax(diet_max);seekBar2.setMax(work_max);seekBar3.setMax(exercise_max);
                                seekbar1_end.setText(dialog_diet.getText().toString()+"卡路里");
                                seekbar2_end.setText(dialog_work.getText().toString()+"小时");
                                seekbar3_end.setText(dialog_exercise.getText().toString()+"小时");
                                seekbar1_start.setText("0");seekbar2_start.setText("0");seekbar3_start.setText("0");
                                seekBar1.setProgress(0);seekBar2.setProgress(0);seekBar3.setProgress(0);
                                seekBar1.setProgress(0);seekBar2.setProgress(0);seekBar3.setProgress(0);
                            }
                        }
                    }).setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();

                }else{
                    pvOptions3.show();
                    Toast.makeText(MainActivity.this, "card3被点击", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void updated(){
        mDatas = new ArrayList<String>();
        mDatas_id = new ArrayList<String>();
        try{
            Cursor c = db.queryDB();
            while(c.moveToNext()){
                mDatas.add(c.getString(c.getColumnIndex("content")));
                mDatas_id.add(c.getString(c.getColumnIndex("_id")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //得到控件
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        //更新备忘录内容
        updated();
        mAdapter.setRecyclerViewOnItemClickListener(new GalleryAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent_one = new Intent(MainActivity.this,NoteActivity.class);
                Bundle by  = new Bundle();
                by.putString("update",mDatas_id.get(position));
                startActivity(intent_one);
            }
        });
        mAdapter.setOnItemLongClickListener(new GalleryAdapter.RecyclerViewOnItemLongClickListener() {
            @Override
            public boolean onItemLongClickListener(View view, final int position) {
                new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                        .setMessage("确认删除该条目？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public  void onClick(DialogInterface dialog, int which){
                               db.deleteDB(mDatas_id.get(position));
                                updated();
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public  void onClick(DialogInterface dialog, int which){

                            }
                        }).show();

                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_note) {
            startActivity(new Intent(this, NoteActivity.class));
        } else if (id == R.id.nav_daily) {
            startActivity(new Intent(this, DailyRoute.class));
        }
        else if (id == R.id.nav_discovery) {
            startActivity(new Intent(this, GankActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

