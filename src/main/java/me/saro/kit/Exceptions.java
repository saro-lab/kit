package me.saro.kit;

import me.saro.kit.functions.ThrowableRunnable;
import me.saro.kit.functions.ThrowableSupplier;

/**
 * Exception util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Exceptions {

    /**
     * to String the exception with stackTrace
     * @param e
     * @return
     */
    public static String toString(Exception e) {
        if (e == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(512);
        sb.append(e.getMessage());
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            sb.append("\r\n").append(stackTraceElement.toString());
        }
        return sb.toString();
    }

    /**
     * It catches "Exception" and converts it to "RuntimeException" and throws it.
     * @param run
     * @param <R>
     * @return
     */
    public static <R> R runtime(ThrowableSupplier<R> run) {
        try {
            return run.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It catches "Exception" and converts it to "RuntimeException" and throws it.
     * @param run
     */
    public static void runtime(ThrowableRunnable run) {
        try {
            run.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ignore "Exception"
     * @param run
     */
    public static void ignore(ThrowableRunnable run) {
        try {
            run.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
