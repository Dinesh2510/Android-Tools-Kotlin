package com.android.tools

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.location.LocationManager
import android.os.Bundle
import com.android.tools.R
import android.os.Build
import android.content.Intent
import android.app.Activity
import android.content.pm.PackageManager
import com.android.tools.Activities.Home_A
import android.text.Html
import android.content.DialogInterface
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.common.api.ResolvableApiException
import android.content.IntentSender.SendIntentException
import android.location.Location
import android.location.LocationListener
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.tools.utils.Constant
import com.android.tools.utils.Method
import com.android.tools.utils.Utils
import java.lang.Exception

class SplashScreen : AppCompatActivity() {
    private var method: Method? = null
    private var progressBar: ProgressBar? = null
    private var isCancelled = false
    private val id = "0"
    private val title: String? = null
    private val type = ""
    private val REQUEST_LOCATION = 199
    private val REQUEST_CODE_PERMISSION = 101
    private var locationManager: LocationManager? = null

    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        method = Method(this@SplashScreen)

        // Making notification bar transparent
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        method!!.changeStatusBarColor()
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar_splash)
    }

    override fun onResume() {
        super.onResume()

        if (!Utils.isPermissionGranted(this)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                alertDialogBuilder = AlertDialog.Builder(this)
                    .setTitle("All files permission")
                    .setMessage("Due to Android 11 restrictions, this app requires all files permission")
                    .setPositiveButton("Allow") { dialogInterface, i -> takePermission() }
                    .setNegativeButton("Deny") { dialogInterface, i ->
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.app_name) + " cannot function without the permission",
                            Toast.LENGTH_LONG
                        ).show()
                        exitApp()
                    }
                    .setIcon(R.drawable.ic_appicon)

                alertDialog = alertDialogBuilder!!.create()

            } else {
                alertDialogBuilder = AlertDialog.Builder(this)
                    .setTitle("All files permission")
                    .setMessage("Please allow storage permission")
                    .setPositiveButton("Allow") { dialogInterface, i -> takePermission() }
                    .setNegativeButton("Deny") { dialogInterface, i ->
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.app_name) + " cannot function without the permission",
                            Toast.LENGTH_LONG
                        ).show()
                        exitApp()
                    }
                    .setIcon(R.drawable.ic_appicon)

                alertDialog = alertDialogBuilder!!.create()

            }

            Handler().postDelayed(object : Runnable {
                override fun run() {
                    alertDialog!!.show()
                }
            }, 4000)

        } else {
            //Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG).show()
            Log.d("", "Permission Granted")
            goToMainScreen()
        }
    }

    private fun takePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {

                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 101
                )

                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, 101)
            } catch (e: Exception) {
                e.printStackTrace()

                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 101
                )

                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 101)
            }
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 101
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0) {
            if (requestCode == 101) {
                val readExt = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (!readExt) {
                    takePermission()
                }
            }
        }
    }

    private fun goToMainScreen() {
        Handler().postDelayed(object : Runnable {
            override fun run() {
                startActivity(Intent(this@SplashScreen, Home_A::class.java))
            }
        }, 5)

    }

    private fun exitApp() {
        finish()
    }


}