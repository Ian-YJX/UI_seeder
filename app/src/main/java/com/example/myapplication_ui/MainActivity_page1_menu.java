package com.example.myapplication_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity_page1_menu extends AppCompatActivity {


    private ImageButton bozhongzuoye;
    private ImageButton Machine_watch;
    private ImageButton seed_watch;
    private ImageButton setting;
    private ImageButton back_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page1_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


//针对按键进行的监控
        //1 针对播种电机的设定,通过按键可以进入到播种作业所对应的activity
        bozhongzuoye = findViewById(R.id.Bozhongzuoye);
        bozhongzuoye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent_bozhongzuoye = new Intent(MainActivity_page1_menu.this, MainActivity_page2_bozhongzuoye.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                startActivity(intent_bozhongzuoye);
            }

            //
        });


        // 2、针对电机监控的设计,通过按键可以进入到播种作业所对应的activity
        Machine_watch = findViewById(R.id.Machine_watch);
        Machine_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent_Machine_watch = new Intent(MainActivity_page1_menu.this, MainActivity_page3_machine_watch.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                startActivity(intent_Machine_watch);
            }

            //
        });

        //3 针对种子监控
        seed_watch = findViewById(R.id.seed_watch);
        seed_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent_seed_watch = new Intent(MainActivity_page1_menu.this, MainActivity_page4_seed_watch.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                startActivity(intent_seed_watch);
            }

            //
        });

        //4  setting
        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent_setting = new Intent(MainActivity_page1_menu.this, MainActivity_page5_setting.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                startActivity(intent_setting);
            }

            //
        });
        //  5  home
        back_home = findViewById(R.id.setting_back);
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent_back_home = new Intent(MainActivity_page1_menu.this, MainActivity_page0_home.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                startActivity(intent_back_home);
            }

            //
        });


    }

}