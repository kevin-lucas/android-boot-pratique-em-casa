package br.com.kevinlucas.startapppratiqueemcasa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StartAppOnBootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = Intent(context, MainActivity::class.java)
        context?.startActivity(service)

        //startServiceRc(context)
    }

    private fun startServiceRc(context: Context?) {
        val service = Intent(context, StartAppOnBootReceiverService::class.java)
        context?.startService(service)
    }
}