package me.saro.kit.dates;

import java.util.Calendar;
import java.util.Date;

/**
 * const date time
 * <br>
 * this class <b>thread-safe</b> because all compute use clone
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class ConstDateTime {

    // inner object
    final private Calendar calendar;

    /**
     * private Constructor
     * @param timeInMillis
     * java timeInMillis 
     */
    private ConstDateTime(long timeInMillis) {
        calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(timeInMillis);
    }

    /**
     * new instant DateFormat with Calendar.getInstance()
     * @return
     */
    public static ConstDateTime now() {
        return new ConstDateTime(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * timeInMillis to DateFormat
     * @param timeInMillis
     * @return
     */
    public static ConstDateTime parse(long timeInMillis) {
        return new ConstDateTime(timeInMillis);
    }

    /**
     * String date to DateFormat
     * @param date
     * @param format
     * @return
     */
    public static ConstDateTime parse(String date, String format) {
        return new ConstDateTime(Dates.parseCalendar(date, format).getTimeInMillis());
    }

    /**
     * convert string date format
     * @param date
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String format(String date, String oldFormat, String newFormat) {
        try {
            if (date != null && !date.isEmpty()) {
                return ConstDateTime.parse(date, oldFormat).toString(newFormat);
            }
        } catch (Exception e) {}
        return null;
    }

    /**
     * date validator 
     * @param date
     * @param format
     * @return
     */
    public static boolean valid(String date, String format) {
        try {
            if (date != null && format != null) {
                ConstDateTime df = ConstDateTime.parse(date, format);
                return df.toString(format).equals(date);
            }
        } catch (Exception e) {}
        return false;
    }

    /**
     * add Milliseconds
     * @param milliseconds
     * @return
     */
    public ConstDateTime plusMilliseconds(int milliseconds) {
        var rv = clone();
        rv.calendar.add(Calendar.MILLISECOND, milliseconds);
        return rv;
    }

    /**
     * add Minutes
     * @param minutes
     * @return
     */
    public ConstDateTime plusMinutes(int minutes) {
        var rv = clone();
        rv.calendar.add(Calendar.MINUTE, minutes);
        return rv;
    }

    /**
     * add Hours
     * @param hours
     * @return
     */
    public ConstDateTime plusHours(int hours) {
        var rv = clone();
        rv.calendar.add(Calendar.HOUR, hours);
        return rv;
    }

    /**
     * add Date
     * @param date
     * @return
     */
    public ConstDateTime plusDates(int date) {
        var rv = clone();
        rv.calendar.add(Calendar.DATE, date);
        return rv;
    }

    /**
     * add Month
     * @param month
     * @return
     */
    public ConstDateTime plusMonth(int month) {
        var rv = clone();
        rv.calendar.add(Calendar.MONTH, month);
        return rv;
    }

    /**
     * add Year
     * @param year
     * @return
     */
    public ConstDateTime plusYear(int year) {
        var rv = clone();
        rv.calendar.add(Calendar.YEAR, year);
        return rv;
    }

    /**
     * set TimeInMillis
     * <b>WARNING : </b> is not Milliseconds
     * @param timeInMillis
     * @return
     */
    public ConstDateTime withTimeInMillis(long timeInMillis) {
        var rv = clone();
        rv.calendar.setTimeInMillis(timeInMillis);
        return rv;
    }

    /**
     * set milliseconds
     * <br>
     * <b>WARNING : </b> is not TimeInMillis
     * @param milliseconds
     * @return
     */
    public ConstDateTime withMilliseconds(int milliseconds) {
        var rv = clone();
        rv.calendar.set(Calendar.MILLISECOND, milliseconds);
        return rv;
    }

    /**
     * set Seconds
     * @param seconds
     * @return
     */
    public ConstDateTime withSeconds(int seconds) {
        var rv = clone();
        rv.calendar.set(Calendar.SECOND, seconds);
        return rv;
    }

    /**
     * set Minutes
     * @param minutes
     * @return
     */
    public ConstDateTime withMinutes(int minutes) {
        var rv = clone();
        rv.calendar.set(Calendar.MINUTE, minutes);
        return rv;
    }

    /**
     * set Hours
     * @param hours
     * @return
     */
    public ConstDateTime withHours(int hours) {
        var rv = clone();
        rv.calendar.set(Calendar.HOUR, hours);
        return rv;
    }

    /**
     * set Date
     * @param day
     * @return
     */
    public ConstDateTime withDayOfMonth(int day) {
        var rv = clone();
        rv.calendar.set(Calendar.DAY_OF_MONTH, day);
        return rv;
    }

    /**
     * set Month
     * @param month
     * @return
     */
    public ConstDateTime withMonth(int month) {
        var rv = clone();
        rv.calendar.set(Calendar.MONTH, month);
        return rv;
    }

    /**
     * set Year
     * @param year
     * @return
     */
    public ConstDateTime withYear(int year) {
        var rv = clone();
        rv.calendar.set(Calendar.YEAR, year);
        return rv;
    }

    /**
     * get TimeInMillis
     * <br>
     * <b>WARNING : </b> is not Milliseconds
     * @return
     */
    public long getTimeInMillis() {
        return calendar.getTimeInMillis();
    }

    /**
     * get Milliseconds
     * <br>
     * <b>WARNING : </b> is not TimeInMillis
     * @return
     */
    public int getMilliseconds() {
        return calendar.get(Calendar.MILLISECOND);
    }

    /**
     * get Seconds
     * @return
     */
    public int getSeconds() {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * get Minute
     * @return
     */
    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * get Hours
     * @return
     */
    public int getHours() {
        return calendar.get(Calendar.HOUR);
    }

    /**
     * get Date
     * @return
     */
    public int getDate() {
        return calendar.get(Calendar.DATE);
    }

    /**
     * get Month
     * @return
     */
    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    /** 
     * get Year
     * @return
     */
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * get DayOfWeek
     * @return
     */
    public int getDayOfWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * get DayOfIntWeek
     * 0:sun - 1:mon ... 6:sat
     * @return
     */
    public int getDayOfIntWeek() {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY: return 0;
            case Calendar.MONDAY: return 1;
            case Calendar.TUESDAY: return 2;
            case Calendar.WEDNESDAY: return 3;
            case Calendar.THURSDAY: return 4;
            case Calendar.FRIDAY: return 5;
            case Calendar.SATURDAY: return 6;
        }
        throw new RuntimeException();
    }

    /**
     * get WeekOfMonth
     * @return
     */
    public int getWeekOfMonth() {
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * get WeekOfYear
     * @return
     */
    public int getWeekOfYear() {
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Last Day Of Month
     * @return
     */
    public int getLastDayOfMonth() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * diff year
     * @param constDateTime
     * @return
     */
    public int diffYears(ConstDateTime constDateTime) {
        return Math.abs(getYear() - constDateTime.getYear());
    }

    /**
     * diff month
     * @param constDateTime
     * @return
     */
    public int diffMonths(ConstDateTime constDateTime) {
        return Math.abs(((getYear() * 12) + getMonth()) - ((constDateTime.getYear() * 12) + constDateTime.getMonth()));
    }

    /**
     * diff day
     * @param constDateTime
     * @return
     */
    public int diffDays(ConstDateTime constDateTime) {
        var sd = onlyDate().calendar;
        var ed = constDateTime.onlyDate().calendar;
        int diffDays = (int)((ed.getTimeInMillis() - sd.getTimeInMillis()) / 86400000L);

        sd.add(Calendar.DATE, diffDays);

        while (sd.before(ed)) {
            sd.add(Calendar.DATE, 1);
            diffDays++;
        }
        while (sd.after(ed)) {
            sd.add(Calendar.DATE, -1);
            diffDays--;
        }
        return Math.abs(diffDays);
    }

    /**
     * Days remaining until last day of month<br>
     * ex) 2019-12-30 -&gt; 1<br>
     * ex) 2019-12-31 -&gt; 0
     * @return
     */
    public int remainDaysUntilLastDayOfMonth() {
        return diffDays(withDayOfMonth(getLastDayOfMonth()));
    }

    /**
     * DateFormat to String
     * @param format
     * @return
     */
    public String format(String format) {
        return Dates.format(calendar.getTime(), format);
    }

    /**
     * DateFormat to String
     * <br>
     * same format(String format)
     * @param format
     * @return
     */
    public String toString(String format) {
        return format(format);
    }

    /**
     * to ISO8601
     * yyyy-MM-dd'T'HH:mm:ssZ[+HH:mm]
     */
    public String toString() {
        return toISO8601();
    }

    /**
     * to ISO8601
     * @return
     */
    public String toISO8601() {
        String format = this.format("yyyy-MM-dd'T'HH:mm:ssZ");
        return format.lastIndexOf('+') != format.length() - 5 ? format.replaceFirst("([\\d]{2})$", ":$1") : format;
    }

    /**
     * remove hh MM ss
     */
    public ConstDateTime onlyDate() {
        var rv = clone();
        var cal = rv.calendar;
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return rv;
    }

    /**
     *
     * @param constDateTime
     * @return
     */
    public boolean equalsYear(ConstDateTime constDateTime) {
        return getYear() == constDateTime.getYear();
    }

    /**
     *
     * @param constDateTime
     * @return
     */
    public boolean equalsYearMonth(ConstDateTime constDateTime) {
        return equalsYear(constDateTime) && getMonth() == constDateTime.getMonth();
    }

    /**
     *
     * @param constDateTime
     * @return
     */
    public boolean equalsYearMonthDate(ConstDateTime constDateTime) {
        return equalsYearMonth(constDateTime) && getDate() == constDateTime.getDate();
    }

    /**
     * to Date
     * @return
     */
    public Date toDate() {
        return this.calendar.getTime();
    }

    /**
     * clone
     */
    public ConstDateTime clone() {
        return new ConstDateTime(((Calendar)this.calendar.clone()).getTimeInMillis());
    }

    /**
     * toCalendar
     */
    public Calendar toCalendar() {
        return (Calendar)this.calendar.clone();
    }

    /**
     * equals
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass().getName().equals(obj.getClass().getName())) {
            return this.getTimeInMillis() == ((ConstDateTime)obj).getTimeInMillis();
        }
        return false;
    }
}
