package com.example.apjakdojade.downloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.apjakdojade.ApJakDojadeInfo
import com.example.apjakdojade.callbacks.DecompressorCallBack
import com.example.apjakdojade.decompressor.ZipDecompressor

class DownloadBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            Log.i(javaClass.name, "Download action has been completed.")

            val callBack =
                DecompressorCallBack()

            ZipDecompressor(
                ApJakDojadeInfo.zipFileLocation,
                ApJakDojadeInfo.unzipLocation,
                callBack
            ).decompress()
        }
    }
}