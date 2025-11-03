package com.prueba.inmobiliariaretrofit.ui.inmuebles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.modelo.Inmueble;
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {


    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;


    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater) {

        this.listaInmuebles = listaInmuebles;
        this.listaInmuebles.sort((o1, o2) -> o2.getIdInmueble() - o1.getIdInmueble());
        this.context = context;
        this.inflater = inflater;

    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolderInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmActual = listaInmuebles.get(position);
        holder.direccion.setText(inmActual.getDireccion());
        holder.precio.setText(inmActual.getValor() + "");
        holder.ambientes.setText("ambientes:" + inmActual.getAmbientes());
        Glide.with(context)
                .load("https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/" + inmActual.getImagen())
                .placeholder(null)
                .error("null")
                .into(holder.portada);
        ((ViewHolderInmueble) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmActual);
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main).navigate(R.id.detalleInmueble, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolderInmueble extends RecyclerView.ViewHolder {

        private TextView direccion, precio, ambientes;
        private ImageView portada;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvPrecio);
            portada = itemView.findViewById(R.id.imgInmueble);
            ambientes = itemView.findViewById(R.id.tvAmbientes);
        }


    }
}
