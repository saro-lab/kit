package me.saro.kit;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


/**
 * @author PARK Yong Seo
 * @param <T>
 * @param <ID>
 * @Since 1.0.0
 */
public class CacheStore<ID, T> {

    private Map<ID, CacheWrapper<T>> store = new ConcurrentHashMap<>();

    final private long cacheTimeMillis;

    public CacheStore(long cacheTimeMillis) {
        this.cacheTimeMillis = cacheTimeMillis;
    }

    /**
     * get
     * @param id id
     * @param orElse or else value
     * @return
     */
    public synchronized T get(ID id, Function<ID, T> orElse) {
        return Optional
                .ofNullable(store.get(id))
                .map(CacheWrapper::get)
                .orElseGet(() -> {
                    final var data = orElse.apply(id);
                    store.put(id, new CacheWrapper<>(cacheTimeMillis, data));
                    return data;
                });
    }

    /**
     * get after forced update
     * @param id id
     * @param value update value
     * @return
     */
    public T getAfterForcedUpdate(ID id, T value) {
        store.put(id, new CacheWrapper<>(cacheTimeMillis, value));
        return value;
    }

    /**
     * remove
     * @param id id
     */
    public void remove(ID id) {
        store.remove(id);
    }

    /**
     * clear
     */
    public void clear() {
        store.clear();
    }

    public static class CacheWrapper<T> {

        final long expireCacheTimeMillis;
        final T data;

        public CacheWrapper(long cacheTimeMillis, T data) {
            this.expireCacheTimeMillis = System.currentTimeMillis() + cacheTimeMillis;
            this.data = data;
        }

        public T get() {
            return expireCacheTimeMillis >= System.currentTimeMillis() ? data : null;
        }
    }
}
