package com.aar.app.proyectoLlodio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class FragmentoLobo extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private OnFragmentInteractionListener mListener;
    public String nombreActividad;
    private ImageView imagenLobo;

    public FragmentoLobo() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragmento_lobo, container, false);

        Button btn = view.findViewById(R.id.btnLobo);
        imagenLobo = view.findViewById(R.id.imagenLobo);

        pestanear();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentPulsado(imagenLobo);

            }
        });

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