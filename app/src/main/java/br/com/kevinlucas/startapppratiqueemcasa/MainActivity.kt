package br.com.kevinlucas.startapppratiqueemcasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context

import android.content.Intent
import android.content.IntentFilter

import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textInstalled: TextView
    private lateinit var imageInstalled: ImageView
    //private lateinit var textStoreInstalled: TextView

    private lateinit var textInternet: TextView
    private lateinit var imageInternet: ImageView

    private lateinit var textStatusOk: TextView

    val appPackage = "com.fortram.pratiqueemcasa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInstalled = findViewById(R.id.tv_installed)
        imageInstalled = findViewById(R.id.iv_alert)
        //textStoreInstalled = findViewById(R.id.tv_store)

        textInternet = findViewById(R.id.tv_internet)
        imageInternet = findViewById(R.id.iv_internet)

        textStatusOk = findViewById(R.id.text_success)

        textStatusOk.visibility = View.INVISIBLE

        setupInternetLabels(false)
        setupAppLabels(false)
    }

    override fun onResume() {
        super.onResume()

        if (isInternetAvailable() && isAppInstalled(this, appPackage)) {
            setupStatusOk()
            startServiceRc()
        } else {
            stopServiceRc()
        }

    }

    private fun startServiceRc() {
        val service = Intent(this, StartAppOnBootReceiverService::class.java)
        startService(service)
    }

    private fun stopServiceRc() {
        val service = Intent(this, StartAppOnBootReceiverService::class.java)
        stopService(service)
    }

    private fun setupStatusOk() {
        textStatusOk.visibility = View.VISIBLE
        textStatusOk.text = getString(R.string.status_ok)
    }


    private fun setupInternetLabels(isVisible: Boolean) {
        if (isVisible) {
            imageInternet.visibility = View.VISIBLE
            textInternet.visibility = View.VISIBLE
            textInternet.text =
                getString(R.string.internet_status_disconect)
        } else {
            imageInternet.visibility = View.INVISIBLE
            textInternet.visibility = View.INVISIBLE
        }
    }

    private fun setupAppLabels(isVisible: Boolean) {
        if (isVisible) {
            textInstalled.visibility = View.VISIBLE
            imageInstalled.visibility = View.VISIBLE

            textInstalled.text =
                getString(R.string.app_not_installed)

        } else {
            textInstalled.visibility = View.INVISIBLE
            imageInstalled.visibility = View.INVISIBLE
        }

    }

    private fun openPratiqueApp() {
        val it = packageManager?.getLaunchIntentForPackage(appPackage)
        it?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(it)
    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        setupAppLabels(isVisible = true)
        return false
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {

            if (!(it != null && it.isConnected)) {
                setupInternetLabels(isVisible = true)
            }

            return it != null && it.isConnected
        }
    }
}