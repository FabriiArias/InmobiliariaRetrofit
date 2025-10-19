package com.prueba.inmobiliariaretrofit.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mapaActualMutableLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getMapaActualMutableLiveData() {
        if (mapaActualMutableLiveData == null) {
            mapaActualMutableLiveData = new MutableLiveData<>();
        }
        return mapaActualMutableLiveData;
    }

    public void cargarMapa() {
        MapaActual mapaActual = new MapaActual();
        mapaActualMutableLiveData.postValue(mapaActual);
    }

    public class MapaActual implements OnMapReadyCallback {
        LatLng ULP = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(ULP).title("Facu"));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(ULP)
                    .zoom(10)
                    .bearing(0)
                    .tilt(30)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}
