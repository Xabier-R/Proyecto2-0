package com.aar.app.proyectoLlodio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorCarpetas extends RecyclerView.Adapter<AdaptadorCarpetas.myViewHolder>{

    Context context;
    List<Carpeta> mdata;
    OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onCarpetaPulsada(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdaptadorCarpetas(Context context, List<Carpeta> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_carpetas, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.txtTitulo.setText(mdata.get(position).getLugar());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;

        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCarpetaPulsada(position);
                        }
                    }
                }
            });
        }
    }
}
