package me.robertle.mcconquest;

import java.text.SimpleDateFormat;
import java.time.Instant;
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
        return seconds / 60 + ":" + seconds % 60;
    }

    public static String getDate(long seconds) {
        Date now = new Date(seconds * 1000);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd h:mm");
        return format.format(now);
    }

    public static int getHour() {
        Date now = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        Date now = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.MINUTE);
    }

    public static long getEpochTime() {
        return Instant.now().getEpochSecond();
    }


    public static String getTimeTil(int hour, int minute) {
        String hourTil;
        if (getHour() < hour) {
            hourTil = String.valueOf(hour - getHour() - 1);
        } else {
            hourTil = String.valueOf((hour + 24) - getHour() - 1);
        }
        String minuteTil;
        if (getMinute() < minute) {
            minuteTil = String.valueOf(minute - getMinute());
        } else {
            minuteTil = String.valueOf((minute + 60) - getMinute());
        }
        return hourTil + (hourTil.equals("1") ? " hour and " : " hours and ") + minuteTil + (minuteTil.equals("1") ? " minute" : " minutes");
    }

}
