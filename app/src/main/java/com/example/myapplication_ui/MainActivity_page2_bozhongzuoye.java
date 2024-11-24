package com.example.myapplication_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_ui.utils.CANFrameGenerator;
import com.example.myapplication_ui.utils.DBCParser;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class MainActivity_page2_bozhongzuoye extends AppCompatActivity {

    private TextView zhuju, feiliang, textCAN;
    private Button zhuju_zeng, zhuju_jian, feiliang_zeng, feiliang_jian, error, disable;
    private ImageButton Bozhongzuoye_backtomenu, Bozhongzuoye_backtohome;
    private ImageButton imageRow1, imageRow2, imageRow3, imageRow4, imageShifei;
    private Spinner seedType;

    private String selectedSeedType; // 当前选中的种子类型
    private DBCParser.Message currentMessage;

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page2_bozhongzuoye);

        initViews();
        setupSpinner();
        initListeners();
        loadDBCFile();
    }

    // 初始化控件
    private void initViews() {
        zhuju = findViewById(R.id.zhuju);
        feiliang = findViewById(R.id.feiliang);
        textCAN = findViewById(R.id.CANe1);

        zhuju_zeng = findViewById(R.id.zhuju_zeng);
        zhuju_jian = findViewById(R.id.zhuju_jian);
        feiliang_zeng = findViewById(R.id.feiliang_zeng);
        feiliang_jian = findViewById(R.id.feiliang_jian);
        error = findViewById(R.id.error);
        disable = findViewById(R.id.disable);

        Bozhongzuoye_backtomenu = findViewById(R.id.bozhongzuoye_backtomenu);
        Bozhongzuoye_backtohome = findViewById(R.id.bozhongzuoye_backtohome);

        imageRow1 = findViewById(R.id.imageRow1);
        imageRow2 = findViewById(R.id.imageRow2);
        imageRow3 = findViewById(R.id.imageRow3);
        imageRow4 = findViewById(R.id.imageRow4);
        imageShifei = findViewById(R.id.imageShifei);

        seedType = findViewById(R.id.seedType);
    }

    // 初始化监听器
    private void initListeners() {
        // 株距增减按钮
        setButtonListeners(zhuju, zhuju_zeng, zhuju_jian, 2);

        // 肥量增减按钮
        setButtonListeners(feiliang, feiliang_zeng, feiliang_jian, 1);

        // 返回菜单和首页
        setNavigationListener(Bozhongzuoye_backtomenu, MainActivity_page1_menu.class);
        setNavigationListener(Bozhongzuoye_backtohome, MainActivity_page0_home.class);

        // 电机和施肥图标监听
        setToggleImageListener(imageRow1);
        setToggleImageListener(imageRow2);
        setToggleImageListener(imageRow3);
        setToggleImageListener(imageRow4);
        setToggleImageListener(imageShifei);

        // 错误按钮
        error.setOnClickListener(v -> handleImageUpdate(R.drawable.bojierror, R.drawable.tbjdisa));

        // 禁用按钮
        disable.setOnClickListener(v -> handleImageUpdate(R.drawable.tbjdisa, R.drawable.tbjdisa));
    }

    private void setButtonListeners(TextView textView, Button incrementButton, Button decrementButton, int step) {
        incrementButton.setOnClickListener(v -> adjustValue(textView, step, 40, true));
        decrementButton.setOnClickListener(v -> adjustValue(textView, step, 10, false));
    }

    private void setNavigationListener(ImageButton button, Class<?> targetActivity) {
        button.setOnClickListener(v -> startActivity(new Intent(this, targetActivity)));
    }

    private void setToggleImageListener(ImageButton imageButton) {
        imageButton.setOnClickListener(v -> {
            toggleImage(imageButton);
            updateTextCAN();
        });
    }

    private void toggleImage(ImageButton imageButton) {
        String tag = (String) imageButton.getTag();
        if ("tbjdisa".equals(tag)) {
            imageButton.setImageResource(R.drawable.tbj);
            imageButton.setTag("tbj");
        } else {
            imageButton.setImageResource(R.drawable.tbjdisa);
            imageButton.setTag("tbjdisa");
        }
    }

    private void handleImageUpdate(int rowResId, int shifeiResId) {
        updateAllImages(rowResId, shifeiResId);
        updateTextCAN();
    }

    private void updateAllImages(int rowResId, int shifeiResId) {
        setImage(imageRow1, rowResId);
        setImage(imageRow2, rowResId);
        setImage(imageRow3, rowResId);
        setImage(imageRow4, rowResId);
        setImage(imageShifei, shifeiResId);
    }

    private void setImage(ImageButton button, int resId) {
        button.setImageResource(resId);
    }

    // 调整数值
    private void adjustValue(TextView textView, int step, int limit, boolean increase) {
        int currentValue = Integer.parseInt(textView.getText().toString());
        currentValue = increase ? Math.min(currentValue + step, limit) : Math.max(currentValue - step, limit);
        textView.setText(String.valueOf(currentValue));
        updateTextCAN();
    }

    // 加载 DBC 文件
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void loadDBCFile() {
        try (InputStream inputStream = getAssets().open("0xe1.dbc")) {
            String dbcContent = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            List<DBCParser.Message> messages = DBCParser.parseDBCString(dbcContent);
            currentMessage = messages.stream()
                    .filter(m -> "18FDD001".equals(m.id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("未找到指定消息"));
            Log.d("message_id",currentMessage.getId());
            Log.d("message_dlc",Integer.toString(currentMessage.getDlc()));
            Log.d("message_signals", currentMessage.getSignals().toString());
        } catch (Exception e) {
            Toast.makeText(this, "加载 DBC 文件失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // 更新 CAN 数据帧
    @SuppressLint("SetTextI18n")
    private void updateTextCAN() {
        if (currentMessage == null) {
            textCAN.setText("未加载 CAN 消息");
            return;
        }
        String feiliangStr=feiliang.getText().toString();
        Log.d("feiliang raw_data",feiliangStr);
        int feiliangInt=Integer.parseInt(feiliang.getText().toString());
        Log.d("feiliang int", String.valueOf(feiliangInt));

        Map<String, String> values = new HashMap<>();
        values.put("code", "225");
        values.put("system_setting_Cmd", "2");
        values.put("Seed_type_Cmd", String.valueOf(getSeedTypeCmd()));
        values.put("Distance_Cmd", zhuju.getText().toString());
        values.put("fertilizer_Cmd", String.valueOf(feiliangInt));
        values.put("seed_motor_p1_Cmd", String.valueOf(getMotorCmd(imageRow1)));
        values.put("seed_motor_p2_Cmd", String.valueOf(getMotorCmd(imageRow2)));
        values.put("seed_motor_p3_Cmd", String.valueOf(getMotorCmd(imageRow3)));
        values.put("seed_motor_p4_Cmd", String.valueOf(getMotorCmd(imageRow4)));
        values.put("fertilizer_motor_Cmd", String.valueOf(getMotorCmd(imageShifei)));

        //Log.d("shifeiMotor status",String.valueOf(getMotorCmd(imageShifei)));
        try {
            String hexCAN = CANFrameGenerator.generateFrame(currentMessage, values);
            textCAN.setText(hexCAN);
        } catch (Exception e) {
            textCAN.setText("生成数据帧失败：" + e.getMessage());
        }
    }

    private int getSeedTypeCmd() {
        switch (selectedSeedType) {
            case "玉米": return 0;
            case "花生": return 1;
            case "高粱": return 2;
            default: return 3;
        }
    }

    private int getMotorCmd(ImageButton button) {
        return "tbj".equals(button.getTag()) ? 1 : 0;
    }

    // 初始化 Spinner
    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.seed_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seedType.setAdapter(adapter);

        selectedSeedType = Objects.requireNonNull(adapter.getItem(0)).toString();
        seedType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSeedType = parent.getItemAtPosition(position).toString();
                updateTextCAN();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}
