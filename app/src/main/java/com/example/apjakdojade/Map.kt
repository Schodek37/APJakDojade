package com.example.apjakdojade

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*

class Map : FragmentActivity(), Maps {
    private var presenter: MapsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        presenter = MapsPresenter(this, mapFragment)

        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.menu_button -> {
                    showPopup(view)
                }
            }
        }

        menu_button.setOnClickListener(clickListener)
    }

    override fun onResume() {
        super.onResume()
        perrmision()
    }


    override fun onInfoWindowClick(marker: Marker) {
        val newindow = Intent(this, WebActivity::class.java)
        newindow.putExtra("stopUrl", marker.snippet)
        startActivity(newindow)
    }

    private fun perrmision() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            presenter?.setEnableLocation()
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (grantResults[0] != -1) {
            presenter?.enableLocation()
        }
    }

    override fun showToast(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_LONG).show()
    }

    private fun showPopup(view: View) {
        var popup: PopupMenu?;
        popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.menu_favorite -> {
                    Toast.makeText(this@Map, item.title, Toast.LENGTH_SHORT).show();
                    val intent = Intent(this, Favorite::class.java)
                    startActivity(intent)
                }
                R.id.menu_localization -> {
                    Toast.makeText(this@Map, item.title, Toast.LENGTH_SHORT).show();
                    val intent = Intent(this, Map::class.java)
                    startActivity(intent)
                }
                R.id.menu_stops -> {
                    Toast.makeText(this@Map, item.title, Toast.LENGTH_SHORT).show();
                    val intent = Intent(this, Details::class.java)
                    startActivity(intent)
                }
                R.id.qr_code -> {
                    Toast.makeText(this@Map, item.title, Toast.LENGTH_SHORT).show();
                    val intent = Intent(this, QrCode::class.java)
                    startActivity(intent)
                }

            }

            true
        })

        popup.show()
    }
}
