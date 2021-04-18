package me.saro.kit.legacy

import me.saro.kit.Dates
import java.util.*

/**
 * const date time
 * <br></br>
 * it is for old java - Instead, "joda-time" is recommended.
 * <br></br>
 * this class **thread-safe** because all compute use clone
 * @author PARK Yong Seo
 */
@Deprecated(message = "use joda or java time")
class ConstDateTime constructor(private val calendar: Calendar = Calendar.getInstance()): Cloneable {

    constructor(timeInMillis: Long) : this(Calendar.getInstance().apply { this.timeInMillis = timeInMillis })

    fun timezone(timeZone: TimeZone?): ConstDateTime = clone().apply { calendar.timeZone = timeZone }

    /**
     * return plus milliseconds
     * @param milliseconds
     * @return
     */
    fun plusMilliseconds(milliseconds: Int): ConstDateTime = clone().apply { calendar.add(Calendar.MILLISECOND, milliseconds) }

    /**
     * return plus minutes
     * @param minutes
     * @return
     */
    fun plusMinutes(minutes: Int): ConstDateTime = clone().apply { calendar.add(Calendar.MINUTE, minutes) }

    /**
     * return plus hours
     * @param hours
     * @return
     */
    fun plusHours(hours: Int): ConstDateTime = clone().apply { calendar.add(Calendar.HOUR, hours) }

    /**
     * return plus dates
     * @param days
     * @return
     */
    fun plusDays(days: Int): ConstDateTime = clone().apply { calendar.add(Calendar.DATE, days) }

    /**
     * return plus month
     * @param month
     * @return
     */
    fun plusMonths(month: Int): ConstDateTime = clone().apply { calendar.add(Calendar.MONTH, month) }

    /**
     * return plus year
     * @param year
     * @return
     */
    fun plusYears(year: Int): ConstDateTime = clone().apply { calendar.add(Calendar.YEAR, year) }

    /**
     * **WARNING : ** is not Milliseconds<br></br>
     * return with timeInMillis
     * @param timeInMillis
     * @return
     */
    fun withTimeInMillis(timeInMillis: Long): ConstDateTime = clone().apply { calendar.timeInMillis = timeInMillis }

    /**
     * **WARNING : ** is not TimeInMillis<br></br>
     * return with milliseconds
     * @param milliseconds
     * @return
     */
    fun withMilliseconds(milliseconds: Int): ConstDateTime = clone().apply { calendar[Calendar.MILLISECOND] = milliseconds }

    /**
     * return with seconds
     * @param seconds
     * @return
     */
    fun withSeconds(seconds: Int): ConstDateTime = clone().apply { calendar[Calendar.SECOND] = seconds }

    /**
     * return with minutes
     * @param minutes
     * @return
     */
    fun withMinutes(minutes: Int): ConstDateTime = clone().apply { calendar[Calendar.MINUTE] = minutes }

    /**
     * return with hours
     * @param hours
     * @return
     */
    fun withHours(hours: Int): ConstDateTime = clone().apply { calendar[Calendar.HOUR] = hours }

    /**
     * return with dayOfMonth
     * @param day
     * @return
     */
    fun withDayOfMonth(day: Int): ConstDateTime = clone().apply { calendar[Calendar.DAY_OF_MONTH] = day }

    /**
     * return with month
     * @param month
     * @return
     */
    fun withMonth(month: Int): ConstDateTime = clone().apply { calendar[Calendar.MONTH] = month }

    /**
     * return with year
     * @param year
     * @return
     */
    fun withYear(year: Int): ConstDateTime = clone().apply { calendar[Calendar.YEAR] = year }

    /**
     * get TimeInMillis
     * <br></br>
     * **WARNING : ** is not Milliseconds
     * @return
     */
    val timeInMillis: Long get() = calendar.timeInMillis

    /**
     * get Milliseconds
     * <br></br>
     * **WARNING : ** is not TimeInMillis
     * @return
     */
    val milliseconds: Int get() = calendar[Calendar.MILLISECOND]

    /**
     * get Seconds
     * @return
     */
    val seconds: Int get() = calendar[Calendar.SECOND]

    /**
     * get Minute
     * @return
     */
    val minute: Int get() = calendar[Calendar.MINUTE]

    /**
     * get Hours
     * @return
     */
    val hours: Int get() = calendar[Calendar.HOUR]

    /**
     * get Date
     * @return
     */
    val date: Int get() = calendar[Calendar.DATE]

    /**
     * get Month
     * @return
     */
    val month: Int get() = calendar[Calendar.MONTH]

    /**
     * get Year
     * @return
     */
    val year: Int get() = calendar[Calendar.YEAR]

    /**
     * get DayOfWeek
     * @return
     */
    val dayOfWeek: Int get() = calendar[Calendar.DAY_OF_WEEK]

    /**
     * get DayOfIntWeek
     * 0:sun - 1:mon ... 6:sat
     * @return
     */
    val dayOfIntWeek: Int get() {
            when (calendar[Calendar.DAY_OF_WEEK]) {
                Calendar.SUNDAY -> return 0
                Calendar.MONDAY -> return 1
                Calendar.TUESDAY -> return 2
                Calendar.WEDNESDAY -> return 3
                Calendar.THURSDAY -> return 4
                Calendar.FRIDAY -> return 5
                Calendar.SATURDAY -> return 6
            }
            throw RuntimeException()
        }

