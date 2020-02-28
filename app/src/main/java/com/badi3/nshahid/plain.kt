package com.badi3.nshahid

import android.Manifest
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Button
import android.widget.Toast

//import com.badi3.nshahid.util.*
//import com.google.android.material.snackbar.Snackbar
//import kotlinx.android.synthetic.main.activity_main.*


import kotlinx.android.synthetic.main.activity_plain.*
import android.os.Environment.getExternalStorageDirectory



class plain : Activity() {

    companion object {
        const val PERMISSION_REQUEST_STORAGE = 0
    }
    lateinit var downloadController: DownloadController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain)



        val openShahidButton = findViewById(R.id.openShahidButton) as Button
        val updateShahidButton = findViewById(R.id.updateShahidButton) as Button

        openShahidButton.setOnClickListener {
            toast("Will Open Shahid")
            launchApp("net.mbc.shahid")

        }

        val apkUrl = "http://elsifi.com/SHAHID_v5.5.1_apkpure.com.apk"
        downloadController = DownloadController(this, apkUrl)


        updateShahidButton.setOnClickListener {
            toast("Will update Shahid")


            checkStoragePermission()


        }

    }

    private fun checkStoragePermission() {
        // Check if the storage permission has been granted
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // start downloading
            downloadController.enqueueDownload()
        } else {
            // Permission is missing and must be requested.
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

//            mainLayout.showSnackbar(
//                R.string.storage_access_required,
//                Snackbar.LENGTH_INDEFINITE, R.string.ok
//            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_STORAGE
                )
//            }
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        }
    }


    private fun launchApp(packageName: String) {
        // Get an instance of PackageManager
        val pm = applicationContext.packageManager

        // Initialize a new Intent
        val intent:Intent? = pm.getLaunchIntentForPackage(packageName)

        // Add category to intent
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)

        // If intent is not null then launch the app
        if(intent!=null){
            applicationContext.startActivity(intent)
        }else{
            toast("Shahid is not installed, please install Shahid first.")





        }
    }

    // Extension function to show toast message
    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
