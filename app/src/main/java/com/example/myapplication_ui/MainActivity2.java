package com.example.myapplication_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity2 extends AppCompatActivity {
    private Button chang2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //简单的话，


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        //将activity与layout建立链接
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");
        // 定义一个toast，然后通过show将其展示出来
        Toast toast = Toast.makeText(this, "name  : " + name + "age:" + age, Toast.LENGTH_SHORT);
        //跳转的时候发送的数据
        toast.show();


    //这个方式是可以的，但是在一开始的时候就进行了广播，应该要加一个判断的语言，
        chang2=findViewById(R.id.chang2);
        chang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent2 = new Intent(MainActivity2.this, MainActivity.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转

                intent2.putExtra("name", "zhang");
                intent2.putExtra("age", "25");
                startActivity(intent2);
            }
        });






}

}