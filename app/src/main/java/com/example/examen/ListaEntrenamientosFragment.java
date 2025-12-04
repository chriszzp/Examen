package com.example.examen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class ListaEntrenamientosFragment extends Fragment {
    private ListView listView;
    private EntrenamientoAdapter adapter;
    private static List<Entrenamiento> entrenamientos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_entrenamientos, container, false);

        listView = view.findViewById(R.id.lista_entrenamientos);

        // Inicializar lista de entrenamientos solo si está vacía
        if (entrenamientos == null) {
            entrenamientos = new ArrayList<>();
            inicializarEntrenamientos();
        }

        adapter = new EntrenamientoAdapter(getContext(), entrenamientos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Entrenamiento entrenamiento = entrenamientos.get(position);
            mostrarDetalle(entrenamiento.getId());
        });

        return view;
    }

    private void inicializarEntrenamientos() {
        entrenamientos.add(new Entrenamiento(1, "Cardio Intenso",
            "Ejercicios cardiovasculares de alta intensidad para mejorar la resistencia",
            "45 minutos", "Alta", android.R.drawable.ic_menu_directions));

        entrenamientos.add(new Entrenamiento(2, "Fuerza Total",
            "Entrenamiento de fuerza para todos los grupos musculares",
            "60 minutos", "Media", android.R.drawable.ic_menu_manage));

        entrenamientos.add(new Entrenamiento(3, "Yoga Relajante",
            "Sesión de yoga para flexibilidad y relajación mental",
            "30 minutos", "Baja", android.R.drawable.ic_menu_gallery));

        entrenamientos.add(new Entrenamiento(4, "HIIT Extremo",
            "Entrenamiento de intervalos de alta intensidad para quemar calorías",
            "25 minutos", "Alta", android.R.drawable.ic_menu_rotate));
    }

    private void mostrarDetalle(int idEntrenamiento) {
        DetalleEntrenamientoFragment detalleFragment = DetalleEntrenamientoFragment.newInstance(idEntrenamiento);

        // Verificar si estamos en landscape (dos paneles)
        boolean isDualPane = getActivity().findViewById(R.id.fragment_detalle) != null;

        if (isDualPane) {
            // Landscape: mostrar en el segundo contenedor
            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_detalle, detalleFragment)
                .commit();
        } else {
            // Portrait: reemplazar el fragment actual
            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detalleFragment)
                .addToBackStack(null)
                .commit();
        }
    }

    public void agregarEntrenamiento(Entrenamiento entrenamiento) {
        entrenamientos.add(entrenamiento);
        adapter.notifyDataSetChanged();
    }

    public static Entrenamiento getEntrenamientoPorId(int id) {
        if (entrenamientos != null) {
            for (Entrenamiento e : entrenamientos) {
                if (e.getId() == id) {
                    return e;
                }
            }
        }
        return null;
    }

    public int generarNuevoId() {
        int maxId = 0;
        for (Entrenamiento e : entrenamientos) {
            if (e.getId() > maxId) {
                maxId = e.getId();
            }
        }
        return maxId + 1;
    }
}

