package com.example.myapplication_ui.utils;

import android.annotation.SuppressLint;

import java.util.Random;

public class NMEASimulator {

    private static final Random RANDOM = new Random();

    // 生成模拟的 GPGGA NMEA 数据
    public static String generateGPGGA() {
        @SuppressLint("DefaultLocale") String time = String.format("%06d", RANDOM.nextInt(240000)); // 随机时间
        @SuppressLint("DefaultLocale") String latitude = String.format("%02d%05.2f", 48, RANDOM.nextDouble() * 60); // 48度随机纬度
        @SuppressLint("DefaultLocale") String longitude = String.format("%03d%05.2f", 11, RANDOM.nextDouble() * 60); // 11度随机经度
        String sentence = String.format("$GPGGA,%s,%s,N,%s,E,1,08,0.9,545.4,M,46.9,M,,",
                time, latitude, longitude);
        String checksum = calculateChecksum(sentence);
        return sentence + "*" + checksum;
    }

    // 生成模拟的 GPGLL NMEA 数据
    public static String generateGPGLL() {
        @SuppressLint("DefaultLocale") String latitude = String.format("%02d%05.2f", 49, RANDOM.nextDouble() * 60); // 49度随机纬度
        @SuppressLint("DefaultLocale") String longitude = String.format("%03d%05.2f", 123, RANDOM.nextDouble() * 60); // 123度随机经度
        @SuppressLint("DefaultLocale") String time = String.format("%02d%02d%02d", RANDOM.nextInt(24), RANDOM.nextInt(60), RANDOM.nextInt(60)); // 随机时间
        String sentence = String.format("$GPGLL,%s,N,%s,W,%s,A,*", latitude, longitude, time);
        String checksum = calculateChecksum(sentence);
        return sentence + checksum;
    }

    // 生成模拟的 GPGSA NMEA 数据
    public static String generateGPGSA() {
        String sentence = "$GPGSA,A,3,04,05,09,12,24,25,29,32,,,,,1.8,1.0,1.2*";
        String checksum = calculateChecksum(sentence);
        return sentence + checksum;
    }

    // 生成模拟的 GPGST NMEA 数据
    public static String generateGPGST() {
        @SuppressLint("DefaultLocale") String time = String.format("%06d", RANDOM.nextInt(240000)); // 随机时间
        String sentence = String.format("$GPGST,%s,1.0,0.5,0.7,45.0,0.9,0.8,1.2*", time);
        String checksum = calculateChecksum(sentence);
        return sentence + checksum;
    }

    // 生成模拟的 GPGSV NMEA 数据
    public static String generateGPGSV() {
        String sentence = "$GPGSV,2,1,08,01,40,083,41,02,17,274,43,03,29,193,42,04,25,117,38*";
        String checksum = calculateChecksum(sentence);
        return sentence + checksum;
    }

    // 生成模拟的 GPRMC NMEA 数据
    public static String generateGPRMC() {
        @SuppressLint("DefaultLocale") String time = String.format("%06d", RANDOM.nextInt(240000)); // 随机时间
        @SuppressLint("DefaultLocale") String latitude = String.format("%02d%05.2f", 49, RANDOM.nextDouble() * 60); // 49度随机纬度
        @SuppressLint("DefaultLocale") String longitude = String.format("%03d%05.2f", 123, RANDOM.nextDouble() * 60); // 123度随机经度
        @SuppressLint("DefaultLocale") String speed = String.format("%.2f", RANDOM.nextDouble() * 30); // 随机速度
        @SuppressLint("DefaultLocale") String course = String.format("%.2f", RANDOM.nextDouble() * 360); // 随机航向
        String sentence = String.format("$GPRMC,%s,A,%s,N,%s,E,%s,%s,230394,003.1,W*",
                time, latitude, longitude, speed, course);
        String checksum = calculateChecksum(sentence);
        return sentence + checksum;
    }

    // 生成模拟的 GPVTG NMEA 数据
    public static String generateGPVTG() {
        @SuppressLint("DefaultLocale") String trueTrack = String.format("%.1f", RANDOM.nextDouble() * 360); // 随机真航迹
        @SuppressLint("DefaultLocale") String magneticTrack = String.format("%.1f", RANDOM.nextDouble() * 360); // 随机磁航迹
        @SuppressLint("DefaultLocale") String speedKnots = String.format("%.2f", RANDOM.nextDouble() * 30); // 随机节
        @SuppressLint("DefaultLocale") String speedKmH = String.format("%.2f", RANDOM.nextDouble() * 60); // 随机公里每小时
        String sentence = String.format("$GPVTG,%s,T,%s,M,%s,N,%s,K*", trueTrack, magneticTrack, speedKnots, speedKmH);
        String checksum = calculateChecksum(sentence);
        return sentence + checksum;
    }

    // 生成模拟的 GPZDA NMEA 数据
    public static String generateGPZDA() {
        @SuppressLint("DefaultLocale") String time = String.format("%06d", RANDOM.nextInt(240000)); // 随机时间
        @SuppressLint("DefaultLocale") String day = String.format("%02d", RANDOM.nextInt(31)); // 随机日
        @SuppressLint("DefaultLocale") String month = String.format("%02d", RANDOM.nextInt(12)); // 随机月
        @SuppressLint("DefaultLocale") String year = String.format("%04d", 2000 + RANDOM.nextInt(25)); // 随机年
        @SuppressLint("DefaultLocale") String timezoneHour = String.format("%02d", RANDOM.nextInt(24)); // 随机时区小时
        @SuppressLint("DefaultLocale") String timezoneMinute = String.format("%02d", RANDOM.nextInt(60)); // 随机时区分钟
        String sentence = String.format("$GPZDA,%s,%s,%s,%s,%s,%s*", time, day, month, year, timezoneHour, timezoneMinute);
        String checksum = calculateChecksum(sentence);
        return sentence + checksum;
    }

    // 计算校验和
// 计算校验和
    private static String calculateChecksum(String data) {
        int checksum = 0;

        // Skip the leading '$' character and process the rest of the string.
        for (int i = 1; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ch == '*') {
                break;  // Stop at the '*' character (end of sentence)
            }
            checksum ^= ch;  // XOR each character with the checksum
        }

        // Return the checksum as a two-digit uppercase hexadecimal string.
        return String.format("%02X", checksum);
    }


    // 生成多种类型 NMEA 数据
    public static String generateAllNMEA() {
        return generateGPGGA() + "\n" +
                generateGPGLL() + "\n" +
                generateGPGSA() + "\n" +
                generateGPGST() + "\n" +
                generateGPGSV() + "\n" +
                generateGPRMC() + "\n" +
                generateGPVTG() + "\n" +
                generateGPZDA() + "\n";
    }
}
