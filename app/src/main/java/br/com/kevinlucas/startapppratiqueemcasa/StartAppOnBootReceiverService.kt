package br.com.kevinlucas.startapppratiqueemcasa

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class StartAppOnBootReceiverService : Service() {

    private lateinit var rc: StartAppOnBootReceiver

    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    override fun onCreate() {
        super.onCreate()

        registerScreenOffReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(rc)
    }

    private fun registerScreenOffReceiver(){
        val filter = IntentFilter()
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SHUTDOWN)

        rc = StartAppOnBootReceiver()

        registerReceiver(rc, filter)
    }
}