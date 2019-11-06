package me.saro.kit.functions;

import java.util.function.Consumer;

/**
 * Throwable <code>Consumer</code>
 * 
 * @param <T>
 * input parameter type T
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
public interface ThrowableConsumer<T> {
    /**
     * @see
     * java.util.function.Consumer
     * 
     * @param t
     * input parameter type T
     * 
     * @throws Exception
     */
    void accept(T t) throws Exception;
    
    /**
     * throws Exception lambda to throws RuntimeException lambda
     * @param consumer
     * @return
     */
    public static <T> Consumer<T> wrap(ThrowableConsumer<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
