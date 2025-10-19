package com.prueba.inmobiliariaretrofit.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;
import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        HomeViewModel inicioViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        inicioViewModel.getMapaActualMutableLiveData().observe(getViewLifecycleOwner(), new Observer<HomeViewModel.MapaActual>() {
            @Override
            public void onChanged(HomeViewModel.MapaActual mapaActual) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {

                    mapFragment.getMapAsync(mapaActual);
                }
            }
        });

        inicioViewModel.cargarMapa();

        return root;
    }
}