    /**
     * get WeekOfMonth
     * @return
     */
    val weekOfMonth: Int get() = calendar[Calendar.WEEK_OF_MONTH]

    /**
     * get WeekOfYear
     * @return
     */
    val weekOfYear: Int get() = calendar[Calendar.WEEK_OF_YEAR]

    /**
     * Last Day Of Month
     * @return
     */
    val lastDayOfMonth: Int get() = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    /**
     * diff year
     * @param constDateTime
     * @return
     */
    fun diffYears(constDateTime: ConstDateTime): Int = Math.abs(year - constDateTime.year)

    /**
     * diff month
     * @param constDateTime
     * @return
     */
    fun diffMonths(constDateTime: ConstDateTime): Int = Math.abs(year * 12 + month - (constDateTime.year * 12 + constDateTime.month))

    /**
     * diff day
     * @param constDateTime
     * @return
     */
    fun diffDays(constDateTime: ConstDateTime): Int {
        val sd = onlyDate().calendar
        val ed = constDateTime.onlyDate().calendar
        var diffDays = ((ed.timeInMillis - sd.timeInMillis) / 86400000L).toInt()
        sd.add(Calendar.DATE, diffDays)
        while (sd.before(ed)) {
            sd.add(Calendar.DATE, 1)
            diffDays++
        }
        while (sd.after(ed)) {
            sd.add(Calendar.DATE, -1)
            diffDays--
        }
        return Math.abs(diffDays)
    }

    /**
     * Days remaining until last day of month<br></br>
     * ex) 2019-12-30 -&gt; 1<br></br>
     * ex) 2019-12-31 -&gt; 0
     * @return
     */
    fun remainDaysUntilLastDayOfMonth(): Int {
        return diffDays(withDayOfMonth(lastDayOfMonth))
    }

    /**
     * DateFormat to String
     * @param format
     * @return
     */
    fun format(format: String?): String {
        return Dates.format(calendar.time, format)
    }

    /**
     * DateFormat to String
     * <br></br>
     * same format(String format)
     * @param format
     * @return
     */
    fun toString(format: String?): String {
        return format(format)
    }

    /**
     * to ISO8601
     * yyyy-MM-dd'T'HH:mm:ssZ[+HH:mm]
     */
    override fun toString(): String {
        return toISO8601()
    }

    /**
     * to ISO8601
     * @return
     */
    fun toISO8601(): String {
        val format = this.format("yyyy-MM-dd'T'HH:mm:ssZ")
        return if (format.lastIndexOf('+') != format.length - 5) format.replaceFirst("([\\d]{2})$".toRegex(), ":$1") else format
    }

    /**
     * remove hh MM ss
     */
    fun onlyDate(): ConstDateTime {
        val rv = clone()
        val cal = rv.calendar
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return rv
    }

    /**
     *
     * @param constDateTime
     * @return
     */
    fun equalsYear(constDateTime: ConstDateTime): Boolean {
        return year == constDateTime.year
    }

    /**
     *
     * @param constDateTime
     * @return
     */
    fun equalsYearMonth(constDateTime: ConstDateTime): Boolean {
        return equalsYear(constDateTime) && month == constDateTime.month
    }

    /**
     *
     * @param constDateTime
     * @return
     */
    fun equalsYearMonthDate(constDateTime: ConstDateTime): Boolean {
        return equalsYearMonth(constDateTime) && date == constDateTime.date
    }

    /**
     * to Date
     * @return
     */
    fun toDate(): Date {
        return calendar.time
    }

    /**
     * clone
     */
    override fun clone(): ConstDateTime = ConstDateTime((calendar.clone() as Calendar).timeInMillis)

    /**
     * toCalendar
     */
    fun toCalendar(): Calendar = calendar.clone() as Calendar

    /**
     * equals
     */
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun equals(obj: Any?): Boolean {
        return if (obj != null && this.javaClass.name == obj.javaClass.name) {
            timeInMillis == (obj as ConstDateTime).timeInMillis
        } else false
    }

    companion object {
        /**
         * new instant DateFormat with Calendar.getInstance()
         * @return
         */
        fun now(): ConstDateTime {
            return ConstDateTime(Calendar.getInstance().timeInMillis)
        }

        /**
         * timeInMillis to DateFormat
         * @param timeInMillis
         * @return
         */
        fun parse(timeInMillis: Long): ConstDateTime {
            return ConstDateTime(timeInMillis)
        }

        /**
         * String date to DateFormat
         * @param date
         * @param format
         * @return
         */
        @JvmStatic
        fun parse(date: String?, format: String?): ConstDateTime {
            return ConstDateTime(Dates.parseCalendar(date, format).timeInMillis)
        }

        /**
         * convert string date format
         * @param date
         * @param oldFormat
         * @param newFormat
         * @return
         */
        fun format(date: String?, oldFormat: String?, newFormat: String?): String? {
            try {
                if (date != null && !date.isEmpty()) {
                    return parse(date, oldFormat).toString(newFormat)
                }
            } catch (e: Exception) {
            }
            return null
        }

        /**
         * date validator
         * @param date
         * @param format
         * @return
         */
        fun valid(date: String?, format: String?): Boolean {
            try {
                if (date != null && format != null) {
                    val df = parse(date, format)
                    return df.toString(format) == date
                }
            } catch (e: Exception) {
            }
            return false
        }
    }
}