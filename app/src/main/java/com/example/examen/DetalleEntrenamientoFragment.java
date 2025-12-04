package com.example.examen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetalleEntrenamientoFragment extends Fragment {
    private static final String ARG_ID_ENTRENAMIENTO = "id_entrenamiento";

    private int idEntrenamiento;
    private ImageView iconoDetalle;
    private TextView nombreDetalle;
    private TextView descripcionDetalle;
    private TextView duracionDetalle;
    private TextView dificultadDetalle;

    public static DetalleEntrenamientoFragment newInstance(int idEntrenamiento) {
        DetalleEntrenamientoFragment fragment = new DetalleEntrenamientoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_ENTRENAMIENTO, idEntrenamiento);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idEntrenamiento = getArguments().getInt(ARG_ID_ENTRENAMIENTO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_entrenamiento, container, false);

        iconoDetalle = view.findViewById(R.id.icono_detalle);
        nombreDetalle = view.findViewById(R.id.nombre_detalle);
        descripcionDetalle = view.findViewById(R.id.descripcion_detalle);
        duracionDetalle = view.findViewById(R.id.duracion_detalle);
        dificultadDetalle = view.findViewById(R.id.dificultad_detalle);

        cargarDatos();

        return view;
    }

    private void cargarDatos() {
        Entrenamiento entrenamiento = ListaEntrenamientosFragment.getEntrenamientoPorId(idEntrenamiento);

        if (entrenamiento != null) {
            iconoDetalle.setImageResource(entrenamiento.getIconoResId());
            nombreDetalle.setText(entrenamiento.getNombre());
            descripcionDetalle.setText(entrenamiento.getDescripcion());
            duracionDetalle.setText("Duración: " + entrenamiento.getDuracion());
            dificultadDetalle.setText("Dificultad: " + entrenamiento.getDificultad());
        }
    }
}

