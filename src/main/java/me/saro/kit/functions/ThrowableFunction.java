package me.saro.kit.functions;

import java.util.function.Function;

/**
 * Throwable <code>Function</code>
 * 
 * @param <T>
 * input parameter type T
 * 
 * @param <R> 
 * output return type
 * 
 * @see
 * java.util.function.Function
 * 
 * @author
 * PARK Yong Seo
 * 
 * @since
 * 1.0.0
 */
@FunctionalInterface
public interface ThrowableFunction<T, R> {
    /**
     * @see
     * java.util.function.Function
     * 
     * @param t
     * input parameter type T
     * 
     * @return
     * output return
     * 
     * @throws Exception
     */
    R apply(T t) throws Exception;
    
    /**
     * throws Exception lambda to throws RuntimeException lambda
     * @param function
     * @return
     */
    public static <T, R> Function<T, R> wrap(ThrowableFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
