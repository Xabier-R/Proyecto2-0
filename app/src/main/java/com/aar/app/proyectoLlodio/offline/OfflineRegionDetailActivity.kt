package com.aar.app.proyectoLlodio.offline

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aar.app.proyectoLlodio.*
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMarkerClickListener
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.offline.OfflineManager
import com.mapbox.mapboxsdk.offline.OfflineRegion
import com.mapbox.mapboxsdk.offline.OfflineRegionDefinition
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.offline.model.OfflineDownloadOptions
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflineConstants.KEY_BUNDLE
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflineDownloadChangeListener
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflinePlugin
import com.mapbox.mapboxsdk.style.expressions.Expression.zoom
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.activity_offline_region_detail.*
import timber.log.Timber
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.*

/**
 * Activity showing the detail of an offline region.
 *
 *
 * This Activity can bind to the OfflineDownloadService and show progress.
 *
 *
 *
 * This Activity listens to broadcast events related to successful, canceled and errored download.
 *
 */
class OfflineRegionDetailActivity : AppCompatActivity(), OfflineDownloadChangeListener, OnMapReadyCallback {
    override fun onMapReady(mapboxMap: MapboxMap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val ORIGIN_ICON_ID = "origin-icon-id"
    private val DESTINATION_ICON_ID = "destination-icon-id"
    private val ROUTE_LAYER_ID = "route-layer-id"
    private val ROUTE_LINE_SOURCE_ID = "route-source-id"
    private val ICON_LAYER_ID = "icon-layer-id"
    private val ICON_SOURCE_ID = "icon-source-id"
    private var offlinePlugin: OfflinePlugin? = null
    private var offlineRegion: OfflineRegion? = null
    private var isDownloading: Boolean = false
    private var lineManager: LineManager? = null
    private var origin: Point? = null
    private var destination: Point? = null


    /**
     * Callback invoked when the states of an offline region changes.
     */
    private val offlineRegionStatusCallback = object : OfflineRegion.OfflineRegionStatusCallback {
        override fun onStatus(status: OfflineRegionStatus) {
            isDownloading = !status.isComplete

        }

        override fun onError(error: String) {
            Toast.makeText(
                    this@OfflineRegionDetailActivity,
                    "Error getting offline region state: $error",
                    Toast.LENGTH_SHORT).show()
        }
    }

    private val offlineRegionDeleteCallback = object : OfflineRegion.OfflineRegionDeleteCallback {
        override fun onDelete() {
            Toast.makeText(
                    this@OfflineRegionDetailActivity,
                    "Region deleted.",
                    Toast.LENGTH_SHORT).show()
            finish()
        }

        override fun onError(error: String) {
            fabDelete.isEnabled = true
            Toast.makeText(
                    this@OfflineRegionDetailActivity,
                    "Error getting offline region state: $error",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_region_detail)
        mapView?.onCreate(savedInstanceState)
        offlinePlugin = OfflinePlugin.getInstance(this)

        val bundle = intent.extras
        if (bundle != null) {
            loadOfflineDownload(bundle)
        }

        fabDelete.setOnClickListener { onFabClick(it) }


    }

    private fun loadOfflineDownload(bundle: Bundle) {
        val regionId: Long
        val actividad: String
        val offlineDownload = bundle.getParcelable<OfflineDownloadOptions>(KEY_BUNDLE)
        regionId = if (offlineDownload != null) {
            // coming from notification
            offlineDownload.uuid()
        } else {
            // coming from list
            bundle.getLong(KEY_REGION_ID_BUNDLE, -1)
        }

        actividad = bundle.getString("actividad", "1")

        if (regionId != -1L) {
            loadOfflineRegion(regionId, actividad)
        }
    }

    private fun loadOfflineRegion(id: Long, actividad: String) {
        OfflineManager.getInstance(this)
                .listOfflineRegions(object : OfflineManager.ListOfflineRegionsCallback {

                    override fun onList(offlineRegions: Array<OfflineRegion>) {
                        for (region in offlineRegions) {
                            if (region.id == id) {
                                offlineRegion = region
                                val definition = region.definition as OfflineRegionDefinition





                                setupUI(definition,actividad)
                                return
                            }
                        }
                    }

                    override fun onError(error: String) {
                        Timber.e(error)
                    }
                })
    }



    private fun setupUI(definition: OfflineRegionDefinition, actividad: String) {
        // update map

        mapView?.getMapAsync { mapboxMap ->
            // correct style
            mapboxMap.setOfflineRegionDefinition(definition) { _ ->
                // restrict camera movement
                Toast.makeText(this@OfflineRegionDetailActivity, actividad, Toast.LENGTH_SHORT).show()
                mapboxMap.setLatLngBoundsForCameraTarget(definition.bounds)
                val icon1 = IconFactory.getInstance(this).fromResource(R.drawable.actividad1)
                val icon2 = IconFactory.getInstance(this).fromResource(R.drawable.actividad2)
                val icon3 = IconFactory.getInstance(this).fromResource(R.drawable.actividad3)
                val icon4 = IconFactory.getInstance(this).fromResource(R.drawable.actividad4)
                val icon5 = IconFactory.getInstance(this).fromResource(R.drawable.actividad5)
                val icon6 = IconFactory.getInstance(this).fromResource(R.drawable.actividad6)
                val icon7 = IconFactory.getInstance(this).fromResource(R.drawable.actividad7)





                if(actividad=="1")
                {
                    mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1716111, -2.971638888888889)).setTitle("Ermuko Andra Mari").setIcon(icon1))

                   mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1719361, -2.9717944444444444)).setTitle("Indusketak").setIcon(icon2))
                   mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1693611, -2.968888888888889)).setTitle("San Antonio Ermita").setIcon(icon3))
                   mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1563111, -2.9710055555555557)).setTitle("Lezeagako Sorgina").setIcon(icon4))
                   mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setIcon(icon5))
                   mapboxMap.addMarker(MarkerOptions().position(LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setIcon(icon6))
                   mapboxMap.addMarker(MarkerOptions().position(LatLng(43.143613, -2.961956)).setTitle("Dolumin barikua").setIcon(icon7))
                }
                else
                {
                    if (actividad=="2")
                    {
                        LoadGeoJson(this@OfflineRegionDetailActivity, mapboxMap, "1_2.geojson").execute()
                        mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1716111, -2.971638888888889)).setTitle("Ermuko Andra Mari").setIcon(icon1))
                        mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1719361, -2.9717944444444444)).setTitle("Indusketak").setIcon(icon2))

                        val position: CameraPosition? = CameraPosition.Builder().target(LatLng(43.17177361, -2.9717166666666))
                            .zoom(18.0) // Sets the zoom
                            .bearing(180.0) // Rotate the camera
                            .tilt(30.0) // Set the camera tilt
                            .build(); // Creates a CameraPosition from the builder
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position!!),6000)
                    }
                    else
                    {
                        if (actividad=="3")
                        {
                            LoadGeoJson(this@OfflineRegionDetailActivity, mapboxMap, "2_3.geojson").execute()
                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1719361, -2.9717944444444444)).setTitle("Indusketak").setIcon(icon2))
                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1693611, -2.968888888888889)).setTitle("San Antonio Ermita").setIcon(icon3))


                            val position: CameraPosition? = CameraPosition.Builder().target(LatLng(43.1706486, -2.97033566))
                                    .zoom(16.0) // Sets the zoom
                                    .bearing(180.0) // Rotate the camera
                                    .tilt(30.0) // Set the camera tilt
                                    .build(); // Creates a CameraPosition from the builder
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position!!),6000)
                        }
                        else
                        {
                            if (actividad == "4") {
                                LoadGeoJson(this@OfflineRegionDetailActivity, mapboxMap, "3_4.geojson").execute()
                                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1693611, -2.968888888888889)).setTitle("San Antonio Ermita").setIcon(icon3))
                                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1563111, -2.9710055555555557)).setTitle("Lezeagako Sorgina").setIcon(icon4))


                                val position: CameraPosition? = CameraPosition.Builder().target(LatLng(43.1628361, -2.9699666666))
                                        .zoom(14.0) // Sets the zoom
                                        .bearing(180.0) // Rotate the camera
                                        .tilt(30.0) // Set the camera tilt
                                        .build(); // Creates a CameraPosition from the builder
                                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position!!),6000)


                            }

                           else
                            {
                                if (actividad == "5") {
                                    LoadGeoJson(this@OfflineRegionDetailActivity, mapboxMap, "4_5.geojson").execute()
                                    mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1563111, -2.9710055555555557)).setTitle("Lezeagako Sorgina").setIcon(icon4))
                                    mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setIcon(icon5))


                                    val position: CameraPosition? = CameraPosition.Builder().target(LatLng(43.1474097, -2.9683481111))
                                            .zoom(14.0) // Sets the zoom
                                            .bearing(180.0) // Rotate the camera
                                            .tilt(30.0) // Set the camera tilt
                                            .build(); // Creates a CameraPosition from the builder
                                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position!!),6000)


                                }
                                else
                                {
                                    if (actividad == "6") {
                                        LoadGeoJson(this@OfflineRegionDetailActivity, mapboxMap, "5_6.geojson").execute()
                                        mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setIcon(icon5))
                                        mapboxMap.addMarker(MarkerOptions().position(LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setIcon(icon6))


                                        val position: CameraPosition? = CameraPosition.Builder().target(LatLng(43.141299, -2.964889999))
                                                .zoom(15.5) // Sets the zoom
                                                .bearing(180.0) // Rotate the camera
                                                .tilt(30.0) // Set the camera tilt
                                                .build(); // Creates a CameraPosition from the builder
                                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position!!),6000)




                                    }
                                    else
                                    {
                                        if (actividad == "7") {
                                            LoadGeoJson(this@OfflineRegionDetailActivity, mapboxMap, "6_7.geojson").execute()
                                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setIcon(icon6))
                                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.143613, -2.961956)).setTitle("Dolumin barikua").setIcon(icon7))



                                            val position: CameraPosition? = CameraPosition.Builder().target(LatLng(43.143851, -2.963018))
                                                    .zoom(16.5) // Sets the zoom
                                                    .bearing(180.0) // Rotate the camera
                                                    .tilt(30.0) // Set the camera tilt
                                                    .build(); // Creates a CameraPosition from the builder
                                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position!!),6000)


                                        }

                                    }
                                }
                            }


                        }

                    }

                }


                /*mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1716111, -2.971638888888889)).setTitle("Ermuko Andra Mari").setIcon(icon1))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1719361, -2.9717944444444444)).setTitle("Indusketak").setIcon(icon2))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1693611, -2.968888888888889)).setTitle("San Antonio Ermita").setIcon(icon3))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1563111, -2.9710055555555557)).setTitle("Lezeagako Sorgina").setIcon(icon4))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setIcon(icon5))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setIcon(icon6))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.143613, -2.961956)).setTitle("Dolumin barikua").setIcon(icon7))*/





                val marcadores = mapboxMap.getMarkers()
                mapboxMap.setOnMarkerClickListener(OnMarkerClickListener { marker -> verMarcadorPulsado(marker, marcadores, mapboxMap) })
                // update textview data

                offlineRegion?.metadata?.let {
                }

                offlineRegion?.getStatus(offlineRegionStatusCallback)

            }


        }

    }




    private fun drawLines(featureCollection: FeatureCollection, mapboxMap: MapboxMap?) {
        if (mapboxMap != null) {
            mapboxMap.getStyle { style ->
                if (featureCollection.features() != null) {
                    if (featureCollection.features()!!.size > 0) {
                        style.addSource(GeoJsonSource("line-source", featureCollection))

                        // The layer properties for our line. This is where we make the line dotted, set the
                        // color, etc.
                        style.addLayer(LineLayer("linelayer", "line-source")
                                .withProperties(PropertyFactory.lineCap(Property.LINE_CAP_SQUARE),
                                        PropertyFactory.lineJoin(Property.LINE_JOIN_MITER),
                                        PropertyFactory.lineOpacity(.7f),
                                        PropertyFactory.lineWidth(6f),
                                        PropertyFactory.lineColor(Color.parseColor("#9400ab"))))
                    }
                }
            }
        }
    }

    private class LoadGeoJson internal constructor(activity: OfflineRegionDetailActivity, mapboxMap: MapboxMap?, s: String) : AsyncTask<Void, Void, FeatureCollection>() {

        private val weakReference: WeakReference<OfflineRegionDetailActivity>
        init {
            this.weakReference = WeakReference(activity)
        }
        private var mapboxMap: MapboxMap? = mapboxMap
        private var nombreruta: String = s

        override fun doInBackground(vararg voids: Void): FeatureCollection? {
            try {
                val activity = weakReference.get()
                if (activity != null) {
                    val inputStream = activity.assets.open(nombreruta)
                    return FeatureCollection.fromJson(convertStreamToString(inputStream))
                }
            } catch (exception: Exception) {
                Timber.e("Exception Loading GeoJSON: %s", exception.toString())
            }

            return null
        }

        override fun onPostExecute(featureCollection: FeatureCollection?) {
            super.onPostExecute(featureCollection)
            val activity = weakReference.get()
            if (activity != null && featureCollection != null) {
                activity.drawLines(featureCollection, mapboxMap)
            }
        }

        companion object {

            internal fun convertStreamToString(`is`: InputStream): String {
                val scanner = Scanner(`is`).useDelimiter("\\A")
                return if (scanner.hasNext()) scanner.next() else ""
            }
        }
    }





    fun onFabClick(view: View) {
        if (offlineRegion != null) {
            if (!isDownloading) {
                // delete download
                offlineRegion?.delete(offlineRegionDeleteCallback)
            } else {
                // cancel download
                val offlineDownload = offlinePlugin?.getActiveDownloadForOfflineRegion(offlineRegion)
                if (offlineDownload != null) {
                    offlinePlugin?.cancelDownload(offlineDownload)
                    isDownloading = false
                }
            }
            view.visibility = View.GONE
        }
    }

    override fun onCreate(offlineDownload: OfflineDownloadOptions) {
        Timber.e("OfflineDownloadOptions created %s", offlineDownload.hashCode())
    }

    override fun onSuccess(offlineDownload: OfflineDownloadOptions) {
        isDownloading = false
        regionStateProgress.visibility = View.INVISIBLE

    }

    override fun onCancel(offlineDownload: OfflineDownloadOptions) {
        finish() // nothing to do in this screen, cancel = delete
    }

    override fun onError(offlineDownload: OfflineDownloadOptions, error: String, message: String) {
        regionStateProgress.visibility = View.INVISIBLE

        Toast.makeText(this, error + message, Toast.LENGTH_LONG).show()
    }

    override fun onProgress(offlineDownload: OfflineDownloadOptions, progress: Int) {
        if (offlineRegion == null) {
            return
        }

        if (offlineDownload.uuid() == offlineRegion?.id) {
            if (regionStateProgress.visibility != View.VISIBLE) {
                regionStateProgress.visibility = View.VISIBLE
            }
            isDownloading = true
            regionStateProgress.progress = progress
        }
    }




    override fun onStart() {
        super.onStart()
        mapView?.onStart()
        offlinePlugin?.addOfflineDownloadStateChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        offlinePlugin?.removeOfflineDownloadStateChangeListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onDestroy() {

        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    companion object {

        val KEY_REGION_ID_BUNDLE = "com.mapbox.mapboxsdk.plugins.offline.bundle.id"
    }


    override fun onBackPressed() {

        val intent = Intent(this, Pantalla2::class.java)
        startActivity(intent)

    }

    private fun verMarcadorPulsado(marker: Marker, marcadores: MutableList<Marker>, mapboxMap: MapboxMap): Boolean {

        val tituloSitio = marker.title

        for (i in marcadores.indices) {
            when (tituloSitio) {
                "Ermuko Andra Mari" -> {
                    empezarActividad1(mapboxMap)
                    return true
                }
                "Indusketak" -> {
                    empezarActividad2(mapboxMap)
                    return true
                }
                "San Antonio Ermita" -> {
                    empezarActividad3(mapboxMap)
                    return true
                }
                "Lezeagako Sorgina" -> {
                    empezarActividad4(mapboxMap)
                    return true
                }
                "Etxebarri Baserria" -> {
                    empezarActividad5(mapboxMap)
                    return true
                }
                "Lamuza Parkea" -> {
                    empezarActividad6(mapboxMap)
                    return true
                }
                "Dolumin barikua" -> {
                    empezarActividad7(mapboxMap)
                    return true
                }
            }
        }
        return false
    }

    //ACTIVIDADES AL PULSAR SOBRE LOS MARCADORES
    private fun empezarActividad1(mapboxMap: MapboxMap) {

        val intent = Intent(this, Actividad1_empezar::class.java)
        startActivityForResult(intent, 1)
        finish()

    }

    private fun empezarActividad2(mapboxMap: MapboxMap) {
        val intent = Intent(this, Actividad2_empezar::class.java)
        startActivityForResult(intent, 2)
    }

    private fun empezarActividad3(mapboxMap: MapboxMap) {
        val intent = Intent(this, Actividad3::class.java)
        startActivityForResult(intent, 3)
    }

    private fun empezarActividad4(mapboxMap: MapboxMap) {
        val intent = Intent(this, Actividad4_empezar::class.java)
        startActivityForResult(intent, 4)
    }

    private fun empezarActividad5(mapboxMap: MapboxMap) {
        val intent = Intent(this, Actividad5_empezar::class.java)
        startActivityForResult(intent, 5)
    }

    private fun empezarActividad6(mapboxMap: MapboxMap) {
        val intent = Intent(this, Actividad6_empezar::class.java)
        startActivityForResult(intent, 6)
    }

    private fun empezarActividad7(mapboxMap: MapboxMap) {
        val intent = Intent(this, Actividad7::class.java)
        startActivityForResult(intent, 7)
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, mapboxMap: MapboxMap) {
        super.onActivityResult(requestCode, resultCode, data)
        var respC = 0
        var respI = 0

            if (resultCode == Activity.RESULT_OK) {

                Toast.makeText(this@OfflineRegionDetailActivity, resultCode, Toast.LENGTH_SHORT).show()

                /*val intent = Intent(this, OfflineRegionDetailActivity::class.java)
                intent.putExtra(KEY_REGION_ID_BUNDLE, 1L)
                val cod: String=resultCode.toString()
                intent.putExtra("Actividad", cod)

                startActivity(intent)*/


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }



    }


}

