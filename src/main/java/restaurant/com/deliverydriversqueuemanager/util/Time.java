package restaurant.com.deliverydriversqueuemanager.util;

import java.time.LocalDateTime;

public class Time {
    public static String getCurrentTime() {
        String currentTime = "";
        LocalDateTime time = LocalDateTime.now();
        String hour = String.valueOf(time.getHour());
        if(hour.length() < 2) {
            hour = '0' + hour;
        }
        String minute = String.valueOf(time.getMinute());
        if (minute.length() < 2) {
            minute = '0' + minute;
        }
        String second = String.valueOf(time.getSecond());
        if (second.length() < 2) {
            second = '0' + second;
        }
        currentTime += hour + ":";
        currentTime += minute + ":";
        currentTime += second;
        return currentTime;
    }

    public static String getStringTime(LocalDateTime time) {
        String currentTime = "";
        String hour = String.valueOf(time.getHour());
        if(hour.length() < 2) {
            hour = '0' + hour;
        }
        String minute = String.valueOf(time.getMinute());
        if (minute.length() < 2) {
            minute = '0' + minute;
        }
        String second = String.valueOf(time.getSecond());
        if (second.length() < 2) {
            second = '0' + second;
        }
        currentTime += hour + ":";
        currentTime += minute + ":";
        currentTime += second;
        return currentTime;
    }
}
