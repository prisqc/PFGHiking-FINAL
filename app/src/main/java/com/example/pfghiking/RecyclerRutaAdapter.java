package com.example.pfghiking;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    final RecyclerRutaAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ModelRuta item);
    }

    public RecyclerRutaAdapter(List<ModelRuta> dataRutas, Context context , RecyclerRutaAdapter.OnItemClickListener listener ){
        this.dataRutas = dataRutas;
        mContext = context;
        dataOriginal = this.dataRutas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.row_ruta_recycler_del, parent, false );
        RecyclerHolder holder = new RecyclerHolder( view );
        if (dataOriginal.size() == 0) {
            dataOriginal.addAll( dataRutas );
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRutaAdapter.RecyclerHolder holder, int position) {

        holder.binData(dataRutas.get( position ));

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
        public RelativeLayout layoutDelete; //PARA BORRA ITEM RV


        public RecyclerHolder(final View itemView) {
            super( itemView );
            nombre_ruta = itemView.findViewById( R.id.TV_name_Layout );
            tvUsuario = itemView.findViewById( R.id.TV_User_Layout );
            distancia = itemView.findViewById( R.id.TV_distRuta_Layout );
            desnivel = itemView.findViewById( R.id.TV_desnRuta_Layout );
            tiempo = itemView.findViewById( R.id.TV_timeRuta_Layout );
            imgRuta = itemView.findViewById( R.id.img_Ruta_Layout );
            layoutDelete = itemView.findViewById( R.id.LayoutBorrar ); //PARA BORRA ITEM RV

        }

        public void binData(ModelRuta modelRuta) {
            nombre_ruta.setText( modelRuta.getNombre_ruta() );
            tvUsuario.setText( modelRuta.getUsers().getEmail() );
            distancia.setText( modelRuta.getDistancia() );
            desnivel.setText( modelRuta.getDesnivel() );
            tiempo.setText( modelRuta.getTiempo() );
            Glide.with( imgRuta.getContext() ).load( modelRuta.getImagen() ).into( imgRuta );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick( modelRuta );
                }
            } );
        }
    } //Fin de clase Recycler holder


    public void removeItem(int position){
        dataRutas.remove( position );
        notifyItemRemoved( position );
    }



}

 //FIN DE LA CLASE RecyclerRutaAdapter
