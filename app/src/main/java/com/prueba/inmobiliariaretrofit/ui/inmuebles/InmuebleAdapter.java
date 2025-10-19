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

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.viewHolderInmueble> {
    private Context context;
    private List<Inmueble> listado;
    private LayoutInflater li;

    public InmuebleAdapter(Context context, List<Inmueble> listado, LayoutInflater li) {
        this.context = context;
        this.listado = listado;
        this.li = li;
    }

    @NonNull
    @Override
    public viewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_inmueble, parent, false);
        return new viewHolderInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderInmueble holder, int position) {
        Inmueble inmuebleActual = listado.get(position);
        holder.direccion.setText("Dirección: " + inmuebleActual.getDireccion());
        holder.precio.setText("Valor: " + inmuebleActual.getValor());
        Glide.with(context)
                .load(ApiClient.getBaseUrl() + inmuebleActual.getImagen())
                //.placeholder(R.drawable.)
                .error("null")
                .into(holder.imagen);
        ((viewHolderInmueble) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmuebleBundle", inmuebleActual);
                //Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main).navigate(R.id.detalleInmueble, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class viewHolderInmueble extends RecyclerView.ViewHolder {

        TextView direccion, precio;
        ImageView imagen;

        public viewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvPrecio);
            imagen = itemView.findViewById(R.id.imageView);
        }
    }
}

