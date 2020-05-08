package com.aar.app.proyectoLaudio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.myViewHolder> {

    Context context;
    List<Patrimonio> mdata;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position, ImageView img);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Adaptador(Context context, List<Patrimonio> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_patrimonio, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.imagen.setImageResource(mdata.get(position).getFondo());
        holder.txtNombre.setText(mdata.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView txtNombre;

        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            imagen = itemView.findViewById(R.id.cartaImagen);
            txtNombre = itemView.findViewById(R.id.nombre);

            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position, imagen);
                        }
                    }
                }
            });
        }


    }




}