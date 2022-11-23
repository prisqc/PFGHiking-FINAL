package com.example.pfghiking;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

public class RecyclerRutaAdapter  extends RecyclerView.Adapter<RecyclerRutaAdapter.RecyclerHolder> {

    private List<ModelRuta> dataRutas;
    private List<ModelRuta> dataOriginal;
    private Context mContext = null;


    public RecyclerRutaAdapter(List<ModelRuta> dataRutas, Context context ){
        this.dataRutas = dataRutas;
        mContext = context;
        dataOriginal = this.dataRutas;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.row_ruta_recycler, parent, false );
        RecyclerHolder holder = new RecyclerHolder( view );
        if (dataOriginal.size() == 0) {
            dataOriginal.addAll( dataRutas );
        }
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
        Glide.with( holder.imgRuta.getContext() ).load( rut.getImagen() ).into( holder.imgRuta );

       /* String imagen = rut.getImagen();
        try{
            //ModelRuta sample = dataRutas.get( holder.getAdapterPosition() );
            if(imagen != null){
                Glide.with(mContext)
                        .load( imagen ).error(R.id.img_CR).into( holder.imgRuta );
            } else{
                Glide.clear( holder.imgRuta );
            }
        }catch (Exception e){
            Log.d("Exception", "e: " + e);
        }*/



    }



    //**********************************************************************************************

    public void filtrado(String txtbuscar) {
        int longitud = txtbuscar.length();
        if (longitud == 0) {
            dataRutas.clear();
            dataRutas.addAll( dataOriginal );
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<ModelRuta> colection = dataRutas.stream()
                        .filter( i -> i.getNombre_ruta().toLowerCase().contains( txtbuscar.toLowerCase() ) )
                        .collect( Collectors.toList() );
                dataRutas.clear();
                dataRutas.addAll( colection );
            } else {
                for (ModelRuta r : dataOriginal) {
                    if (r.getNombre_ruta().toLowerCase().contains( txtbuscar.toLowerCase() )) ;
                    dataRutas.add( r );
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataRutas.size();
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder {

        private TextView nombre_ruta;
        private TextView tvUsuario;
        private TextView distancia;
        private TextView desnivel;
        private TextView tiempo;
        private ImageView imgRuta;


        public RecyclerHolder(final View itemView) {
            super( itemView );
            nombre_ruta = itemView.findViewById( R.id.TV_name_Layout );
            tvUsuario = itemView.findViewById( R.id.TV_User_Layout );
            distancia = itemView.findViewById( R.id.TV_distRuta_Layout );
            desnivel = itemView.findViewById( R.id.TV_desnRuta_Layout );
            tiempo = itemView.findViewById( R.id.TV_timeRuta_Layout );
            imgRuta = itemView.findViewById( R.id.img_Ruta_Layout );


        }

    } //Fin de clase Recycler holder



}

 //FIN DE LA CLASE RecyclerRutaAdapter
