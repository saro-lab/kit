package me.saro.kit

import java.util.function.BiConsumer
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier

fun interface ThrowableFunction<T, R> {
    @Throws(Exception::class) fun apply(t: T): R

    companion object {
        @JvmStatic fun <T, R>wrap(fn: ThrowableFunction<T, R>): Function<T, R> =
            Function { t ->  try { return@Function fn.apply(t) } catch(e: Exception) { throw RuntimeException(e) } }
    }
}
fun interface ThrowableBiFunction<T, U, R> {
    @Throws(Exception::class) fun apply(t: T, u: U): R

    companion object {
        @JvmStatic fun <T, U, R>wrap(fn: ThrowableBiFunction<T, U, R>): BiFunction<T, U, R> =
            BiFunction { t, u ->  try { return@BiFunction fn.apply(t, u) } catch(e: Exception) { throw RuntimeException(e) } }
    }
}
fun interface ThrowableTriFunction<T, U, V, R> {
    @Throws(Exception::class) fun apply(t: T, u: U, v: V): R
}

fun interface ThrowableConsumer<T> {
    @Throws(Exception::class) fun accept(t: T)

    companion object {
        @JvmStatic fun <T>wrap(fn: ThrowableConsumer<T>): Consumer<T> =
            Consumer { t ->  try { return@Consumer fn.accept(t) } catch(e: Exception) { throw RuntimeException(e) } }
    }
}
fun interface ThrowableBiConsumer<T, U> {
    @Throws(Exception::class) fun accept(t: T, u: U)

    companion object {
        @JvmStatic fun <T, U, R>wrap(fn: ThrowableBiConsumer<T, U>): BiConsumer<T, U> =
            BiConsumer { t, u ->  try { return@BiConsumer fn.accept(t, u) } catch(e: Exception) { throw RuntimeException(e) } }
    }
}
fun interface ThrowableTriConsumer<T, U, V> {
    @Throws(Exception::class) fun accept(t: T, u: U, v: V)
}

fun interface ThrowableSupplier<R> {
    @Throws(Exception::class) fun get(): R

    companion object {
        @JvmStatic fun <R>wrap(fn: ThrowableSupplier<R>): Supplier<R> =
            Supplier { try { return@Supplier fn.get() } catch(e: Exception) { throw RuntimeException(e) } }
    }
}

fun interface ThrowableRunnable {
    @Throws(Exception::class) fun run(): Unit

    companion object {
        @JvmStatic fun wrap(fn: ThrowableRunnable): Runnable =
            Runnable { try { return@Runnable fn.run() } catch(e: Exception) { throw RuntimeException(e) } }
    }
}

fun interface ThrowablePredicate<T> {
    @Throws(Exception::class) fun test(t: T): Boolean

    companion object {
        @JvmStatic fun <T>wrap(fn: ThrowablePredicate<T>): Predicate<T> =
            Predicate { t -> try { return@Predicate fn.test(t) } catch(e: Exception) { throw RuntimeException(e) } }
    }
}
