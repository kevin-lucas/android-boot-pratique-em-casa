package br.com.kevinlucas.startapppratiqueemcasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context

import android.content.Intent
import android.content.IntentFilter

import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textInstalled: TextView
    private lateinit var imageInstalled: ImageView
    private lateinit var textLink: TextView

    private lateinit var textInternet: TextView
    private lateinit var imageInternet: ImageView

    private lateinit var textStatusOk: TextView

    val appPackage = "com.fortram.pratiqueemcasa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInstalled = findViewById(R.id.tv_installed)
        imageInstalled = findViewById(R.id.iv_alert)
        textLink = findViewById(R.id.tv_link)
        textInternet = findViewById(R.id.tv_internet)
        imageInternet = findViewById(R.id.iv_internet)
        textStatusOk = findViewById(R.id.text_success)

        // sets visibility
        textStatusOk.visibility = View.INVISIBLE
        textLink.visibility = View.INVISIBLE

        setupInternetLabels(false)
        setupAppLabels(false)
    }

    override fun onResume() {
        super.onResume()

        if (!isInternetAvailable()){
            setupInternetLabels(isVisible = true)
        }

        if (!isAppInstalled(this, appPackage)){
            setupAppLabels(isVisible = true)
        }

        if (!checkSDKVersionUp10()) {
            if (isInternetAvailable() && isAppInstalled(this, appPackage)) {
                setupStatusOk()
                startServiceRc()
            } else {
                stopServiceRc()
            }
        } else {
            textStatusOk.visibility = View.VISIBLE
            textStatusOk.text = getString(R.string.incompatible)
        }

    }

    private fun checkSDKVersionUp10() = Build.VERSION.SDK_INT > 28


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

            textLink.visibility = View.VISIBLE
            textLink.text = getString(R.string.see_store)
            textLink.setOnClickListener {
                val url =
                    "https://play.google.com/store/apps/details?id=com.fortram.pratiqueemcasa"
                val it = Intent(Intent.ACTION_VIEW)
                it.data = Uri.parse(url)
                startActivity(it)
            }

            textInstalled.text =
                getString(R.string.app_not_installed)

        } else {
            textLink.visibility = View.INVISIBLE
            textInstalled.visibility = View.INVISIBLE
            imageInstalled.visibility = View.INVISIBLE
        }

    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {

            return it != null && it.isConnected
        }
    }
}