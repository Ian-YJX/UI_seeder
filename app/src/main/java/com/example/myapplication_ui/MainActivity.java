package com.example.myapplication_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View.OnClickListener;

import java.nio.channels.InterruptedByTimeoutException;
//import android.view.View.OnClickListener;

//针对CAN的相关内容的添加
//需要在后面进行修订
// import com.cpdevice.cpcomm.common.CPCanFilterStruct;
//import com.cpdevice.cpcomm.common.CPCanFrameRxListener;
//import com.cpdevice.cpcomm.common.ServiceLostListener;
//import com.cpdevice.cpcomm.exception.CPBusException;
//import com.cpdevice.cpcomm.frame.ICPCanFrame;
//import com.cpdevice.cpcomm.proto.CPVxProtocolProxy;
//import com.cpdevice.cpcomm.proto.Protocol;
//


//默认的Activity
public class MainActivity extends AppCompatActivity{
    private EditText editText;
    private Button button1;
    private Button chang1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // 设置当期的布局模式
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //  Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //return insets;}


        //自主程序：

        // 通过findViewById找到EditText和Button控件
        editText = findViewById(R.id.editText); // 假设你的EditText控件的ID是editText
        button1 = findViewById(R.id.button1); // 假设你的Button控件的ID是button

        // 为Button设置点击事件监听器
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 当按钮被点击时，获取EditText中的文本内容，并将其转换成了string
                String userInput = editText.getText().toString();
                // 注意这里的editText是前面定义的相关文件的名称，是可以进行修订的
                // 这里可以处理用户输入的数据，例如显示一个Toast,
                Toast.makeText(MainActivity.this, "用户输入了: " + userInput, Toast.LENGTH_SHORT).show();
                // 你也可以在这里发送数据到其他地方，比如另一个Activity、Fragment、Service或者通过广播
            }
        });
        // 设置点击事件监听器
        //当按键被点击后、
        //首先找到这个按键，然后针对按键建立监听，监听被触发后，使用intent，定义后进行跳转
        //同步可以在intent中进行数据的传递
        chang1 = findViewById(R.id.chang1);
        chang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前Activity和目标Activity的类
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                // 可选：传递数据给目标Activity
                // intent.putExtra("key", "value").调用startActivity方法实现页面跳转
                intent.putExtra( "name","liu");
                intent.putExtra("age","36.945");
                startActivity(intent);
            }
        });
        //到这里是一个事件的结束；
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");


        // 定义一个toast，然后通过show将其展示出来
        Toast toast = Toast.makeText(this, "name  : " + name + "age:" + age, Toast.LENGTH_SHORT);
        //跳转的时候发送的数据
        toast.show();

    }


}

