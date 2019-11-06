package me.saro.kit.functions;

/**
 * Throwable <code>Runnable</code>
 * 
 * @see
 * java.lang.Runnable
 * 
 * @author
 * PARK Yong Seo
 * 
 * @since
 * 1.0.0
 */
@FunctionalInterface
public interface ThrowableRunnable {
    /**
     * @see
     * java.lang.Runnable
     * 
     * @throws
     * Exception
     */
    void run() throws Exception;
    
    /**
     * throws Exception lambda to throws RuntimeException lambda
     * @param runnable
     * @return
     */
    public static Runnable wrap(ThrowableRunnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
