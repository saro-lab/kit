package me.saro.kit.functions;

import java.util.function.Predicate;

/**
 * Throwable <code>Predicate</code>
 * 
 * @param <T>
 * input parameter type T
 * 
 * @see
 * java.util.function.Predicate
 * 
 * @author
 * PARK Yong Seo
 * 
 * @since
 * 1.0.0
 */
@FunctionalInterface
public interface ThrowablePredicate<T> {
    /**
     * @see
     * java.util.function.Predicate
     * 
     * @param t
     * input parameter type T
     * 
     * @return
     * test return
     * 
     * @throws Exception
     */
    boolean test(T t) throws Exception;
    
    /**
     * throws Exception lambda to throws RuntimeException lambda
     * @param throwablePredicate
     * @return
     */
    public static <T> Predicate<T> wrap(ThrowablePredicate<T> throwablePredicate) {
        return t -> {
            try {
                return throwablePredicate.test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
