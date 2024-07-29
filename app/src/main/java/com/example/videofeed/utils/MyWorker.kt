package com.example.videofeed.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Add your background processing logic here
        // This example logs a message
        return try {
            // Simulate some background work
            Thread.sleep(2000)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}