package com.example.myapplication_ui.Menu.Machine_watch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication_ui.MainActivity_home;
import com.example.myapplication_ui.Menu.MainActivity_Menu;
import com.example.myapplication_ui.R;

public class MainActivity_Machine_watch extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_machine_watch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton machine_watch_backtomenu = findViewById(R.id.machine_watch_backtomenu);
        machine_watch_backtomenu.setOnClickListener(v -> {
            // 创建一个Intent对象，指定当前Activity和目标Activity的类
            Intent intent_back_tomenu = new Intent(MainActivity_Machine_watch.this, MainActivity_Menu.class);
            // 可选：传递数据给目标Activity
            // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
            startActivity(intent_back_tomenu);
        });
        ImageButton machine_watch_backtohome = findViewById(R.id.machine_watch_backtohome);
        machine_watch_backtohome.setOnClickListener(v -> {
            // 创建一个Intent对象，指定当前Activity和目标Activity的类
            Intent intent_back_tomenu = new Intent(MainActivity_Machine_watch.this, MainActivity_home.class);
            // 可选：传递数据给目标Activity
            // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
            startActivity(intent_back_tomenu);
        });
    }


}