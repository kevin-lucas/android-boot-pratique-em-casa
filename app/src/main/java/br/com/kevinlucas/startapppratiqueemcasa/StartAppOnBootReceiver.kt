package br.com.kevinlucas.startapppratiqueemcasa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartAppOnBootReceiver : BroadcastReceiver() {

    val appPackage = "com.fortram.pratiqueemcasa"
    override fun onReceive(context: Context?, intent: Intent?) {
        val it = context?.packageManager?.getLaunchIntentForPackage(appPackage)
        it?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(it)
    }
}