package com.example.examen;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class NuevoEntrenamientoDialogFragment extends DialogFragment {

    public interface OnEntrenamientoAddedListener {
        void onEntrenamientoAdded(Entrenamiento entrenamiento);
    }

    private EditText editNombre;
    private EditText editDescripcion;
    private EditText editDuracion;
    private Spinner spinnerDificultad;
    private GridView gridIconos;
    private Button btnCancelar;
    private Button btnAnadir;

    private IconoAdapter iconoAdapter;
    private List<IconoEntrenamiento> iconosDisponibles;

    private OnEntrenamientoAddedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_nuevo_entrenamiento, container, false);

        editNombre = view.findViewById(R.id.edit_nombre);
        editDescripcion = view.findViewById(R.id.edit_descripcion);
        editDuracion = view.findViewById(R.id.edit_duracion);
        spinnerDificultad = view.findViewById(R.id.spinner_dificultad);
        gridIconos = view.findViewById(R.id.grid_iconos);
        btnCancelar = view.findViewById(R.id.btn_cancelar);
        btnAnadir = view.findViewById(R.id.btn_anadir);

        // Configurar spinner de dificultad
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dificultades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultad.setAdapter(adapter);

        // Configurar GridView de iconos
        iconosDisponibles = IconoEntrenamiento.getIconosDisponibles();
        iconoAdapter = new IconoAdapter(getContext(), iconosDisponibles);
        gridIconos.setAdapter(iconoAdapter);

        // Seleccionar el primer icono por defecto
        iconoAdapter.setIconoSeleccionado(0);

        // Manejar click en iconos
        gridIconos.setOnItemClickListener((parent, view1, position, id) -> {
            iconoAdapter.setIconoSeleccionado(position);
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        btnAnadir.setOnClickListener(v -> {
            if (validarCampos()) {
                crearEntrenamiento();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    private boolean validarCampos() {
        if (editNombre.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editDescripcion.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "La descripción es obligatoria", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editDuracion.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "La duración es obligatoria", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void crearEntrenamiento() {
        String nombre = editNombre.getText().toString().trim();
        String descripcion = editDescripcion.getText().toString().trim();
        String duracion = editDuracion.getText().toString().trim();
        String dificultad = spinnerDificultad.getSelectedItem().toString();

        // Obtener el icono seleccionado
        IconoEntrenamiento iconoSeleccionado = iconoAdapter.getIconoSeleccionadoItem();
        int iconoResId = iconoSeleccionado.getIconoResId();

        // Obtener el fragment de lista para generar un nuevo ID
        ListaEntrenamientosFragment listaFragment =
            (ListaEntrenamientosFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (listaFragment == null) {
            listaFragment = (ListaEntrenamientosFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragment_lista);
        }

        if (listaFragment != null) {
            int nuevoId = listaFragment.generarNuevoId();

            Entrenamiento nuevoEntrenamiento = new Entrenamiento(
                nuevoId,
                nombre,
                descripcion,
                duracion,
                dificultad,
                iconoResId
            );

            listaFragment.agregarEntrenamiento(nuevoEntrenamiento);

            Toast.makeText(getContext(), "Entrenamiento añadido correctamente", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    public void setOnEntrenamientoAddedListener(OnEntrenamientoAddedListener listener) {
        this.listener = listener;
    }
}

