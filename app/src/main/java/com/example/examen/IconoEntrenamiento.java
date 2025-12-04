/**
 * CLASE: IconoEntrenamiento.java
 *
 * PROPÓSITO:
 * Gestiona los iconos disponibles para asociar a los entrenamientos.
 * Proporciona una lista de iconos temáticos de gimnasio y ejercicio.
 *
 * FUNCIONALIDAD:
 * - Define 12 iconos relacionados con fitness disponibles para seleccionar
 * - Cada icono tiene un nombre descriptivo y una descripción de uso
 * - Método estático para obtener todos los iconos disponibles
 * - Método estático para buscar un icono por su ID de recurso
 *
 * PATRÓN DE DISEÑO:
 * Clase de utilidad con métodos estáticos (Factory Pattern)
 * Proporciona datos predefinidos para el selector de iconos
 *
 * USO:
 * - El diálogo de nuevo entrenamiento usa getIconosDisponibles() para mostrar opciones
 * - El usuario selecciona un icono al crear un entrenamiento
 */
package com.example.examen;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona los iconos disponibles para los entrenamientos.
 * Proporciona 12 iconos temáticos de gimnasio y ejercicio.
 */
public class IconoEntrenamiento {

    // ==================== ATRIBUTOS ====================

    /** ID del recurso drawable del icono */
    private int iconoResId;

    /** Nombre descriptivo corto del icono (ej: "Cardio/Running") */
    private String nombre;

    /** Descripción del uso recomendado del icono */
    private String descripcion;

    // ==================== CONSTRUCTOR ====================

    /**
     * Constructor que inicializa un icono de entrenamiento.
     *
     * @param iconoResId ID del recurso drawable del icono
     * @param nombre Nombre descriptivo corto del icono
     * @param descripcion Descripción del uso recomendado
     */
    public IconoEntrenamiento(int iconoResId, String nombre, String descripcion) {
        this.iconoResId = iconoResId;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ==================== GETTERS ====================

    /**
     * Obtiene el ID del recurso drawable del icono.
     * @return ID del recurso (ej: android.R.drawable.ic_menu_directions)
     */
    public int getIconoResId() {
        return iconoResId;
    }

    /**
     * Obtiene el nombre descriptivo del icono.
     * @return Nombre corto del icono (ej: "Cardio/Running")
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripción de uso del icono.
     * @return Descripción de cuándo usar este icono
     */
    public String getDescripcion() {
        return descripcion;
    }

    // ==================== MÉTODOS ESTÁTICOS (FACTORY) ====================

    /**
     * Retorna la lista completa de iconos disponibles para entrenamientos.
     * Esta lista incluye 12 iconos temáticos relacionados con fitness y ejercicio.
     *
     * ICONOS INCLUIDOS:
     * 1. Cardio/Running - Para ejercicios cardiovasculares
     * 2. Fuerza/Pesas - Para entrenamiento de fuerza
     * 3. Circuito/HIIT - Para entrenamientos de intervalos
     * 4. Navegación/Outdoor - Para actividades al aire libre
     * 5. Yoga/Estiramiento - Para flexibilidad
     * 6. Localización/GPS - Para ejercicios con tracking
     * 7. Velocidad/Sprint - Para entrenamientos de velocidad
     * 8. Rutina Diaria - Para entrenamientos programados
     * 9. Personalizado - Para entrenamientos customizados
     * 10. Visualización - Para seguimiento de progreso
     * 11. Crecimiento/Progreso - Para medir mejoras
     * 12. Objetivo/Meta - Para entrenamientos con metas
     *
     * @return Lista de IconoEntrenamiento con todos los iconos disponibles
     */
    public static List<IconoEntrenamiento> getIconosDisponibles() {
        List<IconoEntrenamiento> iconos = new ArrayList<>();

        // Icono 1: Cardio/Running
        // Icono de dirección/flecha - Ideal para actividades cardiovasculares
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_directions,
            "Cardio/Running",
            "Para ejercicios cardiovasculares y running"
        ));

        // Icono 2: Fuerza/Pesas
        // Icono de gestión/configuración - Representa entrenamiento estructurado
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_manage,
            "Fuerza/Pesas",
            "Para entrenamiento de fuerza y pesas"
        ));

        // Icono 3: Circuito/HIIT
        // Icono de rotación - Perfecto para entrenamientos de circuito
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_rotate,
            "Circuito/HIIT",
            "Para entrenamientos de circuito e intervalos"
        ));

        // Icono 4: Navegación/Outdoor
        // Icono de brújula - Para actividades al aire libre
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_compass,
            "Navegación/Outdoor",
            "Para actividades al aire libre"
        ));

        // Icono 5: Yoga/Estiramiento
        // Icono de galería - Representa variedad y flexibilidad
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_gallery,
            "Yoga/Estiramiento",
            "Para yoga, pilates y flexibilidad"
        ));

        // Icono 6: Localización/GPS
        // Icono de ubicación - Para ejercicios con tracking GPS
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_mylocation,
            "Localización/GPS",
            "Para ejercicios con tracking GPS"
        ));

        // Icono 7: Velocidad/Sprint
        // Icono de enviar/flecha - Representa velocidad
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_send,
            "Velocidad/Sprint",
            "Para entrenamientos de velocidad"
        ));

        // Icono 8: Rutina Diaria
        // Icono de calendario - Para entrenamientos programados
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_today,
            "Rutina Diaria",
            "Para entrenamientos programados"
        ));

        // Icono 9: Personalizado
        // Icono de preferencias - Para entrenamientos customizados
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_preferences,
            "Personalizado",
            "Para entrenamientos personalizados"
        ));

        // Icono 10: Visualización
        // Icono de vista - Para seguimiento y análisis
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_view,
            "Visualización",
            "Para seguimiento de progreso"
        ));

        // Icono 11: Crecimiento/Progreso
        // Icono de subida - Para medir mejoras
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_upload,
            "Crecimiento/Progreso",
            "Para medir tu progreso"
        ));

        // Icono 12: Objetivo/Meta
        // Icono de establecer como - Para entrenamientos con metas
        iconos.add(new IconoEntrenamiento(
            android.R.drawable.ic_menu_set_as,
            "Objetivo/Meta",
            "Para entrenamientos con metas"
        ));

        return iconos;
    }

    /**
     * Busca y retorna un icono específico por su ID de recurso.
     * Si no se encuentra el icono, retorna el primero por defecto.
     *
     * @param iconoResId ID del recurso drawable a buscar
     * @return IconoEntrenamiento encontrado, o el primero si no existe
     */
    public static IconoEntrenamiento getIconoPorId(int iconoResId) {
        // Iterar por todos los iconos disponibles
        for (IconoEntrenamiento icono : getIconosDisponibles()) {
            // Si encontramos el icono buscado, lo retornamos
            if (icono.getIconoResId() == iconoResId) {
                return icono;
            }
        }

        // Si no se encuentra, retornar el primer icono por defecto
        return getIconosDisponibles().get(0);
    }
}

