package com.example.pfghiking;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
      //  Glide.with(holder.imgRuta.getContext()).load(rut.getImagen()).into(holder.imgRuta);
    }



    //para filtrar y mostrar ruta en SearchView
    public void filtrado(final String txtBuscar2) {
        if (txtBuscar2.length() == 0) {
            dataRutas.clear();
            dataRutas.addAll( dataOriginal );
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dataRutas.clear();
                List<ModelRuta> collection = dataRutas.stream()
                        .filter( i -> i.getNombre_ruta().toLowerCase().contains( txtBuscar2.toLowerCase() ) )
                        .collect( Collectors.toList() );
                dataRutas.addAll( collection );
            } else {
                //dataRutas.clear();
                for (ModelRuta ruta : dataOriginal) {
                    if (ruta.getNombre_ruta().toLowerCase().contains( txtBuscar2.toLowerCase() )) {
                    dataOriginal.add( ruta );
                }
            }
        }
    }
    notifyDataSetChanged();

    } //fin del filtrado







    @Override
    public int getItemCount( ) {
        return dataRutas.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView nombre_ruta;
        private TextView tvUsuario;
        private TextView distancia;
        private TextView desnivel;
        private TextView tiempo;
 //       private ImageView imgRuta;



        public RecyclerHolder(final View view) {
            super(view);
            nombre_ruta = view.findViewById( R.id.TV_name_Layout );
            tvUsuario = view.findViewById( R.id.TV_User_Layout );
            distancia = view.findViewById( R.id.TV_distRuta_Layout );
            desnivel = view.findViewById( R.id.TV_desnRuta_Layout );
            tiempo = view.findViewById( R.id.TV_timeRuta_Layout );
      //      imgRuta = view.findViewById( R.id.img_Ruta_Layout );


        }
    }
}
