package com.aar.app.proyectoLlodio.offline

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aar.app.proyectoLlodio.R
import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper
import com.mapbox.mapboxsdk.offline.OfflineManager
import com.mapbox.mapboxsdk.offline.OfflineRegion
import com.mapbox.mapboxsdk.offline.OfflineRegionDefinition
import com.mapbox.mapboxsdk.plugins.offline.utils.OfflineUtils
import java.util.*

/**
 * Actividad que muestra una lista con todos los mapas descargados
 */
class OfflineRegionListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var adapter: OfflineRegionAdapter
    private var actividad: String = "1"

    //BBDD
    var activiades: ActividadesSQLiteHelper? = null
    var db: SQLiteDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_region_list)


        val extras = intent.extras
        if (extras != null) {
            actividad = extras.getString("actividad", "1")
        }

        val listView = findViewById<ListView>(R.id.listView)
        adapter = OfflineRegionAdapter()
        listView.adapter = adapter
        listView.emptyView = findViewById(android.R.id.empty)
        listView.onItemClickListener = this


        //Abrimos la base de datos "DBactividades" en modo de escritura
        activiades = ActividadesSQLiteHelper(this, "DBactividades", null, 1)
        db = activiades!!.getWritableDatabase()






        val args = arrayOf("no")


        var resp = db?.rawQuery("SELECT actividad FROM actividades WHERE realizada=?",args)


        var rutaActividad: String = ""


        if (resp?.moveToFirst()!!) {

            rutaActividad = resp.getString(0)


        }

        var lastChar = rutaActividad.substring(rutaActividad.length - 1)

        var actividad=lastChar


        val intent = Intent(this, OfflineRegionDetailActivity::class.java)
        intent.putExtra(OfflineRegionDetailActivity.KEY_REGION_ID_BUNDLE, 1L)
        intent.putExtra("actividad", actividad)
        startActivity(intent)
        finish()

    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {


    }

    override fun onStart() {
        super.onStart()
        loadOfflineRegions()


    }

    private fun loadOfflineRegions() {
        OfflineManager.getInstance(this).listOfflineRegions(object : OfflineManager.ListOfflineRegionsCallback {
            override fun onList(offlineRegions: Array<OfflineRegion>?) {
                if (offlineRegions != null) {
                    adapter.setOfflineRegions(Arrays.asList(*offlineRegions))
                }
            }

            override fun onError(error: String) {
                Toast.makeText(this@OfflineRegionListActivity, "Error loading regions $error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private class OfflineRegionAdapter : BaseAdapter() {
        private var offlineRegions: List<OfflineRegion> = ArrayList()
        private var currentRegion: OfflineRegion? = null

        init {
            offlineRegions
        }
        internal fun setOfflineRegions(offlineRegions: List<OfflineRegion>) {
            this.offlineRegions = offlineRegions
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return offlineRegions.size
        }

        override fun getItem(position: Int): OfflineRegion {
            return offlineRegions[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
            var convertView = view
            val holder: ViewHolder

            if (convertView == null) {
                holder = ViewHolder()
                convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_feature, parent, false)
                holder.text = convertView?.findViewById(R.id.nameView)
                holder.subText = convertView.findViewById(R.id.descriptionView)
                convertView.tag = holder
            } else {
                holder = convertView.tag as ViewHolder
            }

            currentRegion = getItem(position)
            currentRegion?.metadata?.let {
                holder.text?.text = OfflineUtils.convertRegionName(it)
            }
            holder.subText?.text = (currentRegion?.definition as OfflineRegionDefinition).styleURL
            return convertView
        }

        internal class ViewHolder {
            var text: TextView? = null
            var subText: TextView? = null
        }
    }
}