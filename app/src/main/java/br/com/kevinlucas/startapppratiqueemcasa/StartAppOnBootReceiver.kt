package br.com.kevinlucas.startapppratiqueemcasa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StartAppOnBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent?.action)) {
            val it =
                context?.packageManager?.getLaunchIntentForPackage("com.fortram.pratiqueemcasa")
            it?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(it)
        }
    }
}