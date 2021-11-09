package restaurant.com.deliverydriversqueuemanager.util;

import java.time.LocalDateTime;

public class TimeFormatter {
    public static String formatToHHMMSS(LocalDateTime time) {
        return time.toLocalTime().toString().substring(0, 8);
    }
}
