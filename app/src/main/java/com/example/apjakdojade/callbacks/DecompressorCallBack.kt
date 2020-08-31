package com.example.apjakdojade.callbacks

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.apjakdojade.ApJakDojadeInfo
import com.example.apjakdojade.StopsReader

class DecompressorCallBack : ICallBack {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun invoke() {
        StopsReader(ApJakDojadeInfo.stopsLocation).read()
    }
}