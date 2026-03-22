package com.sxam.sxamtop.worker
// ... imports
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker // #8 DI for Worker
class NewsRefreshWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: NewsRepository,
    private val settings: SettingsDataStore
) : CoroutineWorker(context, workerParams) {

    // ... doWork logic utilizing injected repository

    companion object {
        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<NewsRefreshWorker>(15, TimeUnit.MINUTES) // #7 Fixed to 15 mins
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork("news_refresh", ExistingPeriodicWorkPolicy.KEEP, request)
        }
    }
}