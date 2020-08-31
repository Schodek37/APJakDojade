package com.example.apjakdojade

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.apjakdojade.downloader.Downloader
import java.io.File

class ApiService : IntentService("ApiService") {

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onHandleIntent(intent: Intent?) {
        ApJakDojadeInfo.fileLocation = getExternalFilesDir(null).toString() + "/File/"

        val stopsFile = File(ApJakDojadeInfo.stopsLocation)

        if (stopsFile.exists()) {
            Log.d(this.javaClass.name, "Stops detected, nothing to download.")
            StopsReader(ApJakDojadeInfo.stopsLocation).read()
        } else {
            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            Downloader(
                applicationContext,
                downloadManager
            ).downloadZditmZip()
        }
    }
}