package me.saro.kit;

import me.saro.kit.functions.ThrowableFunction;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * thread util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Threads {
    /**
     * execute all threads
     * <b>WARNING : </b>this method does not shutdown to ExecutorService instance
     * @param executorService
     * @param list
     * @param map
     * @return
     * map result list
     */
    public static <T, R> List<R> executeAllThreads(ExecutorService executorService, List<T> list, ThrowableFunction<T, R> map) {
        try {
            return executorService
                    .invokeAll(list.parallelStream().<Callable<R>>map(e -> () -> map.apply(e)).collect(Collectors.toList()))
                    .parallelStream()
                    .map(ThrowableFunction.wrap(x -> x.get()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * execute all threads
     * @param nThreads
     * @param list
     * @param map
     * @return
     * map result list
     */
    public static <T, R> List<R> executeAllThreads(int nThreads, List<T> list, ThrowableFunction<T, R> map) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<R> rv = executeAllThreads(executorService, list, map);
        executorService.shutdown();
        return rv;
    }

    /**
     * kill thread without exception
     * @param thread
     */
    public static void kill(Thread thread) {
        if (thread != null) {
            try {
                if (thread.isInterrupted()) {
                    thread.interrupt();
                }
            } catch (Exception e) {
            }
        }
    }
}
