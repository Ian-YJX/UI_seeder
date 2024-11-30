package com.example.myapplication_ui.utils;

import android.util.Log;

import java.util.Map;

public class NMEASpeedParser {

    // 解析 NMEA 模拟数据
    public static String parseNMEASimulatedData() {
        String parsedData="";
        // 获取模拟数据
        String simulatedData = NMEASimulator.generateAllNMEA();

        // 逐行处理模拟数据
        String[] lines = simulatedData.split("\n");
        for (String line : lines) {
            if (line.startsWith("$GPRMC")) { // 解析RMC
                try {
                    parsedData=parseNMEASentence((line));
                    Log.d("parsedRMCspeed",parsedData);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
        }

        if (parsedData != null && !parsedData.isEmpty()) {
            Log.d("parsed speed", parsedData);
        } else {
            Log.d("parsed speed", "GNSS speed is null or empty");
        }
        return parsedData;
    }

    // 解析单个 NMEA 语句
    public static String parseNMEASentence(String sentence) {
        Map<String, String> result = new java.util.HashMap<>();

        // 检查语句有效性
        if (!sentence.startsWith("$") || !sentence.contains("*")) {
            throw new IllegalArgumentException("Invalid NMEA sentence: " + sentence);
        }

        // 分离数据部分和校验部分
        String[] parts = sentence.split("\\*");
        String dataPart = parts[0].substring(1); // 去掉开头的 $
        String checksum = parts[1];

        // 校验和验证
        if (!validateChecksum(dataPart, checksum)) {
            throw new IllegalArgumentException("Checksum validation failed for: " + sentence);
        }

        // 分割字段
        String[] fields = dataPart.split(",");
        String messageType = fields[0];
        result.put("消息类型", messageType); // 将消息类型翻译成中文

        if (messageType.equals("GPRMC")) {
            parseRMC(fields, result);
        }

        return result.get("地面速度");
    }

    // 校验和验证
    private static boolean validateChecksum(String data, String checksum) {
        int calculatedChecksum = 0;
        for (char ch : data.toCharArray()) {
            calculatedChecksum ^= ch; // 使用 XOR 计算每个字符的校验和
        }

        // 解析校验和部分并与计算值进行比较
        try {
            int providedChecksum = Integer.parseInt(checksum, 16); // 校验和部分是十六进制
            //System.out.println("Calculated checksum: " + Integer.toHexString(calculatedChecksum).toUpperCase());
            //System.out.println("Provided checksum: " + checksum);
            return calculatedChecksum == providedChecksum; // 比较计算值与提供的校验和
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid checksum format: " + checksum);
        }
    }

    // 解析 RMC 语句
    private static void parseRMC(String[] fields, Map<String, String> result) {
        result.put("时间", fields[1]);
        result.put("状态", fields[2]);
        result.put("纬度", fields[3]);
        result.put("纬度方向", fields[4]);
        result.put("经度", fields[5]);
        result.put("经度方向", fields[6]);
        result.put("地面速度", fields[7]);
        result.put("航向角", fields[8]);
        result.put("日期", fields[9]);
        result.put("磁偏角", fields[10]);
        result.put("磁偏角方向", fields.length > 11 ? fields[11] : "");
        Log.d("parsed","RMC");

    }
}
