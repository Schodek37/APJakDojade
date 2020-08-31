package com.example.apjakdojade

import com.google.android.gms.maps.model.Marker

interface Maps {
    fun showToast(title: String)
    fun onInfoWindowClick(marker: Marker)
}