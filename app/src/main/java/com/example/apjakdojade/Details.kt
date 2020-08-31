package com.example.apjakdojade

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_details.*

class Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val searchBar = findViewById(R.id.search1) as MaterialSearchBar
        searchBar.setHint("Search..")
        val stopsNames = ArrayList<String>()

        Stop.stops.forEach {
            stopsNames.add(it.stopName)
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            stopsNames
        )

        stops_listView.adapter = adapter

        stops_listView.setOnItemClickListener { parent, view, position, id ->
            val Elo = parent.getItemAtPosition(position) as String
            //Toast.makeText(this, "Clicked item : ${Elo.stopURL}",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("stopUrl", Stop.getURL(Elo))
            startActivity(intent)
        }

        stops_listView.setOnItemLongClickListener { parent, view, position, id ->
            val dbHelper = TableInfo.DataBaseHelper(applicationContext)
            val db = dbHelper.writableDatabase
            val Elo = parent.getItemAtPosition(position) as String
            val value = ContentValues()
            val curs = db.rawQuery("SELECT * FROM ${TableInfo.TABLE_NAME} WHERE ${TableInfo.TABLE_COLUMN_PRZYSTANEK}='$Elo'",null)

            if(!curs.moveToFirst()){
                value.put("przystanek",Elo)
                db.insertOrThrow(TableInfo.TABLE_NAME,null,value)
                Toast.makeText(this, "Dodano : $Elo do ulubionych!",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Przystanek : $Elo juz w ulubionych!",Toast.LENGTH_SHORT).show()
            }


            db.close()
            true
        }


        //SEARCHBAR TEXT CHANGE LISTENER
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //SEARCH FILTER
                adapter.filter.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })



        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.menu_button -> {
                    showPopup(view)
                }
            }
        }

        menu_button.setOnClickListener(clickListener)
    }

    private fun showPopup(view: View) {
        var popup: PopupMenu?
        popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.menu_favorite -> {
                    Toast.makeText(this@Details, item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Favorite::class.java)
                    startActivity(intent)
                }
                R.id.menu_localization -> {
                    Toast.makeText(this@Details, item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Map::class.java)
                    startActivity(intent)
                }
                R.id.menu_stops -> {
                    Toast.makeText(this@Details, item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Details::class.java)
                    startActivity(intent)
                }
                R.id.qr_code -> {
                    Toast.makeText(this@Details, item.title, Toast.LENGTH_SHORT).show();
                    val intent = Intent(this, QrCode::class.java)
                    startActivity(intent)
                }

            }

            true
        })

        popup.show()
    }
}
