package com.example.apjakdojade

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class StopsReader(private val stopsFilePath: String) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun read() {
        val cvsSplitBy = ","
        try {
            Log.d(javaClass.name, "Reading stops...")

            val reader = BufferedReader(FileReader(stopsFilePath))

            for (line in reader.lines().skip(1)) {
                val stop =
                    line.split((cvsSplitBy).toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                Stop.stops.add(
                    Stop(
                        stop[0],
                        stop[2].substring(1, stop[2].length - 1),
                        stop[4].toDouble(),
                        stop[5].toDouble(),
                        "Https" + stop[6].substring(5, stop[6].length - 1)
                    )
                )
                mHint.manyHints.add(stop[2].substring(1, stop[2].length - 1))
            }

            Log.d(javaClass.name, "Read stops successfully.")
            ApJakDojadeInfo.haveStopsBeenRead = true
        } catch (e: IOException) {
            Log.e(javaClass.name, "read", e)
        }
    }

}