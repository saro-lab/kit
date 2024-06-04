package me.saro.kit

class TextKit {
    companion object {
        @JvmStatic
        val BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()

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
        fun generateBase62(minLength: Int, maxLength: Int): String =
            generate(BASE62, minLength, maxLength)

        @JvmStatic
        fun generateBase62(length: Int): String =
            generate(BASE62, length)

        @JvmStatic
        fun toString(e: Exception?): String =
            e?.stackTrace?.joinToString("\n", "${e.message}\n")?: ""
    }
}
