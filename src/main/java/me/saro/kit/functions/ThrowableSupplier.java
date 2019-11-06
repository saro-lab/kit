package me.saro.kit.functions;

import java.util.function.Supplier;

/**
 * Throwable <code>Supplier</code>
 * 
 * @param <R> 
 * output return type
 * 
 * @see
 * java.util.function.Supplier
 * 
 * @author
 * PARK Yong Seo
 * 
 * @since
 * 1.0.0
 */
@FunctionalInterface
public interface ThrowableSupplier<R> {
    /**
     * @see
     * java.util.function.Supplier
     * 
     * @return
     * output return
     * 
     * @throws
     * Exception
     */
    R get() throws Exception;
    
    /**
     * throws Exception lambda to throws RuntimeException lambda
     * @param supplier
     * @return
     */
    public static <R> Supplier<R> wrap(ThrowableSupplier<R> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
