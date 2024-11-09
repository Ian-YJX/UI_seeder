package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication_ui.Menu.MainActivity_Menu;

public class MainActivity_bozhongzuoye extends AppCompatActivity {


    private TextView zhuju;
    private int currentNumber_zhuju =20;
    private Button zhuju_zeng;
    private Button zhuju_jian;


    private TextView feiliang;
    private int currentNumber_feiliang = 25;
    private Button feiliang_zeng;
    private Button feiliang_jian;
    private ImageButton Bozhongzuoye_backtomenu;
    private ImageButton Bozhongzuoye_backtohome;
    //private Button working;
    private Button error;
    private Button disable;
    private ImageButton imageRow1;
    private ImageButton imageRow2;
    private ImageButton imageRow3;
    private ImageButton imageRow4;
    private ImageButton imageShifei;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_bozhongzuoye);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 针对按键的判定程序,针对株距增减的确认；
        zhuju = findViewById(R.id.zhuju);
        zhuju_zeng= findViewById(R.id.zhuju_zeng);
        zhuju_jian=findViewById(R.id.zhuju_jian);
        zhuju_zeng.setOnClickListener(new View.OnClickListener( ){
            @Override
            //针对按键的判断说明
            public void onClick(View v) {
                if(currentNumber_zhuju<40){
                currentNumber_zhuju += 2;
                zhuju.setText(String.valueOf(currentNumber_zhuju));
                }
            }
        });
        zhuju_jian.setOnClickListener(new View.OnClickListener( ){
            @Override
            //针对按键的判断说明
            public void onClick(View v) {
                if(currentNumber_zhuju>10) {
                    currentNumber_zhuju -= 2;
                    zhuju.setText(String.valueOf(currentNumber_zhuju));
                }
            }
        });


        // 需要增加相关施肥情况的程序内容

        feiliang = findViewById(R.id.feiling);
        feiliang_zeng= findViewById(R.id.feiliang_zeng);
        feiliang_jian=findViewById(R.id.feiliang_jian);
        feiliang_zeng.setOnClickListener(new View.OnClickListener( ){
            @Override
            //针对按键的判断说明
            public void onClick(View v) {
                if(currentNumber_feiliang<40){
                currentNumber_feiliang += 1;
                feiliang.setText(String.valueOf(currentNumber_feiliang));
                }
            }
        });
        feiliang_jian.setOnClickListener(new View.OnClickListener( ){
            @Override
            //针对按键的判断说明
            public void onClick(View v) {
                if(currentNumber_feiliang>10){
                currentNumber_feiliang -= 1;
                feiliang.setText(String.valueOf(currentNumber_feiliang));
                }
            }
        });

        //仿照数据解析按键
        //working=findViewById(R.id.working);
        error=findViewById(R.id.error);
        disable=findViewById(R.id.disable);


        imageRow1=findViewById(R.id.imageRow1);
        imageRow2=findViewById(R.id.imageRow2);
        imageRow3=findViewById(R.id.imageRow3);
        imageRow4=findViewById(R.id.imageRow4);
        imageShifei=findViewById(R.id.imageShifei);
        // 设置“正常”按钮的点击事件
