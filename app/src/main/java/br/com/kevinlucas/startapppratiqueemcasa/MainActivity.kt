package br.com.kevinlucas.startapppratiqueemcasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.BroadcastReceiver
import android.content.Context

import android.content.Intent

import android.content.IntentFilter
import android.content.pm.PackageManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val it = packageManager?.getLaunchIntentForPackage("com.fortram.pratiqueemcasa")
        it?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(it)
    }
}