package me.saro.kit.functions;

import java.util.function.BiConsumer;

/**
 * Throwable <code>BiConsumer</code>
 * 
 * @param <T>
 * input parameter type T
 * 
 * @param <U>
 * input parameter type U
 * 
 * @see
 * java.util.function.Consumer
 * 
 * @author
 * PARK Yong Seo
 * 
 * @since
 * 1.0.0
 */
@FunctionalInterface
public interface ThrowableBiConsumer<T, U> {
    /**
     * @see
     * java.util.function.Consumer
     * 
     * @param t
     * input parameter type T
     * 
     * @param u
     * input parameter type U
     * 
     * @throws Exception
     */
    void accept(T t, U u) throws Exception;
    
    /**
     * throws Exception lambda to throws RuntimeException lambda
     * @param biConsumer
     * @return
     */
    public static <T, U> BiConsumer<T, U> wrap(ThrowableBiConsumer<T, U> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
