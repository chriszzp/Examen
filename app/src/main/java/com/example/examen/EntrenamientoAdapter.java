/**
 * CLASE: EntrenamientoAdapter.java
 *
 * PROPÓSITO:
 * Adapter que conecta una lista de objetos Entrenamiento con un ListView.
 * Es el PUENTE entre los datos y la vista visual.
 *
 * PATRÓN DE DISEÑO:
 * Adapter Pattern - Convierte datos en vistas
 *
 * RESPONSABILIDADES:
 * 1. Recibe una lista de objetos Entrenamiento
 * 2. Para cada entrenamiento, crea (o reutiliza) una vista
 * 3. Rellena la vista con los datos del entrenamiento (icono + nombre)
 *
 * CÓMO FUNCIONA:
 * - Android llama a getView() por cada item visible
 * - getView() infla el layout item_entrenamiento.xml si es necesario
 * - Busca las vistas (ImageView, TextView) en el layout
 * - Establece los valores (icono, nombre) del entrenamiento actual
 * - Retorna la vista completa para que se muestre
 *
 * OPTIMIZACIÓN:
 * - Usa convertView para reutilizar vistas (no crea nuevas cada vez)
 * - Solo infla layout si convertView es null (primera vez)
 *
 * USO:
 * ListaEntrenamientosFragment usa este adapter para mostrar la lista
 */
package com.example.examen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Adapter personalizado para mostrar entrenamientos en un ListView.
 * Cada item muestra un icono y el nombre del entrenamiento.
 */
public class EntrenamientoAdapter extends ArrayAdapter<Entrenamiento> {

    // ==================== ATRIBUTOS ====================

    /** Context de la aplicación (necesario para inflar layouts) */
    private Context context;

    /** Lista de entrenamientos a mostrar */
    private List<Entrenamiento> entrenamientos;

    // ==================== CONSTRUCTOR ====================

    /**
     * Constructor del adapter.
     *
     * @param context Context de la aplicación
     * @param entrenamientos Lista de entrenamientos a mostrar
     */
    public EntrenamientoAdapter(@NonNull Context context, List<Entrenamiento> entrenamientos) {
        // Llamar al constructor padre
        // Parámetros: context, resource (0 porque usamos layout custom), objects
        super(context, 0, entrenamientos);

        this.context = context;
        this.entrenamientos = entrenamientos;
    }

    // ==================== MÉTODOS DEL ADAPTER ====================

    /**
     * Método CLAVE del adapter: Crea/actualiza la vista para un item específico.
     *
     * FLUJO:
     * 1. Verifica si puede reutilizar una vista existente (convertView)
     * 2. Si no existe, infla el layout item_entrenamiento.xml
     * 3. Obtiene el entrenamiento de la posición actual
     * 4. Busca las vistas (ImageView, TextView) en el layout
     * 5. Establece los valores del entrenamiento en las vistas
     * 6. Retorna la vista completa
     *
     * OPTIMIZACIÓN IMPORTANTE:
     * - convertView es una vista reciclada que Android puede reutilizar
     * - Si no es null, NO necesitamos inflar el layout de nuevo
     * - Esto hace que el scroll sea mucho más fluido
     *
     * @param position Posición del item en la lista (0, 1, 2, ...)
     * @param convertView Vista reciclada (puede ser null)
     * @param parent ViewGroup padre (el ListView)
     * @return Vista configurada para mostrar el item
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Intentar reutilizar la vista existente
        View listItem = convertView;

        // Si no hay vista para reutilizar, crear una nueva
        if (listItem == null) {
            // LayoutInflater convierte XML en objetos View
            // inflate() parámetros:
            // - R.layout.item_entrenamiento: layout a inflar
            // - parent: ViewGroup padre
            // - false: no adjuntar al padre aún (lo hace Android)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_entrenamiento, parent, false);
        }

        // Obtener el entrenamiento actual según la posición
        Entrenamiento entrenamiento = entrenamientos.get(position);

        // Buscar las vistas dentro del layout inflado
        // findViewById() busca vistas por su ID definido en el XML
        ImageView imageView = listItem.findViewById(R.id.icono_entrenamiento);
        TextView textViewNombre = listItem.findViewById(R.id.nombre_entrenamiento);

        // Establecer los valores del entrenamiento en las vistas
        // setImageResource() establece la imagen desde un resource ID
        imageView.setImageResource(entrenamiento.getIconoResId());

        // setText() establece el texto del TextView
        textViewNombre.setText(entrenamiento.getNombre());

        // Retornar la vista configurada
        return listItem;
    }
}

