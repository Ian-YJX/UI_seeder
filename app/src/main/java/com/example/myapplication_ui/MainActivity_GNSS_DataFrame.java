package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_ui.utils.GNSSParser;
import com.example.myapplication_ui.utils.NMEAParser;

import java.util.List;
import java.util.Map;

public class MainActivity_GNSS_DataFrame extends AppCompatActivity {

    // 定义界面控件
    private EditText inputGNSS; // 输入框
    private TextView GNSSresult; // 输出框
    private TextView NMEAresult; // 用于显示NMEA解析结果

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gnss_dataframe);

        // 初始化界面控件
        inputGNSS = findViewById(R.id.inputField);
        Button parseButton = findViewById(R.id.parseButton);
        GNSSresult = findViewById(R.id.outputGNSS);
        NMEAresult = findViewById(R.id.outputNMEA); // 确保在布局文件中添加了这个控件

        // 按钮点击事件
        parseButton.setOnClickListener(v -> {
            // 获取用户输入
            String input = inputGNSS.getText().toString().trim();

            // 调用 GNSSParser 解析用户输入的命令
            String GNSSoutput = GNSSParser.parseCommand(input);

            // 调用 NMEAParser 解析模拟的 NMEA 数据
            List<Map<String, String>> NMEAData = NMEAParser.parseNMEASimulatedData();

            // 格式化 NMEA 解析结果为字符串
            StringBuilder NMEAoutput = new StringBuilder();
            for (Map<String, String> dataMap : NMEAData) {
                for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                    NMEAoutput.append(entry.getKey()).append(": ").append(entry.getValue()).append("   ");
                }
                NMEAoutput.append("\n \n");
            }

            // 显示解析结果
            GNSSresult.setText(GNSSoutput); // 设置GNSS解析结果
            NMEAresult.setText(NMEAoutput.toString()); // 设置NMEA解析结果
        });
    }
}
