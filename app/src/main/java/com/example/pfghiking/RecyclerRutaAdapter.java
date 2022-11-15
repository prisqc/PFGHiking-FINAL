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

    private List<ModelRuta> dataRutas;
    private List<ModelRuta> dataOriginal;


    public RecyclerRutaAdapter( List<ModelRuta> dataRutas){
        this.dataRutas = dataRutas;
        dataOriginal = new ArrayList<>();
        dataOriginal.addAll( dataRutas );
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.row_ruta_recycler, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRutaAdapter.RecyclerHolder holder, int position) {

        ModelRuta rut = dataRutas.get( position );
        holder.nombre_ruta.setText( rut.getNombre_ruta() );
        holder.tvUsuario.setText( rut.getUsers().getEmail() );
        holder.distancia.setText( rut.getDistancia() );
        holder.desnivel.setText( rut.getDesnivel() );
        holder.tiempo.setText( rut.getTiempo() );
        Glide.with(holder.imgRuta.getContext()).load(rut.getImagen()).into(holder.imgRuta);



    }



    @Override
    public int getItemCount( ) {
        return dataRutas.size();
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView nombre_ruta;
        private TextView tvUsuario;
        private TextView distancia;
        private TextView desnivel;
        private TextView tiempo;
        private ImageView imgRuta;



        public RecyclerHolder(final View item) {
            super(item);
            nombre_ruta = item.findViewById( R.id.TV_name_Layout );
            tvUsuario = item.findViewById( R.id.TV_User_Layout );
            distancia = item.findViewById( R.id.TV_distRuta_Layout );
            desnivel = item.findViewById( R.id.TV_desnRuta_Layout );
            tiempo = item.findViewById( R.id.TV_timeRuta_Layout );
            imgRuta = itemView.findViewById( R.id.img_Ruta_Layout );



        }





    } //Fin de clase Recycler holder



} //FIN DE LA CLASE RecyclerRutaAdapter
