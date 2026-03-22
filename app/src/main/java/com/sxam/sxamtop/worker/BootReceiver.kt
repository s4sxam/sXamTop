package com.sxam.sxamtop.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            NewsRefreshWorker.schedule(context) // #3 Reschedule on boot
        }
    }
}