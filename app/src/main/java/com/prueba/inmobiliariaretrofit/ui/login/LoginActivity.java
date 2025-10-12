package com.prueba.inmobiliariaretrofit.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.prueba.inmobiliariaretrofit.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());



        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=binding.etEmail.getText().toString();
                String pass=binding.etPassword.getText().toString();
                viewModel.validarUsuario(mail,pass);

            }
        });

        viewModel.getMutableError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvError.setText(s);
            }
        });
    }


    }
