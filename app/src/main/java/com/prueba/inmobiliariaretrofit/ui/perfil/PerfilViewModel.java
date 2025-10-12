package com.prueba.inmobiliariaretrofit.ui.perfil;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prueba.inmobiliariaretrofit.modelo.Propietario;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();

    private MutableLiveData<Propietario> propietario = new MutableLiveData<>();


    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getEstado() {
        return mEstado;
    }
    public  LiveData<String> getBtnNombre(){
        return mNombre;
    }

    public LiveData<Propietario> getPropietario() {
        return propietario;
    }


    public void cambioboton(String nombreboton, String nombre, String apellido, String dni, String telefono, String email){
        if (nombreboton.equalsIgnoreCase("EDITAR")){
            mEstado.setValue(true);
            mNombre.setValue("GUARDAR");
        }else {
            mEstado.setValue(false);
            mNombre.setValue("EDITAR");
            Propietario actualizado = new Propietario();
            actualizado.setIdPropietario(propietario.getValue().getIdPropietario());
            actualizado.setNombre(nombre);
            actualizado.setApellido(apellido);
            actualizado.setDni(dni);
            actualizado.setTelefono(telefono);
            actualizado.setEmail(email);
            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            Call<Propietario> call = api.ActualizarPropietario("Bearer " + token, actualizado);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "ACTUALIZADO CON EXITO", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(), "ERROR AL ACTULAIZAR", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "ERROR EN LA API", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void obtenerPerfil() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Propietario> call = api.getPropietario("Bearer " + token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietario.postValue(response.body());
                } else {
                    Log.d("Perfil", "Error: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Log.e("Perfil", "Error:" + throwable.getMessage());
            }
        });
    }
    }