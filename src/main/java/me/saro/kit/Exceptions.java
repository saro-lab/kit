package me.saro.kit;

import me.saro.kit.functions.ThrowableRunnable;
import me.saro.kit.functions.ThrowableSupplier;

public class Exceptions {
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

    public static <R> R runtime(ThrowableSupplier<R> run) {
        try {
            return run.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void runtime(ThrowableRunnable run) {
        try {
            run.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
