package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity_page0_home extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page0_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageButton goto_menu = findViewById(R.id.goto_menu);
        goto_menu.setOnClickListener(v -> {
            // 创建一个Intent对象，指定当前Activity和目标Activity的类
            Intent intent_goto_menu = new Intent(MainActivity_page0_home.this, MainActivity_page1_menu.class);
            // 可选：传递数据给目标Activity
            // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
            startActivity(intent_goto_menu);
        });


        Intent intent = getIntent();
        //读取了针对速度的设定值， 这个值应该还是要依据GPS测速进行判断的；
        //
        String stringValue_speed = intent.getStringExtra("speed_set");
        TextView speed_display = findViewById(R.id.speed_display_xml);
        speed_display.setText(String.valueOf(stringValue_speed));


    }
}