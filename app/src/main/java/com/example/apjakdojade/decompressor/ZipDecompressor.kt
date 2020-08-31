package com.example.apjakdojade.decompressor

import android.util.Log
import com.example.apjakdojade.callbacks.ICallBack
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ZipDecompressor(
    private val _zipFile: String,
    private val _location: String,
    private val _callback: ICallBack?
) : IDecompressor {

    override fun decompress() {
        try {
            val fin = FileInputStream(_zipFile)
            val zin = ZipInputStream(fin)
            var ze: ZipEntry?
            while (zin.nextEntry.also { ze = it } != null) {
                Log.d(javaClass.name, "Unzipping " + ze!!.name)
                if (ze!!.isDirectory) {
                    _dirChecker(ze!!.name)
                } else {
                    val fout = FileOutputStream(_location + ze!!.name)
                    val bufout = BufferedOutputStream(fout)
                    val buffer = ByteArray(1024)
                    var read: Int
                    while (zin.read(buffer).also { read = it } != -1) {
                        bufout.write(buffer, 0, read)
                    }
                    bufout.close()
                    zin.closeEntry()
                    fout.close()
                }
            }
            zin.close()
            Log.d(javaClass.name, "Unzipping complete. path :  $_location")
            _callback?.invoke()
        } catch (e: Exception) {
            Log.e(javaClass.name, "unzip", e)
            Log.e(javaClass.name, "Unzipping failed")
        }
    }

    private fun _dirChecker(dir: String) {
        val f = File(_location + dir)
        if (!f.isDirectory) {
            f.mkdirs()
        }
    }

    init {
        _dirChecker("")
    }
}