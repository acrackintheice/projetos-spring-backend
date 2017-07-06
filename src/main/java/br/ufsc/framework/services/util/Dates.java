package br.ufsc.framework.services.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Dates {

    private Dates() {

    }

    static final long ONE_HOUR = 60 * 60 * 1000L;

    public static String formatDate(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Integer daysBetween(Date a, Date b) {

        int tempDifference = 0;
        int difference = 0;
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();

        earlier.setTime(a);
        later.setTime(b);

        while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
            tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
            tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        return difference;

    }

    public static Date getYearDate(Integer year) {
        GregorianCalendar gc = new GregorianCalendar();

        gc.set(GregorianCalendar.YEAR, year);
        gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
        gc.set(GregorianCalendar.MONTH, 0);
        gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
        gc.set(GregorianCalendar.MINUTE, 0);
        gc.set(GregorianCalendar.SECOND, 0);
        gc.set(GregorianCalendar.MILLISECOND, 0);

        return gc.getTime();

    }

}
