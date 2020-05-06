package com.aar.app.proyectoLlodio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper;


public class Fragmento_ayuda extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private OnFragmentInteractionListener mListener;
    public String nombreActividad,str;
    private ImageView imagenLobo;
    private TextView texto;
    private LinearLayout ayuda;

    //BBDD
    private ActividadesSQLiteHelper activiades;
    private SQLiteDatabase db;

    public Fragmento_ayuda() {
        // Required empty public constructor

    }
    public Fragmento_ayuda(String str) {
        this.str=str;

    }
    public FragmentoLobo newInstance(String nombreActividad) {
        FragmentoLobo fragment = new FragmentoLobo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, nombreActividad);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombreActividad = getArguments().getString(ARG_PARAM1);



        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_ayuda, container, false);

        ayuda = view.findViewById(R.id.ayuda);
        imagenLobo = view.findViewById(R.id.imagenLobo);
        texto = view.findViewById(R.id.texto);

        texto.setText(str);
        pestanear();


        return view;


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentPulsado(ImageView imagen);
    }

    public ImageView getImagenLobo() {
        return imagenLobo;
    }


    //metodo  para iniciar animacion de pestañeo en el lobo
    public void pestanear() {

        imagenLobo.setImageResource(R.drawable.animation_list);

        AnimationDrawable loboParpadeo = (AnimationDrawable) imagenLobo.getDrawable();
        loboParpadeo.start();
    }


}
