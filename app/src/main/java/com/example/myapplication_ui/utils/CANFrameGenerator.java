package com.example.myapplication_ui.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CANFrameGenerator {

    // 根据信号定义和输入值生成数据帧
    public static String generateFrame(DBCParser.Message message, Map<String, String> values) {
        // 初始化一个64位的数据帧
        char[] frameBits = new char[message.dlc * 8];
        Arrays.fill(frameBits, '0'); // 默认填充为0
        //Log.d("CANFrameGenerator: frameBits ready", Arrays.toString(frameBits));
        for (DBCParser.Signal signal : message.signals) {
            // 获取用户输入的值
            String valueString = values.getOrDefault(signal.name, "0");
            double value = Integer.parseInt(valueString);
            // 应用缩放因子和偏移量
            int rawValue = (int) ((value - signal.offset) / signal.factor);
            // 转换为二进制字符串，按信号长度补齐
            String binaryValue = Integer.toBinaryString(rawValue);
            if(signal.name.equals("fertilizer_Cmd"))
            {
                Log.d("valueString",valueString);
                Log.d("binaryValue",binaryValue);
            }
            if (binaryValue.length() > signal.length) {
                binaryValue = binaryValue.substring(binaryValue.length() - signal.length); // 截断
            } else {
                binaryValue = String.format("%" + signal.length + "s", binaryValue).replace(' ', '0'); // 补齐
            }
            Log.d(signal.name,binaryValue);
            // 将二进制值写入到帧的正确位置
            for (int i = 0; i < signal.length; i++) {
                int bitIndex = signal.startBit + i;
                frameBits[bitIndex] = binaryValue.charAt(i);
            }
        }
        //Log.d("binary", new String(frameBits));
        return binaryStringToHexString(new String(frameBits));
    }

    private static String binaryStringToHexString(Object binaryInput) {
        String binaryString;

        // 确保输入是一个字符串，否则尝试将其转换为字符串
        if (binaryInput instanceof String) {
            binaryString = (String) binaryInput;
        } else if (binaryInput instanceof List) {
            // 如果是 List，将其拼接成字符串
            binaryString = ((List<?>) binaryInput)
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining());
        } else {
            Log.e("Error", "Invalid binaryInput type: " + binaryInput.getClass().getName());
            throw new IllegalArgumentException("Input must be a binary string or list of binary digits.");
        }

        // 确保 binaryString 只包含 0 和 1
        if (!binaryString.matches("[01]+")) {
            Log.e("Error", "binaryString contains invalid characters: " + binaryString);
            throw new IllegalArgumentException("Input must be a binary string.");
        }

        StringBuilder hexString = new StringBuilder();

        // 将 binaryString 分段处理，每 4 位对应一个十六进制字符
        for (int i = 0; i < binaryString.length(); i += 4) {
            // 获取当前的 4 位二进制字符串，若不足 4 位则补齐
            int segmentEndIndex = Math.min(i + 4, binaryString.length());
            String binarySegment = binaryString.substring(i, segmentEndIndex);

            // 打印调试信息
            //Log.d("binarySegment[" + i + "]", "Range: " + i + " to " + segmentEndIndex + ", Value: " + binarySegment);

            // 将二进制段解析为整数
            int decimalValue = Integer.parseInt(binarySegment, 2);

            // 将整数转换为对应的十六进制字符并追加到结果中
            hexString.append(Integer.toHexString(decimalValue).toUpperCase());
        }

        // 输出调试日志
        Log.d("HexString", hexString.toString());
        return hexString.toString();
    }

}
