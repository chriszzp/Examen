/**
 * CLASE: Entrenamiento.java
 *
 * PROPÓSITO:
 * Esta es la clase MODELO que representa un entrenamiento en la aplicación.
 * Es un POJO (Plain Old Java Object) que almacena los datos de cada entrenamiento.
 *
 * PATRÓN DE DISEÑO:
 * Sigue el patrón JavaBean con:
 * - Campos privados (encapsulación)
 * - Constructor público
 * - Getters y Setters para acceder/modificar los datos
 *
 * DATOS QUE ALMACENA:
 * - id: Identificador único del entrenamiento
 * - nombre: Nombre descriptivo del entrenamiento
 * - descripcion: Descripción detallada del entrenamiento
 * - duracion: Tiempo estimado (ej: "45 minutos")
 * - dificultad: Nivel de dificultad (Baja, Media, Alta)
 * - iconoResId: ID del recurso del icono asociado
 *
 * USO:
 * Esta clase se usa en toda la aplicación para pasar datos de entrenamientos
 * entre diferentes componentes (Fragments, Adapters, etc.)
 */
package com.example.examen;

public class Entrenamiento {
    // ==================== ATRIBUTOS ====================

    /** Identificador único del entrenamiento */
    private int id;

    /** Nombre del entrenamiento (ej: "Cardio Intenso") */
    private String nombre;

    /** Descripción detallada de qué incluye el entrenamiento */
    private String descripcion;

    /** Duración estimada del entrenamiento (ej: "45 minutos") */
    private String duracion;

    /** Nivel de dificultad: "Baja", "Media" o "Alta" */
    private String dificultad;

    /** ID del recurso drawable del icono (ej: android.R.drawable.ic_menu_directions) */
    private int iconoResId;

    // ==================== CONSTRUCTOR ====================

    /**
     * Constructor principal que inicializa todos los campos del entrenamiento.
     *
     * @param id Identificador único del entrenamiento
     * @param nombre Nombre descriptivo del entrenamiento
     * @param descripcion Descripción detallada de los ejercicios incluidos
     * @param duracion Tiempo estimado en formato texto (ej: "30 minutos")
     * @param dificultad Nivel de dificultad: "Baja", "Media" o "Alta"
     * @param iconoResId ID del recurso drawable del icono a mostrar
     */
    public Entrenamiento(int id, String nombre, String descripcion, String duracion, String dificultad, int iconoResId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.dificultad = dificultad;
        this.iconoResId = iconoResId;
    }

    // ==================== GETTERS Y SETTERS ====================

    /**
     * Obtiene el ID único del entrenamiento.
     * @return ID del entrenamiento
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del entrenamiento.
     * @param id Nuevo ID a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del entrenamiento.
     * @return Nombre del entrenamiento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del entrenamiento.
     * @param nombre Nuevo nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción completa del entrenamiento.
     * @return Descripción del entrenamiento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del entrenamiento.
     * @param descripcion Nueva descripción a asignar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la duración estimada del entrenamiento.
     * @return Duración en formato texto (ej: "45 minutos")
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Establece la duración del entrenamiento.
     * @param duracion Nueva duración a asignar
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    /**
     * Obtiene el nivel de dificultad del entrenamiento.
     * @return Dificultad: "Baja", "Media" o "Alta"
     */
    public String getDificultad() {
        return dificultad;
    }

    /**
     * Establece el nivel de dificultad del entrenamiento.
     * @param dificultad Nueva dificultad a asignar
     */
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    /**
     * Obtiene el ID del recurso del icono asociado al entrenamiento.
     * @return ID del recurso drawable (ej: android.R.drawable.ic_menu_directions)
     */
    public int getIconoResId() {
        return iconoResId;
    }

    /**
     * Establece el ID del recurso del icono.
     * @param iconoResId Nuevo ID del recurso drawable a asignar
     */
    public void setIconoResId(int iconoResId) {
        this.iconoResId = iconoResId;
    }
}

