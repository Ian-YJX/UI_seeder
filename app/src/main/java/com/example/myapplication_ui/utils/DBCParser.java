package com.example.myapplication_ui.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class DBCParser {

    // 解析 DBC 文件的字符串，返回消息列表
    public static List<Message> parseDBCString(String dbcContent) {
        List<Message> messages = new ArrayList<>();
        Message currentMessage = null;
        Log.d("DBC Parsing", "Parsing begin!");

        // 逐行读取 DBC 字符串内容
        BufferedReader reader = new BufferedReader(new StringReader(dbcContent));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // 解析 CAN 消息定义（BO_）
                if (line.startsWith("BO_")) {
                    String[] parts = line.split(" ");
                    String id = parts[1];
                    String name = parts[2];
                    int dlc = Integer.parseInt(parts[3]);
                    //Log.d("Message", "id=" + id + " name=" + name + " dlc=" + dlc);

                    currentMessage = new Message(id, name, dlc);
                    messages.add(currentMessage);
                }
                // 解析信号定义（SG_）
                else if (line.startsWith("SG_") && currentMessage != null) {
                    // 解析信号的各部分
                    String[] parts = line.split(":")[1].trim().split(" ");
                    String[] namepart = line.split(":")[0].trim().split(" ");
                    String name = namepart[1];
                    Log.d("Signal Name", name);

                    // 解析信号的位置信息
                    String[] bitInfo = parts[0].split("\\|");
                    int startBit = Integer.parseInt(bitInfo[0]);
                    int length = Integer.parseInt(bitInfo[1].split("@")[0]);
                    boolean isSigned = bitInfo[1].contains("-");
                    Log.d("BitInfo", "startBit=" + startBit + " length=" + length + " isSigned=" + isSigned);

                    // 解析缩放因子和偏移量
                    String[] factorOffset = parts[1].replace("(", "").replace(")", "").split(",");
                    double factor = Double.parseDouble(factorOffset[0]);
                    double offset = Double.parseDouble(factorOffset[1]);
                    Log.d("Factor/Offset", "factor=" + factor + " offset=" + offset);

                    // 解析单位（如果没有单位，描述为引号中的内容）
                    String unit = parts[3].replace("\"", "");
                    String description = "";

                    if (unit.isEmpty()) {
                        // 如果没有单位，描述取引号里的内容
                        description = parts[4].replace("\"", "");
                    }

                    // 如果单位存在，描述清空
                    if (!unit.isEmpty()) {
                        description = "";
                    }

                    // 解析发送节点（最后一部分是发送节点）
                    String sender = parts[parts.length - 1].trim();
                    Log.d("Sender", sender);

                    // 添加信号到当前消息
                    currentMessage.signals.add(new Signal(name, startBit, length, isSigned, factor, offset, unit, description, sender));
                }
            }
            Log.d("DBC Parsing", "Parsing successful!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DBC Parsing", "Error during parsing", e);
        }
        return messages;
    }

    // Signal类表示每个信号的定义
    public static class Signal {
        String name;        // 信号名称
        int startBit;       // 起始位
        int length;         // 位长度
        boolean isSigned;   // 是否为有符号
        double factor;      // 缩放因子
        double offset;      // 偏移量
        String unit;        // 单位
        String description; // 描述
        String sender;      // 发送节点

        public Signal(String name, int startBit, int length, boolean isSigned, double factor, double offset, String unit, String description, String sender) {
            this.name = name;
            this.startBit = startBit;
            this.length = length;
            this.isSigned = isSigned;
            this.factor = factor;
            this.offset = offset;
            this.unit = unit;
            this.description = description;
            this.sender = sender;
        }
    }

    // Message类表示一条CAN消息
    public static class Message {
        public String id;                // CAN ID
        String name;           // 消息名称
        int dlc;               // 数据帧长度
        List<Signal> signals;  // 信号列表

        public Message(String id, String name, int dlc) {
            this.id = id;
            this.name = name;
            this.dlc = dlc;
            this.signals = new ArrayList<>();
        }

        public String getId() {
            return id;
        }

        public int getDlc() {
            return dlc;
        }

        public List<Signal> getSignals() {
            return signals;
        }
    }
}
