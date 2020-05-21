package me.robertle.mcconquest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(now);
    }

}
