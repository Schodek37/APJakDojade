package com.example.apjakdojade.downloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import java.util.*

class Downloader(
    private val applicationContext: Context,
    private val downloadManager: DownloadManager
) {

    val DOWNLOAD_URL = "https://transitfeeds.com/p/zditm-szczecin/587/latest/download"
    val FILE_PATH = "/File"
    val FILE_NAME = "GTFS.zip"

    fun downloadZditmZip() {
        val request = DownloadManager.Request(Uri.parse(DOWNLOAD_URL))

        request.setDescription("Latest GTFS data")
        request.setTitle("gtfs.zip")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
        request.setDestinationInExternalFilesDir(applicationContext, FILE_PATH, FILE_NAME)

        Objects.requireNonNull(downloadManager).enqueue(request)
    }
}