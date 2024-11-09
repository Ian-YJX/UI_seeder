package com.example.myapplication_ui;

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

import com.example.myapplication_ui.Menu.Machine_watch.MainActivity_Machine_watch;

public class DataInputActivity extends AppCompatActivity {

    private ImageButton backtomenu;

    private Spinner spinnerData;
    private TextView parsedDataText;  // 用于显示解析的播种数据
    private ParsedData parsedData = new ParsedData(); // 用于保存解析的数据

    private Spinner spinnerData2;
    private TextView parsedDataText2;  // 用于显示解析的播种数据
    private ParsedData parsedData2 = new ParsedData(); // 用于保存解析的数据

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        // 获取第一个 Spinner 和 TextView
        spinnerData = findViewById(R.id.spinnerData);
        parsedDataText = findViewById(R.id.parsedDataText);

        // 获取第二个 Spinner 和 TextView
        spinnerData2 = findViewById(R.id.spinnerData2);
        parsedDataText2 = findViewById(R.id.parsedDataText2);

        // 设置第一个 Spinner 的数据选项
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.can_data_options, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerData.setAdapter(adapter1);

        // 设置第二个 Spinner 的数据选项
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.can_data_options2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerData2.setAdapter(adapter2);

        // 设置第一个 Spinner 的监听器
        spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDataFrame = spinnerData.getSelectedItem().toString().trim();

                // 输出选中的数据，检查是否正确
                Log.d("DataInputActivity", "Selected Data Frame 1: " + selectedDataFrame);

                // 解析数据帧
                ParsedData newData = parseCANDataFrame(selectedDataFrame);

                // 更新解析后的数据到 parsedData 中，只有非空数据才更新
                parsedData.updateIfNotNull(newData);

                // 格式化解析后的数据并显示
                String dataText = "解析后的播种数据：\n" +
                        "名称：" + parsedData.name + "  " +
                        "状态: " + parsedData.status + "  " +
                        "播种量: " + parsedData.sowingAmount + "  " +
                        "单播率: " + parsedData.singleSowingRate + "\n" +
                        "重播率: " + parsedData.reSowingRate + "  " +
                        "漏播率: " + parsedData.missSowingRate + "  " +
                        "变差率: " + parsedData.deviationRate + "  " +
                        "电流: " + parsedData.current + "  " +
                        "光强: " + parsedData.lightIntensity;

