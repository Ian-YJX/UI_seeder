package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication_ui.Menu.MainActivity_Menu;

import org.w3c.dom.Text;

public class MainActivity_home extends AppCompatActivity {
    private ImageButton goto_menu;
    private TextView speed_display;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


       goto_menu= findViewById(R.id.goto_menu);
       goto_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent_goto_menu = new Intent(MainActivity_home.this, MainActivity_Menu.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                startActivity(intent_goto_menu);
            }
        });



        Intent intent = getIntent();
        //读取了针对速度的设定值， 这个值应该还是要依据GPS测速进行判断的；
        //
        String stringValue_speed =intent.getStringExtra("speed_set");
        speed_display=findViewById(R.id.speed_display_xml);
        speed_display.setText(String.valueOf(stringValue_speed));


    }
}