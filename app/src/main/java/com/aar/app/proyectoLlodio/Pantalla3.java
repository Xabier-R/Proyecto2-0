package com.aar.app.proyectoLlodio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import java.util.List;


public class Pantalla3 extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, LocationListener {

    private MapView mapView;
    private MapboxMap mapa;
    LocationComponent locationComponent;
    PermissionsManager permissionsManager;
    private BuildingPlugin buildingPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.pantalla3);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {}

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted){
            //habilitarLocalizacion(mapa.getStyle());
        }
        else{
            Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapa = mapboxMap;

        mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                habilitarLocalizacion(style);
                buildingPlugin = new BuildingPlugin(mapView, mapa, style);
                buildingPlugin.setMinZoomLevel(15f);
                buildingPlugin.setVisibility(true);
            }
        });
        aniadirMarcadores();
        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                verMarcadorPulsado(marker);
                return false;
            }
        });
    }

    private void habilitarLocalizacion(Style style){
        if (PermissionsManager.areLocationPermissionsGranted(this))
        {
            locationComponent = mapa.getLocationComponent();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            locationComponent.activateLocationComponent(this, style);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.NORMAL);
        }
        else
        {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void aniadirMarcadores()
    {
        Icon icon = IconFactory.getInstance(this).fromResource(R.drawable.favorito2);

        mapa.addMarker(new MarkerOptions().position(new LatLng(43.1716111, -2.971638888888889)).setTitle("Ermuko Andra Mari").setSnippet("actividad 1").setIcon(icon));
        mapa.addMarker(new MarkerOptions().position(new LatLng(43.1719361,-2.9717944444444444)).setTitle("Indusketak").setSnippet("actividad 2").setIcon(icon));
        mapa.addMarker(new MarkerOptions().position(new LatLng(43.1693611,-2.968888888888889)).setTitle("San Antonio Ermita").setSnippet("actividad 3").setIcon(icon));
        mapa.addMarker(new MarkerOptions().position(new LatLng(43.1563111,-2.9710055555555557)).setTitle("Lezeagako Sorgina").setSnippet("actividad 4").setIcon(icon));
        mapa.addMarker(new MarkerOptions().position(new LatLng(43.1385083, -2.965691666666667)).setTitle("Etxebarri Baserria").setSnippet("actividad 5").setIcon(icon));
        mapa.addMarker(new MarkerOptions().position(new LatLng(43.144090, -2.964080)).setTitle("Lamuza Parkea").setSnippet("actividad 6").setIcon(icon));
        mapa.addMarker(new MarkerOptions().position(new LatLng(43.143613, -2.961956)).setTitle("Dolumin barikua").setSnippet("actividad 7").setIcon(icon));

        mapa.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                return verMarcadorPulsado(marker);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        cambiarPosicionCamara(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private void cambiarPosicionCamara(Location location){
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
    }

    private boolean verMarcadorPulsado(Marker marker)
    {
        List<Marker> marcadores = mapa.getMarkers();
        String tituloSitio = marker.getTitle();

        for (int i=0; i<marcadores.size(); i++)
        {
            switch(tituloSitio) {
                case "Ermuko Andra Mari":
                    empezarActividad1();
                    return true;
                case "Indusketak":
                    empezarActividad2();
                    return true;
                case "San Antonio Ermita":
                    empezarActividad3();
                    return true;
                case "Lezeagako Sorgina":
                    empezarActividad4();
                    return true;
                case "Etxebarri Baserria":
                    empezarActividad5();
                    return true;
                case "Lamuza Parkea":
                    empezarActividad6();
                    return true;
                case "Dolumin barikua":
                    empezarActividad7();
                    return true;
                default:
            }
        }
        return false;
    }

    //ACTIVIDADES AL PULSAR SOBRE LOS MARCADORES
    private void empezarActividad1() {
        Intent intent = new Intent(this, Actividad1_empezar.class);
        startActivity(intent);
    }

    private void empezarActividad2(){
        Intent intent = new Intent(this, Actividad2_empezar.class);
        startActivity(intent);
    }

    private void empezarActividad3(){
        Intent intent = new Intent(this, Actividad3.class);
        startActivity(intent);
    }

    private void empezarActividad4(){
        Intent intent = new Intent(this, Actividad4_empezar.class);
        startActivity(intent);
    }

    private void empezarActividad5(){
        Intent intent = new Intent(this, Actividad5.class);
        startActivity(intent);
    }

    private void empezarActividad6() {
        Intent intent = new Intent(this, Actividad6_empezar.class);
        startActivity(intent);
    }

    private void empezarActividad7(){
        Intent intent = new Intent(this, Actividad7.class);
        startActivity(intent);
    }
}
