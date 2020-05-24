package com.example.emojikeyboard.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private val backgroundExecutors: ExecutorService by lazy {
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1)
}

private val mainHandler by lazy {
    Handler(Looper.getMainLooper())
}

@JvmOverloads
fun runOnMainThread(block: () -> Unit, delay: Long = 0) {
    runOnMainThread(Runnable { block() }, delay)
}

@JvmOverloads
fun runOnMainThread(runnable: Runnable, delay: Long = 0) {
    mainHandler.postDelayed(runnable, delay)
}

fun runOnWorkThread(block: () -> Unit) {
    backgroundExecutors.execute { block.invoke() }
}
