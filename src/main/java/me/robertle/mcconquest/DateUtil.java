package me.robertle.mcconquest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static String getDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(now);
    }

    public static String getTimerFormat(int seconds) {

        if (seconds/60 < 10 && seconds%60 < 10) return "0"+seconds/60 + ":" + "0" + seconds%60;
        if (seconds/60 < 10) return "0"+seconds/60 + ":" + seconds%60;
        if (seconds%60 < 10) return seconds/60 + ":" + "0" + seconds%60;
        return "";
    }

    public static int getHour() {
        Date now = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
