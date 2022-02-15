package br.com.kevinlucas.startapppratiqueemcasa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StartAppOnBootCompleteReceiver : BroadcastReceiver() {

    val appPackage = "com.fortram.pratiqueemcasa"

    override fun onReceive(context: Context?, intent: Intent?) {

        startActivityMain(context)

        val it = context?.packageManager?.getLaunchIntentForPackage(appPackage)
        it?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(it)
    }

    private fun startActivityMain(context: Context?) {
        val service = Intent(context, MainActivity::class.java)
        context?.startActivity(service)
    }

    private fun startServiceRc(context: Context?) {
//        val service = Intent(context, StartAppOnBootReceiverService::class.java)
//        context?.startService(service)
    }
}