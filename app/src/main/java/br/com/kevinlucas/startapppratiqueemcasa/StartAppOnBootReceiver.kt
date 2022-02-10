package br.com.kevinlucas.startapppratiqueemcasa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StartAppOnBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_USER_PRESENT == intent?.action ||
            Intent.ACTION_SCREEN_ON == intent?.action
        ) {
            val intent = Intent(context, MainActivity::class.java)
            context?.startActivity(intent)
        }
    }

}