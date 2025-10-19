package com.prueba.inmobiliariaretrofit.ui.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prueba.inmobiliariaretrofit.MainActivity;

import com.prueba.inmobiliariaretrofit.modelo.UserModel;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<UserModel> mutableUser;
    private MutableLiveData<String> mutableError;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<UserModel> getMutableUser(){
        if (mutableUser==null){
            mutableUser=new MutableLiveData<>();
        }

        return mutableUser;
    }

    public LiveData<String> getMutableError(){
        if (mutableError==null){
            mutableError=new MutableLiveData<>();
        }

        return mutableError;
    }

    public void validarUsuario(String email, String password){

        if (email.isEmpty() || password.isEmpty()) {

            mutableError.setValue("Todos los campos son obligatorios");

            return;

        }

        ApiClient.InmoServicio inmoServicio = ApiClient.getInmoServicio();
        Call<String> call= inmoServicio.loginForm(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String token = response.body();
                    Log.d("token", token);
                    ApiClient.guardarToken(getApplication(), token);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplication().startActivity(intent);
                }else{
                    Log.d("token", response.message());
                    Log.d("token", response.code() + "");
                    Toast.makeText(getApplication(), "CREDENCIALES INCORRECTAS", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("token", throwable.getMessage());

            }
        });


//
//        if ( email.equals("tuqi") && password.equals("123")) {
//
//            UserModel usuario = new UserModel();
//
//            usuario.setEmail(email);
//
//            usuario.setPassword(password);
//
//            usuario.setNombre("tuqi");
//
//            //usuario.setFoto(R.drawable.ic_menu_camera);
//
//
//
//            Intent intent = new Intent(getApplication(), MainActivity.class);
//
//            intent.putExtra("User", usuario);
//
//            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//
//            getApplication().startActivity(intent);
//
//        }else{
//            mutableError.setValue("Usuario o contraseña incorrecto");
//
//            return;
//        }

    }


}
