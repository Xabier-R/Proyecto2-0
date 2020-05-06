package com.aar.app.proyectoLlodio.offline

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aar.app.proyectoLlodio.*
import com.aar.app.proyectoLlodio.bbdd.Actividad
import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper
import com.aar.app.proyectoLlodio.traduccion.LocaleHelper
import com.mapbox.android.core.location.*
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMarkerClickListener
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.offline.OfflineManager
import com.mapbox.mapboxsdk.offline.OfflineRegion
import com.mapbox.mapboxsdk.offline.OfflineRegionDefinition
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.offline.model.OfflineDownloadOptions
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflineConstants.KEY_BUNDLE
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflineDownloadChangeListener
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflinePlugin
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.nightonke.boommenu.BoomButtons.HamButton
import com.nightonke.boommenu.BoomButtons.OnBMClickListener
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.OnBoomListener

import com.nightonke.boommenu.Util
import kotlinx.android.synthetic.main.activity_offline_region_detail.*
import timber.log.Timber
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Actividad que muestra el mapa ofline
 *
 */
class OfflineRegionDetailActivity : AppCompatActivity(), OfflineDownloadChangeListener, OnMapReadyCallback, PermissionsListener, FragmentoLobo.OnFragmentInteractionListener {


    //metodo que al pulsar el boton del frangemto lanza la actividad que se encuentra mas cerca
    override fun onFragmentPulsado(imagen: ImageView?) {

        when (actividadLanzar) {
            "actividad1" -> {
                intent = Intent(applicationContext, Actividad1_empezar::class.java)
                startActivity(intent)
            }
            "actividad2" -> {
                intent = Intent(applicationContext, Actividad2_empezar::class.java)
                startActivity(intent)
            }
            "actividad3" -> {
                intent = Intent(applicationContext, Actividad3::class.java)
                startActivity(intent)
            }
            "actividad4" -> {
                intent = Intent(applicationContext, Actividad4_empezar::class.java)
                startActivity(intent)
            }
            "actividad5" -> {
                intent = Intent(applicationContext, Actividad5_empezar::class.java)
                startActivity(intent)
            }
            "actividad6" -> {
                intent = Intent(applicationContext, Actividad6_empezar::class.java)
                startActivity(intent)
            }
            "actividad7" -> {
                intent = Intent(applicationContext, Actividad7::class.java)
                startActivity(intent)
            }

        }

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
    private var p4 : Pantalla4 = Pantalla4()
    private var desarrollador= p4?.isDesarrollador()
    //Localizacion
    private var callback = LocationChangeListeningActivityLocationCallback(this)
    public var mapaBox: MapboxMap? = null
    private var resultado = ""
    private var permissionsManager: PermissionsManager? = null
    private var locationEngine: LocationEngine? = null
    private var listaMarcadores: List<Marker>? = null

    private val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
    private val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
    private var texto: TextView? = null

    private var ubicacionesActiviades: HashMap<String, LatLng>? = null
    private var actividadLanzar: String? = null


    //BBDD
    var activiades: ActividadesSQLiteHelper? = null
    var db: SQLiteDatabase? = null

    //Fragmento
    private var linearLayout:LinearLayout? = null

    private var init = false


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

        //Pantalla completa
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_offline_region_detail)


        //Abrimos la base de datos "DBactividades" en modo de escritura
        activiades = ActividadesSQLiteHelper(this, "DBactividades", null, 1)
        db = activiades!!.getWritableDatabase()

        val boomMenuButton:BoomMenuButton? = findViewById(R.id.bmb);

        // Añaño los botones al builder con sus respectivos listeners
        var bmbSpanish = HamButton.Builder().normalImageRes(R.drawable.spanish).normalText("Español").textGravity(Gravity.CENTER).typeface(Typeface.DEFAULT_BOLD).textSize(16).imagePadding( Rect(35, 35, 35, 35))
                .listener(object : OnBMClickListener{
                    override fun onBoomButtonClick(index: Int) {
                        cambiarIdioma("es")
                    }
                })

