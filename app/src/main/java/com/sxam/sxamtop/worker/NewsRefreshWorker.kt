package com.sxam.sxamtop.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.sxam.sxamtop.data.remote.RetrofitInstances
import com.sxam.sxamtop.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class NewsRefreshWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val settings = SettingsDataStore(context)
            val apiKey = settings.newsApiKeyFlow.first()

            var count = 0
            val rssItems = RetrofitInstances.rssApi.getGoogleNews().items
            count += rssItems.size

            if (apiKey.isNotBlank()) {
                // Fetching via GNews endpoint
                val newsItems = RetrofitInstances.newsApi.getTopHeadlines(apikey = apiKey)
                count += newsItems.articles.size
            }

            if (count > 0) showNotification(count)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(count: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "news_refresh"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "News Updates", NotificationManager.IMPORTANCE_LOW)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("sXamTop News")
            .setContentText("$count new articles available")
            .setAutoCancel(true)
            .build()

        manager.notify(1001, notification)
    }

    companion object {
        fun schedule(context: Context) {
            // SAFE INTERVAL FOR GNEWS (6 Hours)
            val request = PeriodicWorkRequestBuilder<NewsRefreshWorker>(6, TimeUnit.HOURS)
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "news_refresh",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}