package com.example.myapplication_ui.Menu.setting;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.myapplication_ui.MainActivity_home;
import com.example.myapplication_ui.Menu.MainActivity_Menu;
import com.example.myapplication_ui.R;

public class MainActivity3_setting extends AppCompatActivity {

    private  EditText speed_set;
    private Button speed_set_con;
    private ImageButton setting_backtohome;
    private String input_speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_activity3_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // input here
/*
        speed_set_con= findViewById(R.id.speed_set_con);
        speed_set_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }

            ;
        });*/




        speed_set = findViewById(R.id.chesu);
        // 使用TextWatcher监听文本变化
        speed_set.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 不需要处理
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 可以在这里处理文本变化，但通常不需要立即处理每个字符的输入
            }
            @Override
            public void afterTextChanged(Editable S) {
                // 当文本变化完成后，这里可以获取输入的值
                String input = S.toString();
                // 你可以在这里处理输入的值，比如显示在一个TextView上或进行其他逻辑处理
                Log.d("MainActivity3_setting", "Input value: " + input);
            }
        });

        // 或者，你可以设置一个按钮来在用户点击后获取值

        speed_set_con = findViewById(R.id.speed_set_con);
        speed_set_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_speed = speed_set.getText().toString();
                // 处理输入的值
                Toast.makeText(MainActivity3_setting.this, "speed="+input_speed, Toast.LENGTH_SHORT).show();
                //将数据放在intent中
                //Log.d("Main", "Submitted value: " + input_speed);
            }
        });


        setting_backtohome= findViewById(R.id.setting_backtohome);
        setting_backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                //Intent intent_back_tomenu = new Intent(MainActivity_bozhongzuoye.this, MainActivity_Menu.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                //Intent intent_back_home = Intent(MainActivity3_setting.this, MainActivity_home.class);
                Intent intent_setting=new Intent(MainActivity3_setting.this, MainActivity_home.class);
                intent_setting.putExtra("speed_set",input_speed);
                startActivity(intent_setting);
            }
        });

    }
/*
    //这个是屏幕跳出输入的VOid
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入值");
        // 设置输入框
        final EditText input = new EditText(this);
        input.setHint("请输入内容");
        builder.setView(input);

        // 设置对话框的按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 在这里获取输入框的值
                String value = input.getText().toString();
                // 这里可以对获取到的值进行处理，例如显示到Toast或TextView中
                Toast.makeText(MainActivity3_setting.this, "输入的值是: " + value, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);

        // 显示对话框
        builder.show();
    }
    */



}