        var bmbEuskera = HamButton.Builder().normalImageRes(R.drawable.euskera).normalText("Euskera").textGravity(Gravity.CENTER).typeface(Typeface.DEFAULT_BOLD).textSize(16).imagePadding( Rect(35, 35, 35, 35))
                .listener(object : OnBMClickListener{
                    override fun onBoomButtonClick(index: Int) {
                        cambiarIdioma("eu")
                    }
                })
        boomMenuButton?.addBuilder(bmbSpanish)
        boomMenuButton?.addBuilder(bmbEuskera)

        instanciarActividades()

        //Vinculo el linearLayout del fragmento
        linearLayout = findViewById(R.id.linearFragmento)



        mapView?.onCreate(savedInstanceState)
        offlinePlugin = OfflinePlugin.getInstance(this)

        val bundle = intent.extras
        if (bundle != null) {
            loadOfflineDownload(bundle)
        }
        fabDelete.setOnClickListener { onFabClick(it) }
    }

    // metodo para cambiar el idioma de la aplicacion
    private fun cambiarIdioma(idioma: String){
        if (idioma.equals("es")) {
            db?.execSQL("UPDATE idiomas SET idioma='es'")
            LocaleHelper.setLocale(this,"es")
        }
        else {
            db?.execSQL("UPDATE idiomas SET idioma='eu'")
            LocaleHelper.setLocale(this,"eu")
        }

    }

    //metodo que instancia todas las actividades
    private fun instanciarActividades() {
        this.ubicacionesActiviades = HashMap<String, LatLng>()

        ubicacionesActiviades!!.put("actividad1", LatLng(43.1716111, -2.971638888888889))
        ubicacionesActiviades!!.put("actividad2", LatLng(43.1719361, -2.9717944444444444))
        ubicacionesActiviades!!.put("actividad3", LatLng(43.1693611, -2.968888888888889))
        ubicacionesActiviades!!.put("actividad4", LatLng(43.1563111, -2.9710055555555557))
        ubicacionesActiviades!!.put("actividad5", LatLng(43.1385083, -2.965691666666667))

        ubicacionesActiviades!!.put("actividad6", LatLng(43.144090, -2.964080))
        ubicacionesActiviades!!.put("actividad7", LatLng(43.143613, -2.961956))

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


    //metodo que carga las activades y las rutas en el mapa
    private fun setupUI(definition: OfflineRegionDefinition, actividad: String) {
        // update map

        mapView?.getMapAsync { mapboxMap ->
            // correct style
            mapboxMap.setOfflineRegionDefinition(definition) { _ ->
                // restrict camera movement

                mapboxMap.setLatLngBoundsForCameraTarget(definition.bounds)


                //Ver posicion
                this.mapaBox = mapboxMap


                enableLocationComponent(this.mapaBox!!.getStyle()!!)

                //creamos los iconos de las actividades
                val icon1 = IconFactory.getInstance(this).fromResource(R.drawable.actividad1)
                val icon2 = IconFactory.getInstance(this).fromResource(R.drawable.actividad2)
                val icon3 = IconFactory.getInstance(this).fromResource(R.drawable.actividad3)
                val icon4 = IconFactory.getInstance(this).fromResource(R.drawable.actividad4)
                val icon5 = IconFactory.getInstance(this).fromResource(R.drawable.actividad5)
                val icon6 = IconFactory.getInstance(this).fromResource(R.drawable.actividad6)
                val icon7 = IconFactory.getInstance(this).fromResource(R.drawable.actividad7)



                if(actividad=="1")
                {
                    if(desarrollador==true)
                    {
                        dibujarTodo(mapboxMap)
                    }
                    else
                    {
                        mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1716111, -2.971638888888889)).setTitle("Ermuko Andra Mari").setIcon(icon1))
                    }


                    listaMarcadores = mapboxMap.markers


                }
                else
                {
                    if (actividad=="2")
                    {
                        LoadGeoJson(this@OfflineRegionDetailActivity, mapboxMap, "1_2.geojson").execute()
                        if(desarrollador==true)
                        {
                            dibujarTodo(mapboxMap)
                        }
                        else {
                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1716111, -2.971638888888889)).setTitle("Ermuko Andra Mari").setIcon(icon1))
                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1719361, -2.9717944444444444)).setTitle("Indusketak").setIcon(icon2))
                        }
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
                            if(desarrollador==true)
                            {
                                dibujarTodo(mapboxMap)
                            }
                            else {
                                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1719361, -2.9717944444444444)).setTitle("Indusketak").setIcon(icon2))
                                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1693611, -2.968888888888889)).setTitle("San Antonio Ermita").setIcon(icon3))
                            }

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
                                if(desarrollador==true)
                                {
                                    dibujarTodo(mapboxMap)
                                }
                                else {
                                    mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1693611, -2.968888888888889)).setTitle("San Antonio Ermita").setIcon(icon3))
                                    mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1563111, -2.9710055555555557)).setTitle("Lezeagako Sorgina").setIcon(icon4))
                                }

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
                                    if(desarrollador==true)
                                    {
                                        dibujarTodo(mapboxMap)
                                    }
                                    else {
                                        mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1563111, -2.9710055555555557)).setTitle("Lezeagako Sorgina").setIcon(icon4))
                                        mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setIcon(icon5))
                                    }

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
                                        if(desarrollador==true)
                                        {
                                            dibujarTodo(mapboxMap)
                                        }
                                        else {
                                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setIcon(icon5))
                                            mapboxMap.addMarker(MarkerOptions().position(LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setIcon(icon6))
                                        }

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
                                            if(desarrollador==true)
                                            {
                                                dibujarTodo(mapboxMap)
                                            }
                                            else {
                                                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setIcon(icon6))
                                                mapboxMap.addMarker(MarkerOptions().position(LatLng(43.143613, -2.961956)).setTitle("Dolumin barikua").setIcon(icon7))
                                            }


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




                val marcadores = mapboxMap.getMarkers()
                mapboxMap.setOnMarkerClickListener(OnMarkerClickListener { marker -> verMarcadorPulsado(marker, marcadores, mapboxMap) })
                // update textview data

                offlineRegion?.metadata?.let {
                }

                offlineRegion?.getStatus(offlineRegionStatusCallback)

            }
        }
    }

    // metodo que dibuja todas las actividades a la vez
     fun dibujarTodo(mapboxMap: MapboxMap) {
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

    }


    //Ver posicion
    override fun onMapReady(mapboxMap: MapboxMap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(this, "No", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            if (mapaBox?.getStyle() != null) {
                enableLocationComponent(mapaBox?.getStyle()!!)
            }
        } else {
            Toast.makeText(this,"No tienes permisos", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    //Ver posicion


    //Localizacion
    /**
     * Initialize the Maps SDK's LocationComponent
     */
    private fun enableLocationComponent(loadedMapStyle: Style) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            val locationComponent = mapaBox?.getLocationComponent()

            // Set the LocationComponent activation options
            val locationComponentActivationOptions = LocationComponentActivationOptions.builder(this, loadedMapStyle)
                    .useDefaultLocationEngine(false)
                    .build()

            // Activate with the LocationComponentActivationOptions object
            locationComponent?.activateLocationComponent(locationComponentActivationOptions)

            // Enable to make component visible
            locationComponent?.isLocationComponentEnabled = true

            // Set the component's camera mode
            locationComponent?.cameraMode = CameraMode.TRACKING

            // Set the component's render mode
            locationComponent?.renderMode = RenderMode.NORMAL

            initLocationEngine()
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager?.requestLocationPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        permissionsManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    @SuppressLint("MissingPermission")
    private fun initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this)

        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()

        locationEngine?.requestLocationUpdates(request, callback, mainLooper)
        locationEngine?.getLastLocation(callback)
    }

    //Ver posicion
    public class LocationChangeListeningActivityLocationCallback internal constructor(activity: OfflineRegionDetailActivity) : LocationEngineCallback<LocationEngineResult> {

        private val activityWeakReference: WeakReference<OfflineRegionDetailActivity>

        init {
            this.activityWeakReference = WeakReference<OfflineRegionDetailActivity>(activity)
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        override fun onSuccess(result: LocationEngineResult) {
            val activity = activityWeakReference.get()

            if (activity != null) {
                val location = result.lastLocation
                if (location == null) {
                    return;
                }

                val ubicacionActual = LatLng(result.lastLocation?.latitude!!, result.lastLocation?.longitude!!)

                var actividadCercana = activity.verActividad(ubicacionActual, activity.ubicacionesActiviades!!)

                //Si la actividadCercana es diferente de ""
                if (actividadCercana.equals("")==false) {
                    print("La actividad mas cercana es la " + actividadCercana)

                    val actividadesBBDD = ArrayList<Actividad>()
                    val args = arrayOf(actividadCercana)
                    //
                    val c = activity.db?.rawQuery("SELECT actividad, realizada FROM actividades WHERE actividad=?", args);

                    if (c?.moveToFirst()!!) {
                        //Recorremos el cursor hasta que no haya más registros.
                        do {
                            val nombre = c.getString(0)
                            val realizada = c.getString(1)
                            actividadesBBDD.add(Actividad(nombre, realizada))
                        } while (c.moveToNext())
                    }

                    if (actividadesBBDD.get(0).realizada.equals("no")) {
                        activity.empezarActividad1(activity)
                        activity.actividadLanzar = actividadCercana
                    }
                }
                else
                    activity.linearLayout?.visibility = View.INVISIBLE


                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapaBox != null && result.lastLocation != null) {
                    activity.mapaBox?.getLocationComponent()?.forceLocationUpdate(result.lastLocation)
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        override fun onFailure(exception: Exception) {
            Log.d("LocationChangeActivity", exception.localizedMessage!!)
            val activity = activityWeakReference.get()
            if (activity != null) {
                Toast.makeText(activity, exception.localizedMessage,
                        Toast.LENGTH_SHORT).show()
            }
        }
    }
    //Ver posicion



    //metodo que lanza la actividad en la que nos encontremos
    private fun empezarActividad1(activity: OfflineRegionDetailActivity)
    {
        val animatorLobo: ObjectAnimator
        val linearLayout: LinearLayout = findViewById<LinearLayout>(R.id.linearFragmento)

        val fragment = FragmentoLobo()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        //transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.add(R.id.framentoLobo, fragment)
        transaction.commit()


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels



        windowManager.defaultDisplay.getMetrics(metrics)
        animatorLobo = ObjectAnimator.ofFloat(linearLayout,
                "translationX", linearLayout.getX(), linearLayout.getPivotX() - linearLayout.getPivotX());
        animatorLobo.duration = 400
        val animatorSetX = AnimatorSet()
        animatorSetX.playTogether(animatorLobo)
        animatorSetX.start()



        linearLayout.setVisibility(View.VISIBLE)


    }



    // metodo que dibuja las rutas en el mapa
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

    //clase con la que se cargan los ficheros con las distintas rutas
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
        // Prevent leaks
        if (locationEngine != null) {
            locationEngine?.removeLocationUpdates(callback)
        }
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

        // Prevent leaks
        if (locationEngine != null) {
            locationEngine?.removeLocationUpdates(callback)
        }
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

    // devuelve que actividad hasido pulsada
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



//       val intent = Intent(this, GameOverActivity::class.java)
//       startActivityForResult(intent, 7)

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


    fun verActividad(ubiacionActual: LatLng, ubicacionesActiviades: HashMap<String, LatLng>):String{

        //Comprobar a que actividad esta cerca
        for ((key, value) in ubicacionesActiviades) {
            if (ubiacionActual.distanceTo(value)<20)  {
                if(actividadAnteriorRealizada(key))
                {
                    return key

                }

            }


        }
        return ""

    }


    //metodo que muestra cual fue la ultima actividad completada
    fun actividadAnteriorRealizada(key: String):Boolean
    {

        if(key.equals("actividad1"))
        {

            return true

        }
        else {
            var lastChar = key.substring(key.length - 1)

            var num = Integer.parseInt(lastChar) - 1

            val args = arrayOf("actividad" + num)

            var resp = db?.rawQuery("SELECT realizada FROM actividades WHERE actividad=?", args)


            var realizado: String = ""


            if (resp?.moveToFirst()!!) {
                //Recorremos el cursor hasta que no haya más registros.
                do {
                    realizado = resp.getString(0)

                } while (resp.moveToNext())
            }



            if (realizado?.equals("si")!!)
            {
                return true
            }
            else
                return false

        }
    }

}



