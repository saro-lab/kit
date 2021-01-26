package me.saro.kit.legacy

/**
 * java exception util
 * @author PARK Yong Seo
 */
class Exceptions {
    companion object {
        /**
         * to String the exception with stackTrace
         * @param e
         * @return
         */
        @JvmStatic
        fun toString(e: Exception?): String =
            e?.run {
                StringBuilder(512)
                    .append(e.message)
                    .append(e.stackTrace.joinToString("\n", "\n"))
                    .toString()
            } ?: ""

        /**
         * It catches "Exception" and converts it to "RuntimeException" and throws it.
         * @param run
         * @param <R>
         * @return
        </R> */
        @JvmStatic
        fun <R> runtime(run: ThrowableSupplier<R>): R =
            try {
                run.get()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        /**
         * It catches "Exception" and converts it to "RuntimeException" and throws it.
         * @param run
         */
        @JvmStatic
        fun runtime(run: ThrowableRunnable): Unit =
            try {
                run.run()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        /**
         * ignore "Exception"
         * @param run
         */
        @JvmStatic
        fun ignore(run: ThrowableRunnable): Unit =
            try {
                run.run()
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }
}
