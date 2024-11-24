package com.example.myapplication_ui.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_ui.MainActivity_page3_machine_watch;
import com.example.myapplication_ui.MainActivity_page4_seed_watch;
import com.example.myapplication_ui.R;

public class CANReceiving extends AppCompatActivity {

    private final ParsedSowing parsedData_sowing = new ParsedSowing(); // 用于保存解析的数据
    private final ParsedMotor parsedData_motor = new ParsedMotor(); // 用于保存解析的数据
    private Spinner sowingSpinner;
    private TextView parsedSowingCAN;  // 用于显示解析的播种数据
    private Spinner motorSpinner;
    private TextView parsedMotorCAN;  // 用于显示解析的电机数据

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_receiving);

        // 获取第一个 Spinner 和 TextView
        sowingSpinner = findViewById(R.id.spinnerData1);
        parsedSowingCAN = findViewById(R.id.parsedDataText1);

        // 获取第二个 Spinner 和 TextView
        motorSpinner = findViewById(R.id.spinnerData2);
        parsedMotorCAN = findViewById(R.id.parsedDataText2);

        // 设置第一个 Spinner 的数据选项
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.can_data_options, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sowingSpinner.setAdapter(adapter1);

        // 设置第二个 Spinner 的数据选项
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.can_data_options2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motorSpinner.setAdapter(adapter2);

        // 设置第一个 Spinner 的监听器
        sowingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDataFrame = sowingSpinner.getSelectedItem().toString().trim();

                // 输出选中的数据，检查是否正确
                Log.d("DataInputActivity", "Selected Data Frame 1: " + selectedDataFrame);

                // 解析数据帧
                ParsedSowing newData = parseSowingFrame(selectedDataFrame);

                // 更新解析后的数据到 parsedData 中，只有非空数据才更新
                parsedData_sowing.updateIfNotNull(newData);

                // 格式化解析后的数据并显示
                String dataText = "解析后的播种数据：\n" +
                        "名称：" + parsedData_sowing.name + "  " +
                        "状态: " + parsedData_sowing.status + "  " +
                        "播种量: " + parsedData_sowing.sowingAmount + "  " +
                        "单播率: " + parsedData_sowing.singleSowingRate + "\n" +
                        "重播率: " + parsedData_sowing.reSowingRate + "  " +
                        "漏播率: " + parsedData_sowing.missSowingRate + "  " +
                        "变差率: " + parsedData_sowing.deviationRate + "  " +
                        "电流: " + parsedData_sowing.seed_current + "  " +
                        "光强: " + parsedData_sowing.lightIntensity;

                // 设置第一个 TextView 内容
                parsedSowingCAN.setText(dataText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 可选：处理未选择任何内容的情况
            }
        });

        // 设置第二个 Spinner 的监听器
        motorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDataFrame2 = motorSpinner.getSelectedItem().toString().trim();

                // 输出选中的数据，检查是否正确
                Log.d("DataInputActivity", "Selected Data Frame 2: " + selectedDataFrame2);

                // 解析数据帧
                ParsedMotor newData2 = parseMotorFrame(selectedDataFrame2);

                // 更新解析后的数据到 parsedData_motor 中，只有非空数据才更新
                parsedData_motor.updateIfNotNull(newData2);

                // 格式化解析后的数据并显示
                String dataText2 = "解析后的电机数据：\n" +
                        "名称：" + parsedData_motor.name + "  " +
                        "状态: " + parsedData_motor.status + "  " +
                        "转速: " + parsedData_motor.rotationRate + "  " +
                        "电压: " + parsedData_motor.voltage + "\n" +
                        "电流: " + parsedData_motor.motor_current + " " +
                        "温度: " + parsedData_motor.temperature + "  " +
                        "圈数: " + parsedData_motor.circleNum + "  " +
                        "电机状态: " + parsedData_motor.motorStatus + "  ";

                // 设置第二个 TextView 内容
                parsedMotorCAN.setText(dataText2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 可选：处理未选择任何内容的情况
            }
        });


        // 返回菜单按钮点击事件
        ImageButton backtomenu = findViewById(R.id.backtomenu);
        backtomenu.setOnClickListener(v -> {
            // 创建第二个 Intent，用于传递播种数据并跳转到 MainActivity_seed_watch
            Intent intent = new Intent(CANReceiving.this, MainActivity_page4_seed_watch.class);
            intent.putExtra("name", parsedData_sowing.name);
            intent.putExtra("status", parsedData_sowing.status);
            intent.putExtra("sowingAmount", parsedData_sowing.sowingAmount);
            intent.putExtra("singleSowingRate", parsedData_sowing.singleSowingRate);
            intent.putExtra("reSowingRate", parsedData_sowing.reSowingRate);
            intent.putExtra("missSowingRate", parsedData_sowing.missSowingRate);
            intent.putExtra("deviationRate", parsedData_sowing.deviationRate);
            intent.putExtra("current", parsedData_sowing.seed_current);
            intent.putExtra("lightIntensity", parsedData_sowing.lightIntensity);
            // 启动 MainActivity_seed_watch（播种监控界面）
            startActivity(intent);
        });
        ImageButton backtomenu2 = findViewById(R.id.backtomenu2);
        backtomenu2.setOnClickListener(v -> {
            // 创建第一个 Intent，用于传递电机数据并跳转到 MainActivity_Machine_watch
            Intent intent2 = new Intent(CANReceiving.this, MainActivity_page3_machine_watch.class);
            intent2.putExtra("name", parsedData_motor.name);
            intent2.putExtra("status", parsedData_motor.status);
            intent2.putExtra("rotationRate", parsedData_motor.rotationRate);
            intent2.putExtra("voltage", parsedData_motor.voltage);
            intent2.putExtra("temperature", parsedData_motor.temperature);
            intent2.putExtra("circleNum", parsedData_motor.circleNum);
            intent2.putExtra("motorStatus", parsedData_motor.motorStatus);
            intent2.putExtra("current", parsedData_motor.motor_current);
            // 启动 MainActivity_Machine_watch（电机监控界面）
            startActivity(intent2);
        });
    }

    /**
     * 解析CAN数据帧
     */
    @SuppressLint("DefaultLocale")
    private ParsedSowing parseSowingFrame(String dataFrame) {
        ParsedSowing data = new ParsedSowing();

        // 去除 "0x" 前缀，获取纯十六进制字符串
        String hexData = dataFrame.substring(2);

        // 将十六进制转换为二进制字符串
        StringBuilder binaryData = new StringBuilder();
        for (char hexChar : hexData.toCharArray()) {
            binaryData.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt(String.valueOf(hexChar), 16)))
                    .replace(' ', '0'));
        }

        // 检查二进制字符串长度是否符合预期
        if (binaryData.length() != 64) {
            Log.e("DataInputActivity", "Data frame length error: " + binaryData.length());
            return data;
        }

        try {
            // 提取功能码
            String code = binaryData.substring(0, 8);
            data.name = "第一列";

            switch (code) {
                case "00010011": // 功能码 0x13
                    // 解析 sowingAmount, current, reSowingRate, missSowingRate
                    data.sowingAmount = String.valueOf(Integer.parseInt(binaryData.substring(10, 34), 2));
                    data.seed_current = String.format("%.2f", Integer.parseInt(binaryData.substring(34, 44), 2) * 0.2);
                    data.reSowingRate = String.format("%.2f", Integer.parseInt(binaryData.substring(44, 54), 2) * 0.1);
                    data.missSowingRate = String.format("%.2f", Integer.parseInt(binaryData.substring(54, 64), 2) * 0.1);

                    Log.d("DataInputActivity", "Parsed sowingAmount in 0x13: " + "\n" + data.sowingAmount + "\n" + "current=" + data.seed_current + "\n" + "missSowingRate=" + data.missSowingRate + "\n");
                    break;

                case "00010100": // 功能码 0x14
                    data.deviationRate = String.format("%.2f", Integer.parseInt(binaryData.substring(34, 44), 2) * 0.1);
                    break;

                case "10110011": // 功能码 0xB3
                    // 检查 sowingAmount 是否已经存在
                    Log.d("DataInputActivity", "Checking sowingAmount before 0xB3 processing: " + parsedData_sowing.sowingAmount);
                    if (parsedData_sowing.sowingAmount != null && !parsedData_sowing.sowingAmount.isEmpty()) {
                        // 解析单播数量值
                        int singleSowingValue = Integer.parseInt(binaryData.substring(8, 24), 2);

                        // 转换 sowingAmount 值为整数
                        int sowingAmountInt = Integer.parseInt(parsedData_sowing.sowingAmount);

                        // 计算单播率，并格式化为百分比字符串
                        double singleSowingRate = sowingAmountInt != 0 ? (double) singleSowingValue / sowingAmountInt : 0;
                        data.singleSowingRate = String.format("%.2f", singleSowingRate * 100);

                        Log.d("DataInputActivity", "Parsed singleSowingRate in 0xB3: " + data.singleSowingRate);
                    } else {
                        Log.w("DataInputActivity", "sowingAmount not set in previous frames. Cannot calculate singleSowingRate.");
                    }
                    break;

                default:
                    Log.w("DataInputActivity", "Unknown code: " + code);
                    break;
            }


        } catch (NumberFormatException e) {
            Log.e("DataInputActivity", "Error parsing data frame: " + e.getMessage());
        }

        return data;
    }

    private ParsedMotor parseMotorFrame(String dataFrame) {
        ParsedMotor data = new ParsedMotor();

        // 去除 "0x" 前缀，获取纯十六进制字符串
        String hexData = dataFrame.substring(2);

        // 将十六进制转换为二进制字符串
        StringBuilder binaryData = new StringBuilder();
        for (char hexChar : hexData.toCharArray()) {
            binaryData.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt(String.valueOf(hexChar), 16)))
                    .replace(' ', '0'));
        }

        // 检查二进制字符串长度是否符合预期
        if (binaryData.length() != 64) {
            Log.e("DataInputActivity", "Data frame length error: " + binaryData.length());
            return data;
        }

        try {
            // 提取功能码
            String code = binaryData.substring(0, 8);
            data.name = "第一列";

            switch (code) {
                case "00010001": //功能码0x11
                    Log.d("code", String.valueOf(binaryData));
                    String statusCode = binaryData.substring(8, 10);
                    switch (statusCode) {
                        case "00":
                            data.status = "停止工作";
                            break;
                        case "01":
                            data.status = "开始工作";
                            break;
                        case "10":
                            data.status = "暂停工作";
                            break;
                        default:
                            Log.w("DataInputActivity", "状态码未定义: " + statusCode);
                            break;
                    }
                    int motorStatusNum = Integer.parseInt(binaryData.substring(10, 14), 2);
                    switch (motorStatusNum) {
                        case 0:
                            data.motorStatus = "正常";
                            break;
                        case 1:
                            data.motorStatus = "尚未学习";
                            break;
                        case 2:
                        case 10:
                            data.motorStatus = "堵转停止";
                            break;
                        case 3:
                            data.motorStatus = "霍尔错误";
                            break;
                        case 4:
                            data.motorStatus = "无法达到目标速度";
                            break;
                        case 6:
                            data.motorStatus = "过流关断";
                            break;
                        case 7:
                            data.motorStatus = "过热关断";
                            break;
                        case 8:
                            data.motorStatus = "过压关断";
                            break;
                        case 9:
                            data.motorStatus = "欠压关断";
                            break;
                        case 11:
                            data.motorStatus = "驱动通讯异常";
                            break;
                        default:
                            Log.w("DataInputActivity", "未定义的电机状态码: " + motorStatusNum);
                            break;
                    }
                    Log.d("rotation_rate bits:", binaryData.substring(14, 26));
                    data.rotationRate = String.valueOf(Integer.parseInt(binaryData.substring(14, 26), 2));
                    Log.d("motor_current bits:", binaryData.substring(26, 38));
                    data.motor_current = String.format("%.2f ", Integer.parseInt(binaryData.substring(26, 38), 2) * 0.01);
                    Log.d("circleNum bits:", binaryData.substring(38, 64));
                    data.circleNum = String.format("%.1f", Integer.parseInt(binaryData.substring(38, 64), 2) * 0.1);
                    break;

                case "00010010"://功能码0x12
                    Log.d("voltage bits:", binaryData.substring(8, 18));
                    data.voltage = String.valueOf(Integer.parseInt(binaryData.substring(8, 18), 2) * 0.1);
                    Log.d("temperature bits:", binaryData.substring(18, 29));
                    data.temperature = String.valueOf(Integer.parseInt(binaryData.substring(18, 29), 2) * 0.1);


                default:
                    Log.w("DataInputActivity", "Unknown code: " + code);
                    break;
            }


        } catch (NumberFormatException e) {
            Log.e("DataInputActivity", "Error parsing data frame: " + e.getMessage());
        }

        return data;
    }

    private static class ParsedSowing {
        String name;
        String status;
        String sowingAmount;
        String singleSowingRate;
        String reSowingRate;
        String missSowingRate;
        String deviationRate;
        String seed_current;
        String lightIntensity;

        // 仅当新值不为空时更新字段
        public void updateIfNotNull(ParsedSowing newData) {
            if (newData.name != null) this.name = newData.name;
            if (newData.status != null) this.status = newData.status;
            if (newData.sowingAmount != null) this.sowingAmount = newData.sowingAmount;
            if (newData.singleSowingRate != null) this.singleSowingRate = newData.singleSowingRate;
            if (newData.reSowingRate != null) this.reSowingRate = newData.reSowingRate;
            if (newData.missSowingRate != null) this.missSowingRate = newData.missSowingRate;
            if (newData.deviationRate != null) this.deviationRate = newData.deviationRate;
            if (newData.seed_current != null) this.seed_current = newData.seed_current;
            if (newData.lightIntensity != null) this.lightIntensity = newData.lightIntensity;
        }
    }

    // 定义一个内部类，用于保存解析后的数据
    private static class ParsedMotor {
        String name;
        String status;
        String rotationRate;
        String voltage;
        String temperature;
        String circleNum;
        String motorStatus;
        String motor_current;

        // 仅当新值不为空时更新字段
        public void updateIfNotNull(ParsedMotor newData) {
            if (newData.name != null) this.name = newData.name;
            if (newData.status != null) this.status = newData.status;
            if (newData.rotationRate != null) this.rotationRate = newData.rotationRate;
            if (newData.voltage != null) this.voltage = newData.voltage;
            if (newData.temperature != null) this.temperature = newData.temperature;
            if (newData.circleNum != null) this.circleNum = newData.circleNum;
            if (newData.motorStatus != null) this.motorStatus = newData.motorStatus;
            if (newData.motor_current != null) this.motor_current = newData.motor_current;
        }
    }
}