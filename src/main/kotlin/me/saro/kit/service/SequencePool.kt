package me.saro.kit.service

// test

class SequencePool<T>(
    private val minIssuedSequence: Int,
    private val maxIssuedSequence: Int,
) {
    private val lock = Any()
    private val store: ArrayDeque<T> = ArrayDeque(minIssuedSequence)

    private var nowIssuedSize = minIssuedSequence
    private var lastIssuedTime = 0L

    fun get(newIssue: (needSequenceSize: Int) -> List<T>): T {
//        synchronized(lock) {
//            if (store.isEmpty()) {
//                val newSequences = newIssue(nowIssuedSize)
//                if (newSequences.size != nowIssuedSize) {
//                    throw IllegalStateException("newIssue return size is not equal to nowIssuedSize")
//                }
//                store.addAll(newSequences)
//                lastIssuedTime = System.currentTimeMillis()
//            }
//            return store.removeFirst()
//        }
//        store.removeLastOrNull()
        TODO()
    }
}