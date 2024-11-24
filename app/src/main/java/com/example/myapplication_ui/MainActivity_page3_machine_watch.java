package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity_page3_machine_watch extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page3_machine_watch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 获取通过Intent传递过来的数据
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String status = intent.getStringExtra("status");
        String rotationRate = intent.getStringExtra("rotationRate");
        String voltage = intent.getStringExtra("voltage");
        String current = intent.getStringExtra("current");
        String temperature = intent.getStringExtra("temperature");
        String circleNum = intent.getStringExtra("circleNum");
        String motorStatus = intent.getStringExtra("motorStatus");

        // 获取TableLayout和第二行
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        TableRow tableRow = (TableRow) tableLayout.getChildAt(1); // 获取第二行

        // 获取表格中的TextView
        TextView nameTextView = (TextView) tableRow.getChildAt(0); // 第一列
        TextView statusTextView = (TextView) tableRow.getChildAt(1); // 第二列
        TextView rotationRateTextView = (TextView) tableRow.getChildAt(2); // 第三列
        TextView voltageTextView = (TextView) tableRow.getChildAt(3); // 第四列
        TextView currentTextView = (TextView) tableRow.getChildAt(4); // 第五列
        TextView temperatureTextView = (TextView) tableRow.getChildAt(5); // 第六列
        TextView circleNumTextView = (TextView) tableRow.getChildAt(6); // 第七列
        TextView motorStatusTextView = (TextView) tableRow.getChildAt(7); // 第八列

        // 将Intent传递的数据填充到表格的相应位置
        nameTextView.setText(name);
        statusTextView.setText(status);
        rotationRateTextView.setText(rotationRate);
        voltageTextView.setText(voltage);
        currentTextView.setText(current);
        temperatureTextView.setText(temperature);
        circleNumTextView.setText(circleNum);
        motorStatusTextView.setText(motorStatus);

        // 设置返回按钮的点击事件
        ImageButton machine_watch_backtomenu = findViewById(R.id.machine_watch_backtomenu);
        machine_watch_backtomenu.setOnClickListener(v -> {
            // 创建一个Intent对象，指定当前Activity和目标Activity的类
            Intent intent_back_tomenu = new Intent(MainActivity_page3_machine_watch.this, MainActivity_page1_menu.class);
            // 可选：传递数据给目标Activity
            startActivity(intent_back_tomenu);
        });

        // 设置返回到主界面的按钮点击事件
        ImageButton machine_watch_backtohome = findViewById(R.id.machine_watch_backtohome);
        machine_watch_backtohome.setOnClickListener(v -> {
            // 创建一个Intent对象，指定当前Activity和目标Activity的类
            Intent intent_back_tomenu = new Intent(MainActivity_page3_machine_watch.this, MainActivity_page0_home.class);
            // 可选：传递数据给目标Activity
            startActivity(intent_back_tomenu);
        });
    }
}
