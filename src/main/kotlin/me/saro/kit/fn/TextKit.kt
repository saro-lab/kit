package me.saro.kit.fn

import java.util.regex.Matcher
import java.util.regex.Pattern

class TextKit {
    companion object {
        @JvmStatic
        val BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()

        @JvmStatic
        val BASE52 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()

        @JvmStatic
        fun generate(mold: CharArray, length: Int): String {
            val rv = CharArray(length)
            for (i in 0 until length) {
                rv[i] = mold[(Math.random() * mold.size).toInt()]
            }
            return String(rv)
        }

        @JvmStatic
        fun generate(mold: CharArray, minLength: Int, maxLength: Int): String =
            if (minLength == maxLength) generate(mold, minLength)
            else generate(mold, ((Math.random() * (maxLength - minLength)) + minLength).toInt())

        @JvmStatic
        fun replace(text: String, pattern: Pattern, replace: (String) -> String): String {
            val matcher = pattern.matcher(text)
            val sb = StringBuffer()
            while (matcher.find()) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(replace(matcher.group())))
            }
            matcher.appendTail(sb)
            return sb.toString()
        }

        @JvmStatic
        fun toString(e: Exception?): String =
            e?.stackTrace?.joinToString("\n", "${e.message}\n")?: ""
    }
}
