package com.example.pfghiking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerRutaAdapter  extends RecyclerView.Adapter<RecyclerRutaAdapter.RecyclerHolder> {

    List<ModelRuta> dataRutas;
    List<ModelRuta> dataOriginal;

    public RecyclerRutaAdapter( List<ModelRuta> dataRutas){
        this.dataRutas = dataRutas;
        dataOriginal = new ArrayList<>();
        dataOriginal.addAll( dataRutas );
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.row_ruta_recycler, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

        ModelRuta ruta = dataRutas.get( position );
        holder.nombre_ruta.setText( ruta.getNombre_ruta() );
        holder.distancia.setText( ruta.getDistancia() );
        holder.desnivel.setText( ruta.getDesnivel() );
        holder.tiempo.setText( ruta.getTiempo() );
        holder.tvUsuario.setText( ruta.getUsers().getNombre() );
        Glide.with(holder.imgRuta.getContext()).load(ruta.getImagen()).into(holder.imgRuta);
    }

    @Override
    public int getItemCount( ) {
        return dataRutas.size();
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView nombre_ruta;
        private TextView distancia;
        private TextView desnivel;
        private TextView tiempo;
        private ImageView imgRuta;
        private TextView tvUsuario;

        public RecyclerHolder(final View view) {
            super(view);
            nombre_ruta = (TextView ) itemView.findViewById( R.id.TV_name_Layout );
            distancia = (TextView ) itemView.findViewById( R.id.TV_distRuta_Layout );
            desnivel = (TextView ) itemView.findViewById( R.id.TV_desnRuta_Layout );
            tiempo = (TextView ) itemView.findViewById( R.id.TV_timeRuta_Layout );
            imgRuta = (ImageView ) itemView.findViewById( R.id.img_Ruta_Layout );
            tvUsuario = (TextView ) itemView.findViewById( R.id.TV_User_Layout );

        }
    }
}
