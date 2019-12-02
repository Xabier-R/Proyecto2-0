package com.aar.app.proyectoLlodio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorCuento extends RecyclerView.Adapter<AdaptadorCuento.myViewHolder>{

    Context context;
    List<Cuento> mdata;
    OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onbtnPulsado(int position);
        void onCartaPulsada(int position, ImageView imagen);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdaptadorCuento(Context context, List<Cuento> mdata){
        this.context = context;
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_cuento, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.txtTitulo.setText(mdata.get(position).getTitulo());
        holder.txtDescripcion.setText(mdata.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView txtTitulo;
        TextView txtDescripcion;
        ImageView imagenLibro;


        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            imagen = itemView.findViewById(R.id.imgBorrar);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            imagenLibro = itemView.findViewById(R.id.imgLibro);


            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onbtnPulsado(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCartaPulsada(position, imagenLibro);
                        }
                    }
                }
            });
        }
    }
}
