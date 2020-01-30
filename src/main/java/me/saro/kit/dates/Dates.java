package me.saro.kit.dates;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * date util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Dates {

    private static Map<String, DateTimeFormatter> formatters = new HashMap<>();
    private static Map<String, SimpleDateFormat> simpleDateFormats = new HashMap<>();

    /**
     *
     * @param calendar
     * @return
     */
    public static ZonedDateTime toZonedDateTime(Calendar calendar) {
        TimeZone tz = calendar.getTimeZone();
        return ZonedDateTime.ofInstant(calendar.toInstant(), tz != null ? tz.toZoneId() : ZoneId.systemDefault());
    }

    /**
     *
     * @param calendar
     * @return
     */
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        TimeZone tz = calendar.getTimeZone();
        return LocalDateTime.ofInstant(calendar.toInstant(), tz != null ? tz.toZoneId() : ZoneId.systemDefault());
    }

    /**
     *
     * @param calendar
     * @return
     */
    public static LocalDate toLocalDate(Calendar calendar) {
        TimeZone tz = calendar.getTimeZone();
        return LocalDateTime.ofInstant(calendar.toInstant(), tz != null ? tz.toZoneId() : ZoneId.systemDefault()).toLocalDate();
    }

    /**
     *
     * @param calendar
     * @return
     */
    public static LocalTime toLocalTime(Calendar calendar) {
        TimeZone tz = calendar.getTimeZone();
        return LocalDateTime.ofInstant(calendar.toInstant(), tz != null ? tz.toZoneId() : ZoneId.systemDefault()).toLocalTime();
    }

    /**
     *
     * @param date
     * @param format
     * @param zone
     * @return
     */
    public static ZonedDateTime parseZonedDateTime(String date, String format, ZoneId zone) {
        return ZonedDateTime.parse(date, toFormatter(format).withZone(zone));
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static ZonedDateTime parseZonedDateTime(String date, String format) {
        return ZonedDateTime.parse(date, toFormatter(format));
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String date, String format) {
        return LocalDateTime.parse(date, toFormatter(format));
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static LocalDate parseLocalDate(String date, String format) {
        return LocalDate.parse(date, toFormatter(format));
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static LocalTime parseLocalTime(String date, String format) {
        return LocalTime.parse(date, toFormatter(format));
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static Calendar parseCalendar(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(date, format));
        return calendar;
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static Date parseDate(String date, String format) {
        try {
            return toSimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new DateTimeParseException(e.getMessage(), date, -1, e);
        }
    }

    /**
     *
     * @param format
     * @return
     */
    public static synchronized DateTimeFormatter toFormatter(String format) {
        DateTimeFormatter formatter;
        if ((formatter = formatters.get(format)) == null) {
            formatters.put(format, (formatter = DateTimeFormatter.ofPattern(format)));
        }
        return formatter;
    }

    /**
     *
     * @param format
     * @return
     */
    public static synchronized SimpleDateFormat toSimpleDateFormat(String format) {
        SimpleDateFormat simpleDateFormat;
        if ((simpleDateFormat = simpleDateFormats.get(format)) == null) {
            simpleDateFormats.put(format, (simpleDateFormat = new SimpleDateFormat(format)));
        }
        // SimpleDateFormat is not thread-safe
        // solution is return cloned object
        return (SimpleDateFormat)simpleDateFormat.clone();
    }

    /**
     * format
     * @param date
     * @param format
     * @return
     */
    public static synchronized String format(Date date, String format) {
        return toSimpleDateFormat(format).format(date);
    }

    /**
     *
     * @param date
     * @return
     */
    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     *
     * @param zonedDateTime
     * @return
     */
    public static Calendar toCalendar(ZonedDateTime zonedDateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(
                zonedDateTime.getYear(), zonedDateTime.getMonthValue() - 1, zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond()
        );
        calendar.set(Calendar.MILLISECOND, Math.round(zonedDateTime.getNano() / 1_000_000));
        calendar.setTimeZone(TimeZone.getTimeZone(zonedDateTime.getZone()));
        return calendar;
    }

    /**
     *
     * @param localDateTime
     * @return
     */
    public static Calendar toCalendar(LocalDateTime localDateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(
                localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
        );
        calendar.set(Calendar.MILLISECOND, localDateTime.getNano() / 1_000_000);
        return calendar;
    }

    /**
     *
     * @param localDate
     * @return
     */
    public static Calendar toCalendar(LocalDate localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        return calendar;
    }

    /**
     *
     * @param localTime
     * @return
     */
    public static Calendar toCalendar(LocalTime localTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.HOUR, localTime.getHour());
        calendar.set(Calendar.MINUTE, localTime.getMinute());
        calendar.set(Calendar.SECOND, localTime.getSecond());
        calendar.set(Calendar.MILLISECOND, localTime.getNano() / 1_000_000);
        return calendar;
    }
}
