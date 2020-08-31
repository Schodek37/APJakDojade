package com.example.apjakdojade


import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.menu_button


class Favorite : AppCompatActivity() {


    var target : ArrayList<String>? = null
    var adapter : SimpleCursorAdapter?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val searchBar = findViewById(R.id.search2) as MaterialSearchBar
        searchBar.setHint("Search..")


        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.menu_button -> {
                    showPopup(view)
                }
            }
        }


        menu_button.setOnClickListener(clickListener)
        val dbHelper = TableInfo.DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val c = db.rawQuery("Select * from ${TableInfo.TABLE_NAME}",null)


        this.adapter = SimpleCursorAdapter(this,
        android.R.layout.simple_list_item_2,
        c, arrayOf<String>("_id","przystanek"),
            intArrayOf(android.R.id.text1,android.R.id.text1),
            SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE)

        val listView: ListView = findViewById(R.id.list_view_favs) as ListView
         listView.setAdapter(adapter)
        db.close()



        list_view_favs.setOnItemClickListener { parent, view, position, id ->
            val Elo = parent.getItemAtPosition(position) as Cursor
            var nazwa = Elo.getString(Elo.getColumnIndex("przystanek")).toString()
            //Toast.makeText(this, "Clicked item : ${Elo.stopURL}",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("stopUrl", Stop.getURL(nazwa))
            startActivity(intent)
        }

        list_view_favs.setOnItemLongClickListener { parent, view, position, id ->
            val dbHelper = TableInfo.DataBaseHelper(applicationContext)
            val db = dbHelper.writableDatabase
            val Elo = parent.getItemAtPosition(position) as Cursor
            var nazwa = Elo.getString(Elo.getColumnIndex("przystanek")).toString()
            db.delete(TableInfo.TABLE_NAME,"przystanek='${nazwa}'",null)

            Toast.makeText(this, "Usunieto : $nazwa \nz ulubionych!",Toast.LENGTH_SHORT).show()
            val c = db.rawQuery("Select * from ${TableInfo.TABLE_NAME}",null)
            this.adapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                c, arrayOf("_id","przystanek"),
                intArrayOf(android.R.id.text1,android.R.id.text1),
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE)

            val listView: ListView = findViewById(R.id.list_view_favs) as ListView
            listView.setAdapter(adapter)
            db.close()
            searchBar.setText(searchBar.getText())
            true
        }






        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //SEARCH FILTER
                val dbHelper = TableInfo.DataBaseHelper(applicationContext)
                val db = dbHelper.writableDatabase
                val d = db.rawQuery("Select * from ${TableInfo.TABLE_NAME} where ${TableInfo.TABLE_COLUMN_PRZYSTANEK} like '%${charSequence}%'",null)
                val adapter = SimpleCursorAdapter(applicationContext,
                    android.R.layout.simple_list_item_2,
                    d, arrayOf<String>("_id","przystanek"),
                    intArrayOf(android.R.id.text1,android.R.id.text1),
                    SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE)

                val listView: ListView = findViewById(R.id.list_view_favs) as ListView
                listView.setAdapter(adapter)
                db.close()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

    }

    private fun showPopup(view: View) {
        var popup: PopupMenu?
        popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.menu_favorite -> {
                    Toast.makeText(this@Favorite, item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Favorite::class.java)
                    startActivity(intent)
                }
                R.id.menu_localization -> {
                    Toast.makeText(this@Favorite, item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Map::class.java)
                    startActivity(intent)
                }
                R.id.menu_stops -> {
                    Toast.makeText(this@Favorite, item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Details::class.java)
                    startActivity(intent)
                }
                R.id.qr_code -> {
                    Toast.makeText(this@Favorite, item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, QrCode::class.java)
                    startActivity(intent)
                }

            }

            true
        })

        popup.show()
    }
}
