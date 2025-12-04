/**
 * CLASE: IconoAdapter.java
 *
 * PROPÓSITO:
 * Adapter para mostrar los iconos disponibles en un GridView dentro del diálogo
 * de crear entrenamiento. Similar a EntrenamientoAdapter pero con funcionalidad
 * adicional para manejar selección de iconos.
 *
 * DIFERENCIAS CON EntrenamientoAdapter:
 * - Usa BaseAdapter (más flexible) en lugar de ArrayAdapter
 * - Maneja SELECCIÓN de items (cuál icono está seleccionado)
 * - Cambia la apariencia visual según si está seleccionado o no
 * - Se usa con GridView (cuadrícula) en lugar de ListView
 *
 * FUNCIONALIDADES ESPECIALES:
 * - Destacado visual del icono seleccionado (opacidad 100%)
 * - Iconos no seleccionados con opacidad reducida (60%)
 * - Método para obtener el icono seleccionado
 *
 * USO:
 * NuevoEntrenamientoDialogFragment usa este adapter para el GridView
 * que muestra los 12 iconos disponibles.
 */
package com.example.examen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter personalizado para mostrar iconos en un GridView con selección.
 * Permite seleccionar un icono y lo destaca visualmente.
 */
public class IconoAdapter extends BaseAdapter {

    // ==================== ATRIBUTOS ====================

    /** Context de la aplicación */
    private Context context;

    /** Lista de iconos disponibles para mostrar */
    private List<IconoEntrenamiento> iconos;

    /** Posición del icono actualmente seleccionado (-1 si ninguno) */
    private int iconoSeleccionado = -1;

    // ==================== CONSTRUCTOR ====================

    /**
     * Constructor del adapter.
     *
     * @param context Context de la aplicación
     * @param iconos Lista de IconoEntrenamiento a mostrar
     */
    public IconoAdapter(Context context, List<IconoEntrenamiento> iconos) {
        this.context = context;
        this.iconos = iconos;
    }

    // ==================== MÉTODOS OBLIGATORIOS DE BaseAdapter ====================

    /**
     * Retorna el número total de iconos en la lista.
     *
     * @return Cantidad de iconos
     */
    @Override
    public int getCount() {
        return iconos.size();
    }

    /**
     * Retorna el objeto en la posición especificada.
     *
     * @param position Posición del item
     * @return Objeto IconoEntrenamiento en esa posición
     */
    @Override
    public Object getItem(int position) {
        return iconos.get(position);
    }

    /**
     * Retorna el ID del item en la posición especificada.
     * En este caso, usamos la posición como ID.
     *
     * @param position Posición del item
     * @return ID del item (mismo que position)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    // ==================== MÉTODO PRINCIPAL ====================

    /**
     * Crea/actualiza la vista para un icono específico.
     * Similar a getView de EntrenamientoAdapter pero con lógica de selección.
     *
     * FLUJO:
     * 1. Reutiliza vista existente o infla nueva
     * 2. Obtiene el IconoEntrenamiento de la posición
     * 3. Busca las vistas (ImageView, TextView, container)
     * 4. Establece la imagen y el nombre
     * 5. Aplica estilos según si está seleccionado o no
     * 6. Retorna la vista configurada
     *
     * ESTILOS DE SELECCIÓN:
     * - Seleccionado: Opacidad 100%, fondo destacado
     * - No seleccionado: Opacidad 60%, sin fondo
     *
     * @param position Posición del icono en la lista
     * @param convertView Vista reciclada (puede ser null)
     * @param parent ViewGroup padre (el GridView)
     * @return Vista configurada para mostrar el icono
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        // Si no hay vista para reutilizar, crear una nueva
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflar el layout de cada celda del grid
            view = inflater.inflate(R.layout.item_icono_selector, parent, false);
        }

        // Obtener el IconoEntrenamiento actual
        IconoEntrenamiento icono = iconos.get(position);

        // Buscar las vistas en el layout
        ImageView imageView = view.findViewById(R.id.imagen_icono);
        TextView textView = view.findViewById(R.id.nombre_icono);
        View containerView = view.findViewById(R.id.icono_container);

        // Establecer la imagen del icono
        imageView.setImageResource(icono.getIconoResId());

        // Establecer el nombre del icono
        textView.setText(icono.getNombre());

        // Aplicar estilos según si está seleccionado
        if (position == iconoSeleccionado) {
            // ICONO SELECCIONADO:
            // - Fondo destacado para que resalte
            containerView.setBackgroundResource(android.R.drawable.btn_default);
            // - Opacidad al 100% (completamente visible)
            view.setAlpha(1.0f);
        } else {
            // ICONO NO SELECCIONADO:
            // - Sin fondo (transparente)
            containerView.setBackgroundResource(0);
            // - Opacidad al 60% (aspecto desactivado)
            view.setAlpha(0.6f);
        }

        return view;
    }

    // ==================== MÉTODOS DE SELECCIÓN ====================

    /**
     * Establece qué icono está seleccionado y actualiza la vista.
     *
     * @param position Posición del icono a seleccionar
     */
    public void setIconoSeleccionado(int position) {
        this.iconoSeleccionado = position;
        // notifyDataSetChanged() le dice al adapter que actualice todas las vistas
        // Esto hace que se vuelva a llamar getView() para cada item visible
        notifyDataSetChanged();
    }

    /**
     * Obtiene la posición del icono actualmente seleccionado.
     *
     * @return Posición del icono seleccionado (-1 si ninguno)
     */
    public int getIconoSeleccionado() {
        return iconoSeleccionado;
    }

    /**
     * Obtiene el objeto IconoEntrenamiento actualmente seleccionado.
     * Si no hay ninguno seleccionado, retorna el primero por defecto.
     *
     * @return IconoEntrenamiento seleccionado o el primero si no hay selección
     */
    public IconoEntrenamiento getIconoSeleccionadoItem() {
        // Verificar que la posición seleccionada sea válida
        if (iconoSeleccionado >= 0 && iconoSeleccionado < iconos.size()) {
            return iconos.get(iconoSeleccionado);
        }
        // Si no hay selección válida, retornar el primer icono
        return iconos.get(0);
    }
}

