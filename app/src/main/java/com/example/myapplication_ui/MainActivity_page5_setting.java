package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication_ui.utils.CANFrameGenerator;
import com.example.myapplication_ui.utils.DBCParser;

import java.util.List;

public class MainActivity_page5_setting extends AppCompatActivity {

    private EditText speedSet;
    private CheckBox checkBox1, checkBox2, checkBox3;
    private TextView CANe2;
    private EditText feiliang;
    private String boxIndex = "01";
    private String speedBinary = "0000000000000";
    private String code = "11100010";
    private String meiquanfeiliang = "000000000000000";
    private String rest = "";
    private DBCParser.Message currentMessage;
    private List<DBCParser.Message> messages;

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
        initializeViews();

        // 确保复选框至少有一个选中
        ensureAtLeastOneChecked();

        // 设置复选框和输入框监听器
        setListeners();
    }

    // 更新 CAN 信息
    private void updateCAN() {
        String CANBinary, CANHex;

        code = formatBinary(code, 8);
        boxIndex = formatBinary(boxIndex, 2);
        speedBinary = formatBinary(speedBinary, 13);
        meiquanfeiliang = formatBinary(meiquanfeiliang, 15);
        rest = formatBinary(rest, 26);

        CANBinary = code + boxIndex + speedBinary + meiquanfeiliang + rest;
        Log.d("Binary", CANBinary);

        CANHex = "0x" + binaryToHex(CANBinary);
        Log.d("Hex", CANHex);

        CANe2.setText(CANHex);
    }

    // 初始化视图
    private void initializeViews() {
        speedSet = findViewById(R.id.chesu);
        speedSet.setEnabled(false);

        Button speedSetCon = findViewById(R.id.speed_set_con);
        speedSetCon.setOnClickListener(v -> {
            String speed = speedSet.getText().toString();
            Log.d("Speed", "Speed value: " + speed);
        });

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
        CANe2 = findViewById(R.id.CANe2);
        feiliang = findViewById(R.id.feiliangPerCircle);

        updateCAN();
    }

    // 确保至少一个复选框被选中
    private void ensureAtLeastOneChecked() {
        if (!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()) {
            checkBox1.setChecked(true);
        }
    }

    private void ensureAtLeastOneChecked(CheckBox uncheckedBox) {
        if (!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()) {
            uncheckedBox.setChecked(true);
        }
    }

    // 设置监听器
    private void setListeners() {
        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            handleCheckboxChange(isChecked, "01", checkBox1, checkBox2, checkBox3);
        });

        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            handleCheckboxChange(isChecked, "00", checkBox2, checkBox1, checkBox3);
        });

        checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            handleCheckboxChange(isChecked, "10", checkBox3, checkBox1, checkBox2);
            speedSet.setEnabled(isChecked);
            if (!isChecked) {
                speedSet.setText("0.00");
            }
        });

        setInputFieldListener(feiliang, 0.0, 32.768, 1000, (binaryValue) -> {
            meiquanfeiliang = binaryValue;
            updateCAN();
        });

        setInputFieldListener(speedSet, 0.0, 81.92, 100, (binaryValue) -> {
            speedBinary = binaryValue;
            updateCAN();
        });
    }

    // 处理复选框状态切换
    private void handleCheckboxChange(boolean isChecked, String boxValue, CheckBox current, CheckBox other1, CheckBox other2) {
        if (isChecked) {
            other1.setChecked(false);
            other2.setChecked(false);
            boxIndex = boxValue;
            updateCAN();
        } else {
            ensureAtLeastOneChecked(current);
        }
    }

    // 设置输入框监听器
    private void setInputFieldListener(EditText inputField, double minValue, double maxValue, int multiplier, InputChangeCallback callback) {
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
                    input = "0.000";
                    inputField.setText(input);
                }

                double value = Double.parseDouble(input);
                if (value < minValue) {
                    value = minValue;
                    inputField.setText(String.valueOf(value));
                } else if (value > maxValue) {
                    value = maxValue;
                    inputField.setText(String.valueOf(value));
                }

                String binaryValue = Integer.toBinaryString((int) (value * multiplier));
                callback.onValueChanged(binaryValue);
            }
        });
    }

    // 格式化二进制字符串
    private String formatBinary(String binary, int length) {
        return String.format("%" + length + "s", binary).replace(' ', '0');
    }

    // 将二进制字符串转换为十六进制
    private String binaryToHex(String binary) {
        if (binary == null || binary.isEmpty()) {
            throw new IllegalArgumentException("Input binary string cannot be null or empty");
        }

        binary = binary.replaceAll("[^01]", ""); // 仅保留 0 和 1

        while (binary.length() % 4 != 0) {
            binary = "0" + binary;
        }

        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 4) {
            String fourBits = binary.substring(i, i + 4);
            int decimal = Integer.parseInt(fourBits, 2);
            hex.append(Integer.toHexString(decimal));
        }

        return hex.toString().toUpperCase();
    }

    // 回调接口，用于处理输入变更
    interface InputChangeCallback {
        void onValueChanged(String binaryValue);
    }
}
