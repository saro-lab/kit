package me.saro.kit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


/**
 * @author PARK Yong Seo
 * @param <T>
 * @param <ID>
 * @Since 1.0.0
 */
public class CacheStore<ID, T> {

    private Map<ID, CacheWrapper<T>> store = new HashMap<>();

    final private long cacheTimeMillis;

    public CacheStore(long cacheTimeMillis) {
        this.cacheTimeMillis = cacheTimeMillis;
    }

    public synchronized T get(ID id, Function<ID, T> orElse) {
        return  Optional
                .ofNullable(store.get(id))
                .map(CacheWrapper::get)
                .orElseGet(() -> {
                    var v = orElse.apply(id);
                    store.put(id, new CacheWrapper<>(cacheTimeMillis, v));
                    return v;
                });
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
