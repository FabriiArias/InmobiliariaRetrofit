package com.prueba.inmobiliariaretrofit.ui.inmuebles;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.prueba.inmobiliariaretrofit.databinding.FragmentDetalleInmuebleBinding;
import com.prueba.inmobiliariaretrofit.modelo.Inmueble;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

public class DetalleInmueble extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;

    private Inmueble inmueble;

    public static InmuebleFragment newInstance() {
        return new InmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding=FragmentDetalleInmuebleBinding.inflate(inflater,container,false);
        Bundle bundle = getArguments();
        inmueble=(Inmueble) bundle.getSerializable("inmueble");
        View view= binding.getRoot();

        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                mv.buildInmueble(getContext(),binding,inmueble);
            }
        });


        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inmueble.setDisponible(binding.cbDisponible.isChecked());
                mv.actualizarInmueble(inmueble);
            }
        });

        mv.recuperarInmueble(getArguments());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }


}
