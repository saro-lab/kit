package me.saro.kit

import java.util.*

/**
 * @author PARK Yong Seo
 * @param <ID>
 * @param <T>
</ID></T> */
class CacheStore<ID, T>(
    private val cacheTimeMillis: Long
) {
    private val store: MutableMap<ID, CacheWrapper<T>> = HashMap()

    /**
     * get
     * @param id id
     * @param orElse or else data
     * @return
     */
    fun get(id: ID, orElse: (ID) -> T): T = synchronized(store) { store[id]?.data ?: put(id, orElse(id)) }

    /**
     * get after force update
     */
    fun getAfterForcedUpdate(id: ID, data: T): T = synchronized(store) { put(id, data) }

    /**
     * remove
     * @param id id
     */
    fun remove(id: ID): Unit = synchronized(store) { store.remove(id) }

    /**
     * clear all data
     */
    fun clear(): Unit = synchronized(store) { store.clear() }

    private fun put(id: ID, data: T) = data.apply { store[id] = CacheWrapper(cacheTimeMillis + System.currentTimeMillis(), this) }
}

private class CacheWrapper<T>(
    private val expireTimeMillis: Long,
    private val _data: T
) {
    val data get(): T? = if (expireTimeMillis >= System.currentTimeMillis()) _data else null
}
