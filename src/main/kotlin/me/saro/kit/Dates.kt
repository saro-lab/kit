package me.saro.kit

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

/**
 * date util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
open class Dates {

    companion object {
        private val formatters: MutableMap<String, DateTimeFormatter> = HashMap()
        private val simpleDateFormats: MutableMap<String, SimpleDateFormat> = HashMap()

        /**
         *
         * @param calendar
         * @return
         */
        @JvmStatic
        fun toZonedDateTime(calendar: Calendar): ZonedDateTime {
            val tz = calendar.timeZone
            return ZonedDateTime.ofInstant(calendar.toInstant(), if (tz != null) tz.toZoneId() else ZoneId.systemDefault())
        }

        /**
         *
         * @param calendar
         * @return
         */
        @JvmStatic
        fun toLocalDateTime(calendar: Calendar): LocalDateTime {
            val tz = calendar.timeZone
            return LocalDateTime.ofInstant(calendar.toInstant(), if (tz != null) tz.toZoneId() else ZoneId.systemDefault())
        }

        /**
         *
         * @param calendar
         * @return
         */
        @JvmStatic
        fun toLocalDate(calendar: Calendar): LocalDate {
            val tz = calendar.timeZone
            return LocalDateTime.ofInstant(calendar.toInstant(), if (tz != null) tz.toZoneId() else ZoneId.systemDefault())
                .toLocalDate()
        }

        /**
         *
         * @param calendar
         * @return
         */
        @JvmStatic
        fun toLocalTime(calendar: Calendar): LocalTime {
            val tz = calendar.timeZone
            return LocalDateTime.ofInstant(calendar.toInstant(), if (tz != null) tz.toZoneId() else ZoneId.systemDefault())
                .toLocalTime()
        }

        /**
         *
         * @param date
         * @param format
         * @param zone
         * @return
         */
        @JvmStatic
        fun parseZonedDateTime(date: String?, format: String, zone: ZoneId?): ZonedDateTime {
            return ZonedDateTime.parse(date, toFormatter(format).withZone(zone))
        }

        /**
         *
         * @param date
         * @param format
         * @return
         */
        @JvmStatic
        fun parseZonedDateTime(date: String?, format: String): ZonedDateTime {
            return ZonedDateTime.parse(date, toFormatter(format))
        }

        /**
         *
         * @param date
         * @param format
         * @return
         */
        @JvmStatic
        fun parseLocalDateTime(date: String?, format: String): LocalDateTime {
            return LocalDateTime.parse(date, toFormatter(format))
        }

        /**
         *
         * @param date
         * @param format
         * @return
         */
        @JvmStatic
        fun parseLocalDate(date: String?, format: String): LocalDate {
            return LocalDate.parse(date, toFormatter(format))
        }

        /**
         *
         * @param date
         * @param format
         * @return
         */
        @JvmStatic
        fun parseLocalTime(date: String?, format: String): LocalTime {
            return LocalTime.parse(date, toFormatter(format))
        }

        /**
         *
         * @param date
         * @param format
         * @return
         */
        @JvmStatic
        fun parseCalendar(date: String?, format: String): Calendar {
            val calendar = Calendar.getInstance()
            calendar.time = parseDate(date, format)
            return calendar
        }

        /**
         *
         * @param date
         * @param format
         * @return
         */
        @JvmStatic
        fun parseDate(date: String?, format: String): Date {
            return try {
                toSimpleDateFormat(format).parse(date)
            } catch (e: ParseException) {
                throw DateTimeParseException(e.message, date, -1, e)
            }
        }

        /**
         *
         * @param format
         * @return
         */
        @Synchronized
        @JvmStatic
        fun toFormatter(format: String): DateTimeFormatter {
            var formatter: DateTimeFormatter
            if (formatters[format].also { formatter = it!! } == null) {
                formatters[format] = DateTimeFormatter.ofPattern(format).also { formatter = it }
            }
            return formatter
        }

        /**
         *
         * @param format
         * @return
         */
        @Synchronized
        @JvmStatic
        fun toSimpleDateFormat(format: String): SimpleDateFormat {
            var simpleDateFormat: SimpleDateFormat
            if (simpleDateFormats[format].also { simpleDateFormat = it!! } == null) {
                simpleDateFormats[format] = SimpleDateFormat(format).also { simpleDateFormat = it }
            }
            // SimpleDateFormat is not thread-safe
            // solution is return cloned object
            return simpleDateFormat.clone() as SimpleDateFormat
        }

        /**
         * format
         * @param date
         * @param format
         * @return
         */
        @Synchronized
        @JvmStatic
        fun format(date: Date?, format: String): String {
            return toSimpleDateFormat(format).format(date)
        }

        /**
         *
         * @param date
         * @return
         */
        @JvmStatic
        fun toCalendar(date: Date?): Calendar {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar
        }

        /**
         *
         * @param zonedDateTime
         * @return
         */
        @JvmStatic
        fun toCalendar(zonedDateTime: ZonedDateTime): Calendar {
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar[zonedDateTime.year, zonedDateTime.monthValue - 1, zonedDateTime.dayOfMonth, zonedDateTime.hour, zonedDateTime.minute] =
                zonedDateTime.second
            calendar[Calendar.MILLISECOND] = Math.round((zonedDateTime.nano / 1000000).toFloat())
            calendar.timeZone = TimeZone.getTimeZone(zonedDateTime.zone)
            return calendar
        }

        /**
         *
         * @param localDateTime
         * @return
         */
        @JvmStatic
        fun toCalendar(localDateTime: LocalDateTime): Calendar {
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar[localDateTime.year, localDateTime.monthValue - 1, localDateTime.dayOfMonth, localDateTime.hour, localDateTime.minute] =
                localDateTime.second
            calendar[Calendar.MILLISECOND] = localDateTime.nano / 1000000
            return calendar
        }

        /**
         *
         * @param localDate
         * @return
         */
        @JvmStatic
        fun toCalendar(localDate: LocalDate): Calendar {
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar[localDate.year, localDate.monthValue - 1] = localDate.dayOfMonth
            return calendar
        }

        /**
         *
         * @param localTime
         * @return
         */
        @JvmStatic
        fun toCalendar(localTime: LocalTime): Calendar {
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar[Calendar.HOUR] = localTime.hour
            calendar[Calendar.MINUTE] = localTime.minute
            calendar[Calendar.SECOND] = localTime.second
            calendar[Calendar.MILLISECOND] = localTime.nano / 1000000
            return calendar
        }
    }


}