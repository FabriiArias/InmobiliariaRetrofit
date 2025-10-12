package com.prueba.inmobiliariaretrofit.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.prueba.inmobiliariaretrofit.databinding.FragmentPerfilBinding;
import com.prueba.inmobiliariaretrofit.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;;
    private PerfilViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        vm.obtenerPerfil();

        vm.getPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                if (propietario != null) {
                    binding.etNombre.setText(propietario.getNombre());
                    binding.etApellido.setText(propietario.getApellido());
                    binding.etDni.setText(propietario.getDni());
                    binding.etTelefono.setText(propietario.getTelefono());
                    binding.etEmail.setText(propietario.getEmail());
                }
            }
        });

        vm.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etDni.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
                binding.etEmail.setEnabled(aBoolean);
            }
        });
        vm.getBtnNombre().observe((getViewLifecycleOwner()), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btPerfil.setText(s);
            }
        });
        binding.btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cambioboton(binding.btPerfil.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etDni.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etEmail.getText().toString());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

}