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

    private List<ModelRuta> dataRutas = new ArrayList<>();
    private List<ModelRuta> dataOriginal;
    private final RecyclerViewClickListener listener; // para generar eventos en el recycler view

    public RecyclerRutaAdapter( List<ModelRuta> dataRutas, RecyclerViewClickListener listener){
        this.listener = listener;
        this.dataRutas = dataRutas;
        dataOriginal = new ArrayList<>();
        dataOriginal.addAll( dataRutas );
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.row_ruta_recycler, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view, listener);
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


    public static class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre_ruta;
        private TextView tvUsuario;
        private TextView distancia;
        private TextView desnivel;
        private TextView tiempo;
 //       private ImageView imgRuta;



        public RecyclerHolder(final View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            nombre_ruta = itemView.findViewById( R.id.TV_name_Layout );
            tvUsuario = itemView.findViewById( R.id.TV_User_Layout );
            distancia = itemView.findViewById( R.id.TV_distRuta_Layout );
            desnivel = itemView.findViewById( R.id.TV_desnRuta_Layout );
            tiempo = itemView.findViewById( R.id.TV_timeRuta_Layout );
      //      imgRuta = itemView.findViewById( R.id.img_Ruta_Layout );

            // para generar eventos en el RV
            itemView.setOnClickListener( this );


        }

        // para generar eventos en el RV
        @Override
        public void onClick(View v) {
            listener.onItemClick( v, getAdapterPosition() );
        }
    }



    //para generar eventos en el RV
    public interface RecyclerViewClickListener{
        void onItemClick(View view, int position);
    }



}
