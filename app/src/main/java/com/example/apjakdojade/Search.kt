package com.example.apjakdojade

import kotlin.Int.Companion.MAX_VALUE

// This will be used for  issues #2
class Search() {

    fun search(editText: String) {
    }

    fun find(name: String): Stop? {
        var minimu = MAX_VALUE;
        var minimu_stop: Stop = Stop.stops[1];
        var temp: Int

        for (stop1 in Stop.stops) {
            temp = LevenshteinDistance(stop1.stopName, name);
            if (minimu > temp) {
                if (temp == 0) return stop1;
                else {
                    minimu = temp;
                    minimu_stop = stop1;
                }
            }
        }
        return minimu_stop;
    }

    private fun LevenshteinDistance(stop: String?, search: String): Int {
        var i: Int
        var j: Int
        val len1: Int
        val len2: Int
        var cost: Double
        len1 = stop!!.length
        len2 = search.length
        val array = Array(len1 + 1) { DoubleArray(len2 + 1) }
        i = 0
        while (i <= len1) {
            array[i][0] = i.toDouble()
            i++
        }
        j = 0
        while (j <= len2) {
            array[0][j] = j.toDouble()
            j++
        }
        i = 1
        while (i < len1 + 1) {
            j = 1
            while (j < len2 + 1) {
                cost = if (stop[i - 1] == search[j - 1]) {
                    0.0
                } else {
                    1.0
                }
                array[i][j] = Math.min(
                    Math.min(array[i - 1][j] + 1, array[i][j - 1] + 1),
                    array[i - 1][j - 1] + cost
                )
                j++
            }
            i++
        }
        return array[i - 1][j - 1].toInt()
    }
}
