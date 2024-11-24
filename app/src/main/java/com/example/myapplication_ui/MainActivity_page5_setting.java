package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication_ui.utils.CANFrameGenerator;
import com.example.myapplication_ui.utils.DBCParser;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity_page5_setting extends AppCompatActivity {

    private EditText speedSet;
    private CheckBox checkBox1, checkBox2, checkBox3;
    private TextView textCAN;
    private EditText feiliang;
    private int boxIndex = 1;
    private DBCParser.Message currentMessage;

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page5_setting);

        // 设置系统边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化视图
        initViews();

        // 设置复选框和输入框监听器
        initListeners();
        loadDBCFile();
    }

    // 初始化视图
    private void initViews() {
        speedSet = findViewById(R.id.chesu);
        speedSet.setEnabled(false);
        ImageButton settingBackToHome = findViewById(R.id.setting_backtohome);
        settingBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_page5_setting.this, MainActivity_page0_home.class);
            startActivity(intent);
        });

        ImageButton settingBackToMenu = findViewById(R.id.setting_backtomenu);
        settingBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_page5_setting.this, MainActivity_page1_menu.class);
            startActivity(intent);
        });

        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.speed_set_con);
        textCAN = findViewById(R.id.CANe2);
        feiliang = findViewById(R.id.feiliangPerCircle);
        checkBox1.isChecked();
        handleCheckboxChange(true, 1, checkBox1, checkBox2, checkBox3);
    }


    private void checkboxCheck(CheckBox uncheckedBox) {
        if (!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()) {
            uncheckedBox.setChecked(true);
            updateCAN();
        }
    }

    // 设置监听器
    private void initListeners() {
        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange(isChecked, 1, checkBox1, checkBox2, checkBox3));

        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> handleCheckboxChange(isChecked, 0, checkBox2, checkBox1, checkBox3));

        checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            handleCheckboxChange(isChecked, 2, checkBox3, checkBox1, checkBox2);
            speedSet.setEnabled(isChecked);
            if (!isChecked) {
                speedSet.setText("0.00");
            }
        });

        setInputFieldListener(feiliang, 32.768, 1000, (binaryValue) -> updateCAN());

        setInputFieldListener(speedSet, 81.92, 100, (binaryValue) -> updateCAN());
    }

    // 处理复选框状态切换
    private void handleCheckboxChange(boolean isChecked, int boxValue, CheckBox current, CheckBox other1, CheckBox other2) {
        if (isChecked) {
            other1.setChecked(false);
            other2.setChecked(false);
            boxIndex = boxValue;
            updateCAN();
        } else {
            checkboxCheck(current);
        }
    }

    private void updateCAN() {
        if (currentMessage == null) {
            textCAN.setText("未加载 CAN 消息");
            Log.d("currentMessage", "NULL");
            return;
        }
        Log.d("currentMessage", "Not NULL");
        String feiliangStr=feiliang.getText().toString();
        Log.d("feiliang raw_data",feiliangStr);
        double feiliangDouble=Double.parseDouble(feiliang.getText().toString());
        Log.d("feiliang int", String.valueOf(feiliangDouble));

        Map<String, String> values = new HashMap<>();
        values.put("code", "226");
        values.put("Speed_Cmd", String.valueOf(boxIndex));
        values.put("Speed_Value", speedSet.getText().toString());
        values.put("fertilizer_Value", feiliang.getText().toString());
        Log.d("values", values.toString());
        try {
            String hexCAN = CANFrameGenerator.generateFrame(currentMessage, values);
            textCAN.setText(hexCAN);
        } catch (Exception e) {
            textCAN.setText("生成数据帧失败：" + e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void loadDBCFile() {
        try (InputStream inputStream = getAssets().open("0xe2.dbc")) {
            // 读取文件内容
            String dbcContent = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();

            // 解析 DBC 文件
            List<DBCParser.Message> messages = DBCParser.parseDBCString(dbcContent);
            Log.d("DBC Messages", "Total messages parsed: " + messages.size());
            for (DBCParser.Message message : messages) {
                Log.d("DBC Message ID", message.id); // 打印所有消息 ID
            }

            // 查找特定消息
            currentMessage = messages.stream()
                    .filter(m -> "18FDD002".equalsIgnoreCase(m.id)) // 忽略大小写比较
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("未找到指定消息"));
            //Log.d("DBC Selected Message", "Found message with ID: " + currentMessage.id);
            Log.d("message_id",currentMessage.getId());
            Log.d("message_dlc",Integer.toString(currentMessage.getDlc()));
        } catch (Exception e) {
            Toast.makeText(this, "加载 DBC 文件失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("DBC Error", "解析错误", e); // 打印详细错误日志
        }
    }

    // 设置输入框监听器
    private void setInputFieldListener(EditText inputField, double maxValue, int multiplier, InputChangeCallback callback) {
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                if (input.isEmpty()) {
                    input = "0.00";
                    inputField.setText(input);
                }

                double value = Double.parseDouble(input);
                if (value < 0.0) {
                    Log.d("IllegalValue"+value,"<0");
                    value = 0.0;
                    inputField.setText(String.valueOf(value));
                    Log.d("Revised text",inputField.getText().toString());
                } else if (value > maxValue) {
                    Log.d("IllegalValue"+value,">"+ maxValue);
                    value = maxValue;
                    inputField.setText(String.valueOf(value));

                }

                String binaryValue = Integer.toBinaryString((int) (value * multiplier));
                callback.onValueChanged(binaryValue);
            }
        });
    }

    // 回调接口，用于处理输入变更
    interface InputChangeCallback {
        void onValueChanged(String binaryValue);
    }
}
