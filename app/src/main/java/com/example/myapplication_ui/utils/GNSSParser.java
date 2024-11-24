package com.example.myapplication_ui.utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class GNSSParser {

    // 波特率映射表
    private static final Map<String, String> BAUD_RATE_MAP = new HashMap<String, String>() {{
        put("8025", "9600");
        put("0096", "38400");
        put("00E1", "57600");
        put("00C2", "115200");
    }};

    // 卫星系统映射表
    private static final Map<String, String> SATELLITE_SYSTEM_MAP = new HashMap<String, String>() {{
        put("010000", "GPS only");
        put("000100", "BD only");
        put("000001", "GLONASS only");
        put("010100", "GPS + BD");
        put("010001", "GPS + GLONASS");
    }};

    // 启动模式映射表
    private static final Map<String, String> START_MODE_MAP = new HashMap<String, String>() {{
        put("0000", "Hot start");
        put("0100", "Warm start");
        put("FFB9", "Cold start");
    }};

    // 更新频率映射表
    private static final Map<String, String> FREQUENCY_MAP = new HashMap<String, String>() {{
        put("6400", "10Hz");
        put("C800", "5Hz");
        put("E803", "1Hz");
        put("D007", "0.5Hz");
        put("8813", "0.2Hz");
    }};

    /**
     * 解析 UBX 命令并返回可读的描述
     *
     * @param command 十六进制字符串表示的 UBX 命令
     * @return 命令解析后的描述
     */
    public static String parseCommand(String command) {
        // 预处理输入命令
        String cleanedCommand = preprocessCommand(command);

        // 校验头部是否为 UBX 协议
        if (!cleanedCommand.startsWith("B562")) {
            Log.d("cleanedCommand", cleanedCommand);
            return "无效命令：缺少 UBX 协议头部 (B5 62)";
        }

        // 校验命令长度是否合法
        if (!validateLength(cleanedCommand)) {
            Log.d("cleanedCommand", cleanedCommand);
            return "无效命令：长度字段不匹配";
        }

        // 校验校验和
        if (!validateChecksum(cleanedCommand)) {
            Log.d("cleanedCommand", cleanedCommand);
            return "无效命令：校验和验证失败";
        }

        // 解析命令类别
        String classID = cleanedCommand.substring(4, 8); // bytes[2] + bytes[3]
        switch (classID) {
            case "0600":
                Log.d("commandType", "BaudRate");
                return parseBaudRate(cleanedCommand);
            case "063E":
                Log.d("commandType", "SatelliteSystem");
                return parseSatelliteSystem(cleanedCommand);
            case "0604":
                Log.d("commandType", "StartMode");
                return parseStartMode(cleanedCommand);
            case "0608":
                Log.d("commandType", "Frequency");
                return parseFrequency(cleanedCommand);
            default:
                Log.d("commandType", "Unknown");
                return "未知命令类别：" + classID;
        }
    }

    // 解析波特率设置命令
    private static String parseBaudRate(String cleanedCommand) {
        if (cleanedCommand.length() < 40) return "无效波特率命令：长度不足";

        // 波特率字段为第 14~17 字节
        String baudRateKey = cleanedCommand.substring(28, 32); // bytes[14] + bytes[15]
        String baudRate = BAUD_RATE_MAP.get(baudRateKey);
        return baudRate != null ? "设置波特率：" + baudRate : "未知波特率：" + baudRateKey;
    }

    // 解析卫星系统设置命令
    private static String parseSatelliteSystem(String cleanedCommand) {
        if (cleanedCommand.length() < 26) return "无效卫星系统命令：长度不足";

        // 卫星系统字段为第 14,38,62 字节
        String systemKey1 = cleanedCommand.substring(28, 30); // bytes[14]
        String systemKey2 = cleanedCommand.substring(76, 78); // bytes[38]
        String systemKey3 = cleanedCommand.substring(124, 126); // bytes[62]
        String systemKey = systemKey1 + systemKey2 + systemKey3;
        String system = SATELLITE_SYSTEM_MAP.get(systemKey);
        return system != null ? "卫星系统：" + system : "未知卫星系统：" + systemKey;
    }

    // 解析启动模式命令
    private static String parseStartMode(String cleanedCommand) {
        if (cleanedCommand.length() < 10) return "无效启动模式命令：长度不足";

        // 启动模式字段为第 6 字节
        String startModeKey = cleanedCommand.substring(12, 16); // bytes[6],bytes[7]
        String startMode = START_MODE_MAP.get(startModeKey);
        return startMode != null ? "启动模式：" + startMode : "未知启动模式：" + startModeKey;
    }

    // 解析更新频率命令
    private static String parseFrequency(String cleanedCommand) {
        if (cleanedCommand.length() < 12) return "无效更新频率命令：长度不足";

        // 更新频率字段为第 4 字节
        String frequencyKey = cleanedCommand.substring(12, 16); // bytes[6],bytes[7]
        String frequency = FREQUENCY_MAP.get(frequencyKey);
        return frequency != null ? "更新频率：" + frequency : "未知更新频率：" + frequencyKey;
    }

    // 校验命令长度是否正确
    private static boolean validateLength(String cleanedCommand) {
        if (cleanedCommand.length() < 12) return false;

        // UBX 协议内容长度由 bytes[4] 和 bytes[5] 指定
        int payloadLength = Integer.parseInt(cleanedCommand.substring(8, 12), 16) / 256; // bytes[4] + bytes[5]
        Log.d("payloadlegth", Integer.toString(payloadLength));
        // 计算期望的完整命令长度：头部（2）+ 消息类别（1）+消息id（1）+长度字段（2）+ 负载长度 + 校验和（2）
        int expectedLength = 8 + payloadLength;
        Log.d("cleanedCommand.length", Integer.toString(cleanedCommand.length()));
        return cleanedCommand.length() == expectedLength * 2; // 转为字符长度
    }

    // 校验校验和是否正确
    private static boolean validateChecksum(String cleanedCommand) {
        int ckA = 0, ckB = 0;

        // 校验和仅对 payload 部分进行计算（从第 2 字节到倒数第 3 字节）
        for (int i = 4; i < cleanedCommand.length() - 4; i += 2) {
            int value = Integer.parseInt(cleanedCommand.substring(i, i + 2), 16);
            ckA = (ckA + value) & 0xFF;
            ckB = (ckB + ckA) & 0xFF;
        }

        int receivedCkA = Integer.parseInt(cleanedCommand.substring(cleanedCommand.length() - 4, cleanedCommand.length() - 2), 16);
        int receivedCkB = Integer.parseInt(cleanedCommand.substring(cleanedCommand.length() - 2), 16);
        return ckA == receivedCkA && ckB == receivedCkB;
    }

    // 预处理输入命令
    private static String preprocessCommand(String command) {
        // 清除所有空格和非十六进制字符
        String cleanedCommand = command.replaceAll("[^A-Fa-f0-9]", "");

        // 确保清理后的命令字符串长度为偶数（十六进制字节对）
        if (cleanedCommand.length() % 2 != 0) {
            throw new IllegalArgumentException("输入命令长度无效，应为偶数");
        }
        return cleanedCommand;
    }
}
