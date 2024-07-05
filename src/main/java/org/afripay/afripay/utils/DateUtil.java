package org.afripay.afripay.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static Date dateFormat(String date) {
        Date date1 = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String dateToString(Date date) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return format.format(date);
    }

    public static String dateToJoinedString(Date date, String formatString) {
        formatString = GeneralUtil.stringIsNullOrEmpty(formatString) ? "yyyyMMdd" : formatString;

        DateFormat format = new SimpleDateFormat(formatString);

        return format.format(date);
    }

    public static Date dateTimeFullFormat(String date) {
        Date date1 = null;
        String dateTime = date + " 00:00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date1 = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static boolean isFutureDate(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfToday = localDateTime.with(LocalTime.MAX);

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        tomorrow = tomorrow.with(LocalTime.MIN);

        return endOfToday.equals(tomorrow) || endOfToday.isAfter(tomorrow);
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {

        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getDaysAgo(Date date, int daysAgo) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        localDateTime = localDateTime.minusDays(daysAgo);
        return localDateTimeToDate(localDateTime);
    }

    public static String getDayOfWeek(String day) {
        Date date = dateTimeFullFormat(day);
        Locale locale = Locale.getDefault();
        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        return formatter.format(date);
    }

    public static Date todayDate() {
        Date todayDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = simpleDateFormat.format(todayDate);
        return dateTimeFullFormat(today);
    }

    public static int calculateAge(int year, int month, int day) {

        LocalDate dob = LocalDate.of(year, month, day);
        LocalDate curDate = LocalDate.now();
        //calculates the amount of time between two dates and returns the years
        return Period.between(dob, curDate).getYears();
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static Date todayDateInAnyYear(int yearDiff) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, yearDiff);
        Date anyYear = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String yearDate = simpleDateFormat.format(anyYear);
        return dateTimeFullFormat(yearDate);
    }

}