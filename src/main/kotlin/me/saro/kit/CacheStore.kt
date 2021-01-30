package me.saro.kit

import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

/**
 * cache store
 * @author PARK Yong Seo
 */
class CacheStore<ID, T>(
    private val cacheTimeMillis: Long
) {
    private val store: MutableMap<ID, CacheWrapper<T>> = ConcurrentHashMap()

    /**
     * find by id
     * if does not exist update and return
     */
    fun find(id: ID, orElseUpdateAndGet: (ID) -> T): T = store[id]?.data ?: update(id, orElseUpdateAndGet(id))

    /**
     * find by id
     */
    fun findOrNull(id: ID): T? = store[id]?.data

    /**
     * update
     * this functions ignore cache time
     */
    fun update(id: ID, data: T): T = data.apply { store[id] = CacheWrapper(System.currentTimeMillis() + cacheTimeMillis, this) }

    /**
     * remove
     */
    fun remove(id: ID): T? = store.remove(id)?.data

    /**
     * clear all data
     */
    fun clear(): Unit = store.clear()

    /**
     * foreach key-data
     */
    fun forEach(action: BiConsumer<ID, T>) = store.forEach { (k, v) -> v.data?.also { action.accept(k, it) } }

    /**
     * all data
     */
    fun all(): Map<ID, T> = HashMap<ID, T>().also { forEach { k, v -> it[k] = v } }

    /**
     * all keys
     */
    fun keys() = ArrayList<ID>(store.size).also { forEach { k, _ -> it.add(k) } }

    private class CacheWrapper<T> (
        private val expireTimeMillis: Long,
        private val _data: T
    ) {
        val data get(): T? = if (expireTimeMillis >= System.currentTimeMillis()) _data else null
    }
}

