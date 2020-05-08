package com.aar.app.proyectoLlodio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
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

    //Interface
    public interface OnItemClickListener {
        void onbtnPulsado(int position);
        void onCartaPulsada(int position);
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


        //decode base64 string to image
        Bitmap bitmap = decodeBase64(mdata.get(position).getFotoSacada());
        BitmapDrawable ob = new BitmapDrawable(context.getResources(), bitmap);
        holder.imgSacada.setBackground(ob);
    }

    //Metodo que devuelve el tamaño de la lista
    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imagenBorrar;
        ImageView imgSacada;
        TextView txtTitulo;
        TextView txtDescripcion;
        ImageView imagenLibro;


        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            imgSacada = itemView.findViewById(R.id.imgLibro);
            imagenBorrar = itemView.findViewById(R.id.imgBorrar);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);

            //Agrego el listener onbtnPulsado a la imagenBorrar del Cuento
            imagenBorrar.setOnClickListener(new View.OnClickListener() {
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

            //Agrego el listener onCartaPulsada al Cuento de la lista
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCartaPulsada(position);
                        }
                    }
                }
            });
        }
    }

    //Metodo que decodifica de base64 a imagen
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}