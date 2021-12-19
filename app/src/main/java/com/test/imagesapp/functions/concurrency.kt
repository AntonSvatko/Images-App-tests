package com.test.imagesapp.functions

import kotlinx.coroutines.*

suspend inline fun <T> List<T>.parallelMap(crossinline block: suspend (T) -> T): List<T> =
    withContext(CoroutineName("ParallelMap")) {
        map {
            async {
                block(it)
            }
        }.awaitAll()
    }

inline fun createCoroutineHandler(crossinline onError: (Throwable?) -> Unit) =
    CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }