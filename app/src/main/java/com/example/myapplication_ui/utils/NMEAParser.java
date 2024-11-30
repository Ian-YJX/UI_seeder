package com.example.myapplication_ui.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NMEAParser {

    // 解析 NMEA 模拟数据
    public static List<Map<String, String>> parseNMEASimulatedData() {
        List<Map<String, String>> parsedData = new ArrayList<>();
        // 获取模拟数据
        String simulatedData = NMEASimulator.generateAllNMEA();

        // 逐行处理模拟数据
        String[] lines = simulatedData.split("\n");
        for (String line : lines) {
            if (line.startsWith("$")) { // 忽略非 NMEA 语句
                try {
                    parsedData.add(parseNMEASentence(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
        }

        return parsedData;
    }

    // 解析单个 NMEA 语句
    public static Map<String, String> parseNMEASentence(String sentence) {
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

        switch (messageType) {
            case "GPGGA":
                parseGGA(fields, result);
                break;
            case "GPGLL":
                parseGLL(fields, result);
                break;
            case "GPGSA":
                parseGSA(fields, result);
                break;
            case "GPGST":
                parseGST(fields, result);
                break;
            case "GPGSV":
                parseGSV(fields, result);
                break;
            case "GPRMC":
                parseRMC(fields, result);
                break;
            case "GPVTG":
                parseVTG(fields, result);
                break;
            case "GPZDA":
                parseZDA(fields, result);
                break;
            default:
                result.put("原始数据", dataPart); // 未知类型保留原始数据
        }

        return result;
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
            System.out.println("Calculated checksum: " + Integer.toHexString(calculatedChecksum).toUpperCase());
            System.out.println("Provided checksum: " + checksum);
            return calculatedChecksum == providedChecksum; // 比较计算值与提供的校验和
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid checksum format: " + checksum);
        }
    }

    // 解析 GGA 语句
    private static void parseGGA(String[] fields, Map<String, String> result) {
        result.put("时间", fields[1]);
        result.put("纬度", fields[2]);
        result.put("纬度方向", fields[3]);
        result.put("经度", fields[4]);
        result.put("经度方向", fields[5]);
        result.put("定位质量", fields[6]);
        result.put("卫星数", fields[7]);
        result.put("水平精度因子", fields[8]);
        result.put("海拔", fields[9]);
        result.put("海拔单位", fields[10]);
        result.put("大地水准面离地面高度", fields[11]);
        result.put("大地水准面离地面高度单位", fields[12]);
        result.put("DGPS 年龄", fields.length > 13 ? fields[13] : "");
        result.put("DGPS 站点ID", fields.length > 14 ? fields[14] : "");
        Log.d("parsed","GGA");
    }

    // 解析 GLL 语句
    private static void parseGLL(String[] fields, Map<String, String> result) {
        result.put("纬度", fields[1]);
        result.put("纬度方向", fields[2]);
        result.put("经度", fields[3]);
        result.put("经度方向", fields[4]);
        result.put("时间", fields[5]);
        result.put("状态", fields[6]);
        Log.d("parsed","GLL");
    }

    // 解析 GSA 语句
    private static void parseGSA(String[] fields, Map<String, String> result) {
        result.put("模式", fields[1]);
        result.put("定位类型", fields[2]);
        result.put("使用卫星", fields.length > 3 ? String.join(",", Arrays.copyOfRange(fields, 3, 15)) : "");
        result.put("位置精度因子", fields.length > 15 ? fields[15] : "");
        result.put("水平精度因子", fields.length > 16 ? fields[16] : "");
        result.put("垂直精度因子", fields.length > 17 ? fields[17] : "");
        Log.d("parsed","GSA");
    }

    // 解析 GST 语句
    private static void parseGST(String[] fields, Map<String, String> result) {
        result.put("时间", fields[1]);
        result.put("均方根值", fields[2]);
        result.put("半长轴误差", fields[3]);
        result.put("半短轴误差", fields[4]);
        result.put("方向", fields[5]);
        result.put("纬度误差", fields[6]);
        result.put("经度误差", fields[7]);
        result.put("海拔误差", fields[8]);
        Log.d("parsed","GST");
    }

    // 解析 GSV 语句
    private static void parseGSV(String[] fields, Map<String, String> result) {
        result.put("消息总数", fields[1]);
        result.put("消息编号", fields[2]);
        result.put("可见卫星数", fields[3]);
        if (fields.length > 4) {
            List<String> satelliteInfo = new ArrayList<>();
            satelliteInfo.addAll(Arrays.asList(fields).subList(4, fields.length));
            result.put("卫星信息", String.join(",", satelliteInfo));
            Log.d("parsed","GSV");
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

    // 解析 VTG 语句
    private static void parseVTG(String[] fields, Map<String, String> result) {
        result.put("真航迹", fields[1]);
        result.put("真航迹指示符", fields[2]);
        result.put("磁航迹", fields[3]);
        result.put("磁航迹指示符", fields[4]);
        result.put("速度(节)", fields[5]);
        result.put("节单位", fields[6]);
        result.put("速度(km/h)", fields[7]);
        result.put("公里每小时单位", fields[8]);
        Log.d("parsed","VTG");
    }

    // 解析 ZDA 语句
    private static void parseZDA(String[] fields, Map<String, String> result) {
        result.put("时间", fields[1]);
        result.put("日", fields[2]);
        result.put("月", fields[3]);
        result.put("年", fields[4]);
        result.put("本地时区小时", fields[5]);
        result.put("本地时区分钟", fields[6]);
        Log.d("parsed","ZDA");
    }
}
