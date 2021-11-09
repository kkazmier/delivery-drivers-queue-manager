package restaurant.com.deliverydriversqueuemanager.util;

import restaurant.com.deliverydriversqueuemanager.model.Driver;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Comparators {
//    public static int compareByDateTime(LocalDateTime ldt1, LocalDateTime ldt2) {
//        if(ldt1.isBefore(ldt2)) {
//            return -1;
//        } else if (ldt1.isAfter(ldt2)) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }



    public static Comparator<Driver> compare() {
        Comparator<Driver> cmp = Comparator.comparing(Driver::getChangeDriverStatusTime);
        return cmp;
    }
}
