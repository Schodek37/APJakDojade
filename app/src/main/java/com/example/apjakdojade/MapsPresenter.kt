package com.example.apjakdojade

import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsPresenter(
    private val view: Maps,
    mapFragment: SupportMapFragment
) : OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var enable = false
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        if (enable) enableLocation()
        for (stop in Stop.stops) {
            mMap.addMarker(
                MarkerOptions().position(LatLng(stop.stopLat, stop.stopLon)).title(stop.stopName)
                    .snippet(stop.stopURL).icon(BitmapDescriptorFactory.fromResource(R.raw.bus))
            )
            mMap.setOnInfoWindowClickListener { marker: Marker -> view.onInfoWindowClick(marker) }
        }
        try {
            val stop = Stop.stops[1]
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(stop.stopLat, stop.stopLon),
                    14f
                )
            )

        } catch (e: Exception) {
            Log.e(javaClass.name, "empty Stop", e)
        }
    }

    fun enableLocation() {
        mMap.isMyLocationEnabled = true;
        enable = true
    }

    fun setEnableLocation() {
        enable = true
    }

    init {
        mapFragment.getMapAsync(this)
    }
}