                // 设置第一个 TextView 内容
                parsedDataText.setText(dataText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 可选：处理未选择任何内容的情况
            }
        });

        // 设置第二个 Spinner 的监听器
        spinnerData2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDataFrame2 = spinnerData2.getSelectedItem().toString().trim();

                // 输出选中的数据，检查是否正确
                Log.d("DataInputActivity", "Selected Data Frame 2: " + selectedDataFrame2);

                // 解析数据帧
                ParsedData newData2 = parseCANDataFrame(selectedDataFrame2);

                // 更新解析后的数据到 parsedData2 中，只有非空数据才更新
                parsedData2.updateIfNotNull(newData2);

                // 格式化解析后的数据并显示
                String dataText2 = "解析后的电机数据：\n" +
                        "名称：" + parsedData2.name + "  " +
                        "状态: " + parsedData2.status + "  " +
                        "转速: " + parsedData2.rotationRate + "  " +
                        "电压: " + parsedData2.voltage + "\n" +
                        "电流: " + parsedData2.current + " "+
                        "温度: " + parsedData2.temperature + "  " +
                        "圈数: " + parsedData2.circleNum + "  " +
                        "电机状态: " + parsedData2.motorStatus + "  ";

                // 设置第二个 TextView 内容
                parsedDataText2.setText(dataText2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 可选：处理未选择任何内容的情况
            }
        });


        // 返回菜单按钮点击事件
        backtomenu = findViewById(R.id.backtomenu);
        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 Intent 并传递数据
                Intent intent2 = new Intent(DataInputActivity.this, MainActivity_Machine_watch.class);
                intent2.putExtra("name", parsedData2.name);
                intent2.putExtra("status", parsedData2.status);
                intent2.putExtra("rotationRate", parsedData2.rotationRate);
                intent2.putExtra("voltage", parsedData2.voltage);
                intent2.putExtra("temperature", parsedData2.temperature);
                intent2.putExtra("circleNum", parsedData2.circleNum);
                intent2.putExtra("motorStatus", parsedData2.motorStatus);
                intent2.putExtra("current", parsedData2.current);

                Intent intent = new Intent(DataInputActivity.this, MainActivity_seed_watch.class);
                intent.putExtra("name", parsedData.name);
                intent.putExtra("status", parsedData.status);
                intent.putExtra("sowingAmount", parsedData.sowingAmount);
                intent.putExtra("singleSowingRate", parsedData.singleSowingRate);
                intent.putExtra("reSowingRate", parsedData.reSowingRate);
                intent.putExtra("missSowingRate", parsedData.missSowingRate);
                intent.putExtra("deviationRate", parsedData.deviationRate);
                intent.putExtra("current", parsedData.current);
                intent.putExtra("lightIntensity", parsedData.lightIntensity);

                startActivity(intent);
            }
        });
    }

    /**
     * 解析CAN数据帧
     */
    private ParsedData parseCANDataFrame(String dataFrame) {
        ParsedData data = new ParsedData();

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
                    data.current = String.format("%.1f mA", Integer.parseInt(binaryData.substring(34, 44), 2) * 0.2);
                    data.reSowingRate = String.format("%.1f %%", Integer.parseInt(binaryData.substring(44, 54), 2) * 0.1);
                    data.missSowingRate = String.format("%.1f %%", Integer.parseInt(binaryData.substring(54, 64), 2) * 0.1);

                    Log.d("DataInputActivity", "Parsed sowingAmount in 0x13: "+"\n" + data.sowingAmount +"\n" +"current=" +data.current +"\n" +"missSowingRate=" +data.missSowingRate +"\n");
                    break;

                case "00010100": // 功能码 0x14
                    data.deviationRate = String.format("%.1f %%", Integer.parseInt(binaryData.substring(34, 44), 2) * 0.1);
                    break;

                case "10110011": // 功能码 0xB3
                    // 检查 sowingAmount 是否已经存在
                    Log.d("DataInputActivity", "Checking sowingAmount before 0xB3 processing: " + parsedData.sowingAmount);
                    if (parsedData.sowingAmount != null && !parsedData.sowingAmount.isEmpty()) {
                        // 解析单播数量值
                        int singleSowingValue = Integer.parseInt(binaryData.substring(8, 24), 2);

                        // 转换 sowingAmount 值为整数
                        int sowingAmountInt = Integer.parseInt(parsedData.sowingAmount);

                        // 计算单播率，并格式化为百分比字符串
                        double singleSowingRate = sowingAmountInt != 0 ? (double) singleSowingValue / sowingAmountInt : 0;
                        data.singleSowingRate = String.format("%.1f%%", singleSowingRate * 100);

                        Log.d("DataInputActivity", "Parsed singleSowingRate in 0xB3: " + data.singleSowingRate);
                    } else {
                        Log.w("DataInputActivity", "sowingAmount not set in previous frames. Cannot calculate singleSowingRate.");
                    }
                    break;

                case "00010001":
                    String statusCode=binaryData.substring(8, 10);
                    switch (statusCode){
                        case "00":
                            data.status="停止工作";
                            break;
                        case "01":
                            data.status="开始工作";
                            break;
                        case "10":
                            data.status="暂停工作";
                            break;
                        default:
                            Log.w("DataInputActivity", "状态码未定义: " + statusCode);
                            break;
                    }
                    int motorStatusNum=Integer.parseInt(binaryData.substring(10, 14), 2);
                    switch (motorStatusNum){
                        case 0:
                            data.motorStatus="正常";
                            break;
                        case 1:
                            data.motorStatus="尚未学习";
                            break;
                        case 2:
                            data.motorStatus="堵转停止";
                            break;
                        case 3:
                            data.motorStatus="霍尔错误";
                            break;
                        case 4:
                            data.motorStatus="无法达到目标速度";
                            break;
                        case 6:
                            data.motorStatus="过流关断";
                            break;
                        case 7:
                            data.motorStatus="过热关断";
                            break;
                        case 8:
                            data.motorStatus="过压关断";
                            break;
                        case 9:
                            data.motorStatus="欠压关断";
                            break;
                        case 10:
                            data.motorStatus="堵转停止";
                            break;
                        case 11:
                            data.motorStatus="驱动通讯异常";
                            break;
                        default:
                            Log.w("DataInputActivity", "未定义的电机状态码: " + motorStatusNum);
                            break;
                    }
                    data.rotationRate = String.valueOf(Integer.parseInt(binaryData.substring(14, 26), 2));
                    data.current = String.valueOf(Integer.parseInt(binaryData.substring(26, 38), 2)*0.01);
                    data.circleNum=String.valueOf(Integer.parseInt(binaryData.substring(38, 64), 2)*0.1);
                    break;
                case "00010010":
                    data.voltage = String.valueOf(Integer.parseInt(binaryData.substring(8, 18), 2)*0.1);
                    data.current = String.valueOf(Integer.parseInt(binaryData.substring(18, 29), 2)*0.1);


                default:
                    Log.w("DataInputActivity", "Unknown code: " + code);
                    break;
            }


        } catch (NumberFormatException e) {
            Log.e("DataInputActivity", "Error parsing data frame: " + e.getMessage());
        }

        return data;
    }

    // 辅助方法：解析状态码
    private String parseStatus(String statusBits) {
        switch (statusBits) {
            case "00":
                return "停止工作";
            case "01":
                return "开始工作";
            case "10":
                return "暂停工作";
            default:
                return "未知状态";
        }
    }

    // 辅助方法：解析传感器状态码
    private String parseSensorStatus(String sensorBits) {
        switch (sensorBits) {
            case "000":
                return "正常";
            case "001":
                return "堵管";
            case "010":
                return "无种";
            default:
                return "未知传感器状态";
        }
    }

    // 定义一个内部类，用于保存解析后的数据
    private static class ParsedData {
        String name;
        String status;
        String sowingAmount;
        String singleSowingRate;
        String reSowingRate;
        String missSowingRate;
        String deviationRate;
        String current;
        String lightIntensity;
        String rotationRate;
        String voltage;
        String temperature;
        String circleNum;
        String motorStatus;
        // 仅当新值不为空时更新字段
        public void updateIfNotNull(ParsedData newData) {
            if (newData.name != null) this.name = newData.name;
            if (newData.status != null) this.status = newData.status;
            if (newData.sowingAmount != null) this.sowingAmount = newData.sowingAmount;
            if (newData.singleSowingRate != null) this.singleSowingRate = newData.singleSowingRate;
            if (newData.reSowingRate != null) this.reSowingRate = newData.reSowingRate;
            if (newData.missSowingRate != null) this.missSowingRate = newData.missSowingRate;
            if (newData.deviationRate != null) this.deviationRate = newData.deviationRate;
            if (newData.current != null) this.current = newData.current;
            if (newData.lightIntensity != null) this.lightIntensity = newData.lightIntensity;
            if (newData.rotationRate != null) this.rotationRate = newData.rotationRate;
            if (newData.voltage != null) this.voltage = newData.voltage;
            if (newData.temperature != null) this.temperature = newData.temperature;
            if (newData.circleNum != null) this.circleNum = newData.circleNum;
            if (newData.motorStatus != null) this.motorStatus = newData.motorStatus;

            Log.d("ParsedData", "Updated parsed data: " + this.toString());  // 添加日志
        }


        @Override
        public String toString() {
            return "ParsedData{" +
                    "name='" + name + '\'' +
                    ", status='" + status + '\'' +
                    ", sowingAmount='" + sowingAmount + '\'' +
                    ", singleSowingRate='" + singleSowingRate + '\'' +
                    ", reSowingRate='" + reSowingRate + '\'' +
                    ", missSowingRate='" + missSowingRate + '\'' +
                    ", deviationRate='" + deviationRate + '\'' +
                    ", current='" + current + '\'' +
                    ", lightIntensity='" + lightIntensity + '\'' +
                    '}';
        }
    }
}
