package com.aar.app.proyectoLlodio.offline

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aar.app.proyectoLlodio.*
import com.aar.app.proyectoLlodio.javaservices.DirectionsActivity
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMarkerClickListener
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.offline.OfflineManager
import com.mapbox.mapboxsdk.offline.OfflineRegion
import com.mapbox.mapboxsdk.offline.OfflineRegionDefinition
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus
import com.mapbox.mapboxsdk.plugins.annotation.Line
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.offline.model.OfflineDownloadOptions
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflineConstants.KEY_BUNDLE
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflineDownloadChangeListener
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflinePlugin
import kotlinx.android.synthetic.main.activity_offline_region_detail.*
import timber.log.Timber

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
class OfflineRegionDetailActivity : AppCompatActivity(), OfflineDownloadChangeListener {

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






        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                // Set the origin location to the Alhambra landmark in Granada, Spain.
                origin = Point.fromLngLat(-2.971638888888889, 43.1716111)

                // Set the destination location to the Plaza del Triunfo in Granada, Spain.
                destination = Point.fromLngLat(-2.9717944444444444, 43.1719361)

                initSource(style)

                initLayers(style)

                // Get the directions route from the Mapbox Directions API
                getRoute(mapboxMap, origin, destination)
            }
        }


    }

    private fun loadOfflineDownload(bundle: Bundle) {
        val regionId: Long
        val offlineDownload = bundle.getParcelable<OfflineDownloadOptions>(KEY_BUNDLE)
        regionId = if (offlineDownload != null) {
            // coming from notification
            offlineDownload.uuid()
        } else {
            // coming from list
            bundle.getLong(KEY_REGION_ID_BUNDLE, -1)
        }

        if (regionId != -1L) {
            loadOfflineRegion(regionId)
        }
    }

    private fun loadOfflineRegion(id: Long) {
        OfflineManager.getInstance(this)
                .listOfflineRegions(object : OfflineManager.ListOfflineRegionsCallback {

                    override fun onList(offlineRegions: Array<OfflineRegion>) {
                        for (region in offlineRegions) {
                            if (region.id == id) {
                                offlineRegion = region
                                val definition = region.definition as OfflineRegionDefinition
                                setupUI(definition)
                                return
                            }
                        }
                    }

                    override fun onError(error: String) {
                        Timber.e(error)
                    }
                })
    }



    private fun setupUI(definition: OfflineRegionDefinition) {
        // update map
        mapView?.getMapAsync { mapboxMap ->
            // correct style
            mapboxMap.setOfflineRegionDefinition(definition) { _ ->
                // restrict camera movement
                mapboxMap.setLatLngBoundsForCameraTarget(definition.bounds)
                val icon1 = IconFactory.getInstance(this).fromResource(R.drawable.actividad1)
                val icon2 = IconFactory.getInstance(this).fromResource(R.drawable.actividad2)
                val icon3 = IconFactory.getInstance(this).fromResource(R.drawable.actividad3)
                val icon4 = IconFactory.getInstance(this).fromResource(R.drawable.actividad4)
                val icon5 = IconFactory.getInstance(this).fromResource(R.drawable.actividad5)
                val icon6 = IconFactory.getInstance(this).fromResource(R.drawable.actividad6)
                val icon7 = IconFactory.getInstance(this).fromResource(R.drawable.actividad7)

                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1716111, -2.971638888888889)).setTitle("Ermuko Andra Mari").setIcon(icon1))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1719361, -2.9717944444444444)).setTitle("Indusketak").setIcon(icon2))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1693611, -2.968888888888889)).setTitle("San Antonio Ermita").setIcon(icon3))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1563111, -2.9710055555555557)).setTitle("Lezeagako Sorgina").setIcon(icon4))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setIcon(icon5))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setIcon(icon6))
                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.143613, -2.961956)).setTitle("Dolumin barikua").setIcon(icon7))
                val marcadores = mapboxMap.getMarkers()
                mapboxMap.setOnMarkerClickListener(OnMarkerClickListener { marker -> verMarcadorPulsado(marker,marcadores) })
                // update textview data
                offlineRegion?.metadata?.let {

                }

                offlineRegion?.getStatus(offlineRegionStatusCallback)


//
//                Intent(this@OfflineRegionDetailActivity, DirectionsActivity::class.java),null,
//                R.string.activity_java_services_directions_url, false, BuildConfig.MIN_SDK_VERSION));

                val intent = Intent(this, DirectionsActivity::class.java)
                startActivity(intent)

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

    private fun verMarcadorPulsado(marker: Marker, marcadores: MutableList<Marker>): Boolean {

        val tituloSitio = marker.title

        for (i in marcadores.indices) {
            when (tituloSitio) {
                "Ermuko Andra Mari" -> {
                    empezarActividad1()
                    return true
                }
                "Indusketak" -> {
                    empezarActividad2()
                    return true
                }
                "San Antonio Ermita" -> {
                    empezarActividad3()
                    return true
                }
                "Lezeagako Sorgina" -> {
                    empezarActividad4()
                    return true
                }
                "Etxebarri Baserria" -> {
                    empezarActividad5()
                    return true
                }
                "Lamuza Parkea" -> {
                    empezarActividad6()
                    return true
                }
                "Dolumin barikua" -> {
                    empezarActividad7()
                    return true
                }
            }
        }
        return false
    }

    //ACTIVIDADES AL PULSAR SOBRE LOS MARCADORES
    private fun empezarActividad1() {
        val intent = Intent(this, Actividad1_empezar::class.java)
        startActivity(intent)
    }

    private fun empezarActividad2() {
        val intent = Intent(this, Actividad2_empezar::class.java)
        startActivity(intent)
    }

    private fun empezarActividad3() {
        val intent = Intent(this, Actividad3::class.java)
        startActivity(intent)
    }

    private fun empezarActividad4() {
        val intent = Intent(this, Actividad4_empezar::class.java)
        startActivity(intent)
    }

    private fun empezarActividad5() {
        val intent = Intent(this, Actividad5_empezar::class.java)
        startActivity(intent)
    }

    private fun empezarActividad6() {
        val intent = Intent(this, Actividad6_empezar::class.java)
        startActivity(intent)
    }

    private fun empezarActividad7() {
        val intent = Intent(this, Actividad7::class.java)
        startActivity(intent)
    }

}