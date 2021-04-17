package me.saro.kit

import me.saro.kit.dates.Dates

/**
 * valid util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
open class Valids {
    companion object {
        /**
         * mail check<br></br>
         * - not allow Top-Level Domain<br></br>
         * - not support unicode
         */
        @kotlin.jvm.JvmStatic
        val IS_MAIL = "[_a-z0-9\\-]+(\\.[_a-z0-9\\-]+)*@([_a-z0-9\\-]+\\.)+[a-z]{2,}"

        /**
         * mail check<br></br>
         * - not allow Top-Level Domain<br></br>
         * - not support unicode
         * **cautious:**<br></br>
         * this function not requirements rfc spec<br></br>
         * - https://tools.ietf.org/html/rfc5321<br></br>
         * - https://tools.ietf.org/html/rfc5322
         * **For example**<br></br>
         * rfc valid : abc@abc<br></br>
         * isMail not valid : abc@abc (Top-Level Domain)
         * rfc5 valid : abc@saro.me<br></br>
         * isMail valid : abc@saro.me
         *
         * @param mail address
         * @param maxLength limit mail length
         * @return is valid
         */
        @kotlin.jvm.JvmStatic
        fun isMail(mail: String?, maxLength: Int): Boolean {
            return mail != null && mail.length <= maxLength && mail.matches(IS_MAIL.toRegex())
        }

        /**
         * all parameters is not null
         * @param objs
         * @return
         */
        @kotlin.jvm.JvmStatic
        fun isNotNull(vararg objs: Any?): Boolean {
            if (objs != null) {
                for (obj in objs) {
                    if (obj == null) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        /**
         * all parameters is not blank
         * @param texts
         * @return
         */
        @kotlin.jvm.JvmStatic
        fun isNotBlank(vararg texts: String?): Boolean {
            if (texts != null) {
                for (text in texts) {
                    if (text == null || text.matches("[\\s]*".toRegex())) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        /**
         * check date validation
         * @param date
         * @param format
         * @return
         */
        @kotlin.jvm.JvmStatic
        fun isDate(date: String, format: String?): Boolean {
            return try {
                Dates.format(Dates.parseDate(date, format), format) == date
            } catch (e: Exception) {
                false
            }
        }
    }
}