//        working.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("DEBUG", "Working button clicked");
//                imageRow1.setImageResource(R.drawable.bojiselect);
//                imageRow2.setImageResource(R.drawable.bojiselect);
//                imageRow3.setImageResource(R.drawable.bojiselect);
//                imageRow4.setImageResource(R.drawable.bojiselect);
//                imageShifei.setImageResource(R.drawable.tbjdisa);
//            }
//        });

        imageRow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 tag，并确保是字符串类型
                Object tagObj = imageRow1.getTag();
                String tag = tagObj != null ? tagObj.toString() : "";

                if ("@drawable/tbjdisa".equals(tag)) {
                    // 如果当前是 tbjdisa，则切换到 tbj
                    imageRow1.setImageResource(R.drawable.tbj);
                    imageRow1.setTag("@drawable/tbj"); // 更新tag为字符串
                } else {
                    // 否则切换到 tbjdisa
                    imageRow1.setImageResource(R.drawable.tbjdisa);
                    imageRow1.setTag("@drawable/tbjdisa"); // 更新tag为字符串
                }
            }
        });



        imageRow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 tag，并确保是字符串类型
                Object tagObj = imageRow2.getTag();
                String tag = tagObj != null ? tagObj.toString() : "";

                if ("@drawable/tbjdisa".equals(tag)) {
                    // 如果当前是 tbjdisa，则切换到 tbj
                    imageRow2.setImageResource(R.drawable.tbj);
                    imageRow2.setTag("@drawable/tbj"); // 更新tag为字符串
                } else {
                    // 否则切换到 tbjdisa
                    imageRow2.setImageResource(R.drawable.tbjdisa);
                    imageRow2.setTag("@drawable/tbjdisa"); // 更新tag为字符串
                }
            }
        });


        imageShifei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 tag，并确保是字符串类型
                Object tagObj = imageShifei.getTag();
                String tag = tagObj != null ? tagObj.toString() : "";

                if ("@drawable/tbjdisa".equals(tag)) {
                    // 如果当前是 tbjdisa，则切换到 tbj
                    imageShifei.setImageResource(R.drawable.tbj);
                    imageShifei.setTag("@drawable/tbj"); // 更新tag为字符串
                } else {
                    // 否则切换到 tbjdisa
                    imageShifei.setImageResource(R.drawable.tbjdisa);
                    imageShifei.setTag("@drawable/tbjdisa"); // 更新tag为字符串
                }
            }
        });


        imageRow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 tag，并确保是字符串类型
                Object tagObj = imageRow3.getTag();
                String tag = tagObj != null ? tagObj.toString() : "";

                if ("@drawable/tbjdisa".equals(tag)) {
                    // 如果当前是 tbjdisa，则切换到 tbj
                    imageRow3.setImageResource(R.drawable.tbj);
                    imageRow3.setTag("@drawable/tbj"); // 更新tag为字符串
                } else {
                    // 否则切换到 tbjdisa
                    imageRow3.setImageResource(R.drawable.tbjdisa);
                    imageRow3.setTag("@drawable/tbjdisa"); // 更新tag为字符串
                }
            }
        });


        imageRow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 tag，并确保是字符串类型
                Object tagObj = imageRow4.getTag();
                String tag = tagObj != null ? tagObj.toString() : "";

                if ("@drawable/tbjdisa".equals(tag)) {
                    // 如果当前是 tbjdisa，则切换到 tbj
                    imageRow4.setImageResource(R.drawable.tbj);
                    imageRow4.setTag("@drawable/tbj"); // 更新tag为字符串
                } else {
                    // 否则切换到 tbjdisa
                    imageRow4.setImageResource(R.drawable.tbjdisa);
                    imageRow4.setTag("@drawable/tbjdisa"); // 更新tag为字符串
                }
            }
        });

        // 设置“错误”按钮的点击事件
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageRow1.setImageResource(R.drawable.bojierror);
                imageRow2.setImageResource(R.drawable.bojierror);
                imageRow3.setImageResource(R.drawable.bojierror);
                imageRow4.setImageResource(R.drawable.bojierror);
                imageShifei.setImageResource(R.drawable.tbjdisa);
            }
        });

        // 设置“禁用”按钮的点击事件
        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageRow1.setImageResource(R.drawable.bojidisable);
                imageRow2.setImageResource(R.drawable.bojidisable);
                imageRow3.setImageResource(R.drawable.bojidisable);
                imageRow4.setImageResource(R.drawable.bojidisable);
                imageShifei.setImageResource(R.drawable.tbjdisa); // 施肥电机变为禁用状态
            }
        });

        Bozhongzuoye_backtomenu= findViewById(R.id.bozhongzuoye_backtomenu);
        Bozhongzuoye_backtomenu.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           // 创建一个Intent对象，指定当前Activity和目标Activity的类
                                                           Intent intent_back_tomenu = new Intent(MainActivity_bozhongzuoye.this, MainActivity_Menu.class);
                                                           // 可选：传递数据给目标Activity
                                                           // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                                                           startActivity(intent_back_tomenu);
                                                       }
                                                   });
        Bozhongzuoye_backtohome= findViewById(R.id.bozhongzuoye_backtohome);
        Bozhongzuoye_backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent_back_tomenu = new Intent(MainActivity_bozhongzuoye.this, MainActivity_home.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                startActivity(intent_back_tomenu);
            }
        });
        //增加一个确定按钮，完成相关的确定，当确认设定后就才将数据发送出去；

    }
}