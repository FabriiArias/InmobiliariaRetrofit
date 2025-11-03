package com.prueba.inmobiliariaretrofit.ui.inmuebles;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prueba.inmobiliariaretrofit.modelo.Inmueble;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();
    public InmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Inmueble>> getListaInmuebles(){
        return listaInmuebles;
    }

    public void obtenerListaInmuebles(){
        String token = ApiClient.leerToken(getApplication());

        // DEBUG: Verifiquemos qué token estamos enviando.
        if (token == null) {
            Log.e("TOKEN_DEBUG", "El token leído es NULO. La llamada a la API fallará.");
            Toast.makeText(getApplication(), "Error: Sesión no iniciada. Vuelva a loguearse.", Toast.LENGTH_LONG).show();
            return; // Detenemos la ejecución si no hay token.
        }
        Log.d("TOKEN_DEBUG", "Token a enviar: Bearer " + token);


        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call <List<Inmueble>> call = api.getInmueble("Bearer "+ token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    // DEBUG: La respuesta fue exitosa (código 2xx).
                    Log.d("API_SUCCESS", "Inmuebles obtenidos correctamente.");
                    listaInmuebles.postValue(response.body());
                } else {
                    // DEBUG: El servidor respondió con un error (4xx o 5xx).
                    // ESTE ES EL LOG MÁS IMPORTANTE PARA DEPURAR.
                    Log.e("API_ERROR", "Respuesta no exitosa. Código: " + response.code() + " Mensaje: " + response.message());
                    Toast.makeText(getApplication(),"No se obtuvieron Inmuebles (Error " + response.code() + ")",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                // DEBUG: La llamada falló antes de obtener una respuesta del servidor.
                Log.e("API_FAILURE", "Fallo en la conexión: " + throwable.getMessage(), throwable);
                Toast.makeText(getApplication(),"Error de conexión al obtener Inmuebles",Toast.LENGTH_LONG).show();
            }
        });
    }
}
