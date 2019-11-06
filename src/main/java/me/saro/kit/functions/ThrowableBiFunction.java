package me.saro.kit.functions;

import java.util.function.BiFunction;

/**
 * Throwable <code>BiFunction</code>
 * 
 * @param <T>
 * input parameter type T
 * 
 * @param <U>
 * input parameter type U
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
public interface ThrowableBiFunction<T, U, R> {
    /**
     * @see
     * java.util.function.Function
     * 
     * @param t
     * input parameter type T
     * 
     * @param u
     * input parameter type U
     * 
     * @return
     * output return
     * 
     * @throws Exception
     */
    R apply(T t, U u) throws Exception;
    
    /**
     * throws Exception lambda to throws RuntimeException lambda
     * @param biFunction
     * @return
     */
    public static <T, U, R> BiFunction<T, U, R> wrap(ThrowableBiFunction<T, U, R> biFunction) {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
