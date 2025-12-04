/**
 * FRAGMENT: ListaEntrenamientosFragment.java
 *
 * PROPÓSITO:
 * Fragment que muestra la lista de entrenamientos disponibles en un ListView.
 * Es el "Master" del patrón Master-Detail.
 *
 * RESPONSABILIDADES:
 * 1. Mantener la lista de entrenamientos (variable estática compartida)
 * 2. Mostrar los entrenamientos en un ListView usando EntrenamientoAdapter
 * 3. Manejar clicks en items de la lista
 * 4. Decidir cómo mostrar detalles según orientación (portrait/landscape)
 * 5. Permitir agregar nuevos entrenamientos
 * 6. Proporcionar métodos para buscar y generar IDs
 *
 * PATRÓN DE DISEÑO:
 * - Master-Detail: Este es el "Master" (lista maestra)
 * - Observer: El adapter se actualiza automáticamente con notifyDataSetChanged()
 *
 * FLUJO DE NAVEGACIÓN:
 * Portrait: Click en item → Reemplaza este fragment con DetalleFragment
 * Landscape: Click en item → Actualiza el panel derecho (fragment_detalle)
 *
 * USADO EN:
 * - MainActivity (portrait): En fragment_container
 * - MainActivity (landscape): En fragment_lista (panel izquierdo)
 */
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

    // ==================== ATRIBUTOS ====================

    /**
     * ListView que muestra la lista de entrenamientos.
     * Se conecta con el EntrenamientoAdapter para mostrar los datos.
     */
    private ListView listView;

    /**
     * Adapter que conecta los datos (entrenamientos) con la vista (ListView).
     * Responsable de crear y actualizar cada fila de la lista.
     */
    private EntrenamientoAdapter adapter;

    /**
     * Lista ESTÁTICA de entrenamientos.
     *
     * ¿POR QUÉ ESTÁTICA?
     * - Se comparte entre todas las instancias del fragment
     * - Persiste cuando el fragment se recrea (rotación, navegación)
     * - Permite que otros fragments/activities accedan a los datos
     * - Se mantiene mientras la app esté en memoria
     *
     * IMPORTANTE:
     * Al ser estática, los datos NO se pierden al rotar el dispositivo
     * o al navegar entre fragments.
     */
    private static List<Entrenamiento> entrenamientos;

    // ==================== CICLO DE VIDA DEL FRAGMENT ====================

    /**
     * Método onCreateView: Se llama cuando Android necesita crear la vista del fragment.
     *
     * FLUJO:
     * 1. Infla el layout XML (fragment_lista_entrenamientos.xml)
     * 2. Busca el ListView en el layout
     * 3. Inicializa la lista de entrenamientos (solo la primera vez)
     * 4. Crea y asigna el adapter al ListView
     * 5. Configura el listener para clicks en items
     * 6. Retorna la vista completa
     *
     * ¿CUÁNDO SE LLAMA?
     * - Primera vez que se muestra el fragment
     * - Después de una rotación de pantalla
     * - Al volver de otro fragment (si no está en back stack)
     *
     * @param inflater Objeto para convertir XML en Views
     * @param container ViewGroup padre donde se insertará el fragment
     * @param savedInstanceState Bundle con datos guardados (null si es primera vez)
     * @return View completa del fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // PASO 1: Inflar el layout XML
        // Convierte fragment_lista_entrenamientos.xml en objetos View
        View view = inflater.inflate(R.layout.fragment_lista_entrenamientos, container, false);

        // PASO 2: Buscar el ListView en el layout inflado
        listView = view.findViewById(R.id.lista_entrenamientos);

        // PASO 3: Inicializar lista de entrenamientos solo si está vacía
        // Al ser estática, se verifica si ya fue inicializada antes
        // Esto previene duplicar entrenamientos al rotar o recrear el fragment
        if (entrenamientos == null) {
            entrenamientos = new ArrayList<>();
            inicializarEntrenamientos();  // Carga los 4 entrenamientos iniciales
        }

        // PASO 4: Crear el adapter y conectarlo al ListView
        // El adapter será el "traductor" entre los datos y las vistas
        adapter = new EntrenamientoAdapter(getContext(), entrenamientos);
        listView.setAdapter(adapter);

        // PASO 5: Configurar listener para clicks en items de la lista
        // Lambda: (parámetros) -> { código a ejecutar }
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            // Obtener el entrenamiento clickeado según su posición
            Entrenamiento entrenamiento = entrenamientos.get(position);

            // Mostrar los detalles del entrenamiento
            mostrarDetalle(entrenamiento.getId());
        });

        // PASO 6: Retornar la vista completa
        return view;
    }

    // ==================== INICIALIZACIÓN DE DATOS ====================

    /**
     * Inicializa la lista con 4 entrenamientos predefinidos.
     *
     * ENTRENAMIENTOS INCLUIDOS:
     * 1. Cardio Intenso - Alta dificultad, 45 minutos
     * 2. Fuerza Total - Media dificultad, 60 minutos
     * 3. Yoga Relajante - Baja dificultad, 30 minutos
     * 4. HIIT Extremo - Alta dificultad, 25 minutos
     *
     * CUÁNDO SE LLAMA:
     * Solo la primera vez, cuando entrenamientos es null.
     * No se vuelve a llamar en rotaciones o navegación.
     *
     * IMPORTANTE:
     * Cada Entrenamiento tiene:
     * - ID único (1, 2, 3, 4)
     * - Nombre descriptivo
     * - Descripción detallada
     * - Duración en texto
     * - Nivel de dificultad
     * - Icono temático de Android (ic_menu_directions, ic_menu_manage, etc.)
     */
    private void inicializarEntrenamientos() {
        // Entrenamiento 1: CARDIO INTENSO
        // Icono: ic_menu_directions (flecha/dirección) para representar movimiento
        entrenamientos.add(new Entrenamiento(1, "Cardio Intenso",
            "Ejercicios cardiovasculares de alta intensidad para mejorar la resistencia",
            "45 minutos", "Alta", android.R.drawable.ic_menu_directions));

        // Entrenamiento 2: FUERZA TOTAL
        // Icono: ic_menu_manage (gestión/configuración) para representar estructura
        entrenamientos.add(new Entrenamiento(2, "Fuerza Total",
            "Entrenamiento de fuerza para todos los grupos musculares",
            "60 minutos", "Media", android.R.drawable.ic_menu_manage));

        // Entrenamiento 3: YOGA RELAJANTE
        // Icono: ic_menu_gallery (galería) para representar variedad/flexibilidad
        entrenamientos.add(new Entrenamiento(3, "Yoga Relajante",
            "Sesión de yoga para flexibilidad y relajación mental",
            "30 minutos", "Baja", android.R.drawable.ic_menu_gallery));

        // Entrenamiento 4: HIIT EXTREMO
        // Icono: ic_menu_rotate (rotación) para representar circuito/repetición
        entrenamientos.add(new Entrenamiento(4, "HIIT Extremo",
            "Entrenamiento de intervalos de alta intensidad para quemar calorías",
            "25 minutos", "Alta", android.R.drawable.ic_menu_rotate));
    }

    // ==================== NAVEGACIÓN Y GESTIÓN DE VISTAS ====================

    /**
     * Muestra los detalles de un entrenamiento.
     * El comportamiento cambia según la orientación del dispositivo.
     *
     * FLUJO:
     * 1. Crea un DetalleEntrenamientoFragment con el ID del entrenamiento
     * 2. Detecta si estamos en landscape (modo dual pane)
     * 3. Decide cómo mostrar el fragment:
     *    - Landscape: Actualiza el panel derecho (fragment_detalle)
     *    - Portrait: Reemplaza el fragment actual (fragment_container)
     *
     * PORTRAIT (Vertical):
     * ┌────────────────┐
     * │ Lista          │ ← Este fragment
     * └────────────────┘
     *        ↓ Click
     * ┌────────────────┐
     * │ Detalle        │ ← Se reemplaza con DetalleFragment
     * └────────────────┘
     *
     * LANDSCAPE (Horizontal):
     * ┌──────────┬─────────────┐
     * │ Lista    │ Detalle     │ ← Panel derecho se actualiza
     * │ (este)   │ (actualiza) │
     * └──────────┴─────────────┘
     *
     * @param idEntrenamiento ID único del entrenamiento a mostrar
     */
    private void mostrarDetalle(int idEntrenamiento) {
        // PASO 1: Crear el fragment de detalle usando el Factory Method
        // newInstance() crea el fragment y le pasa el ID mediante Bundle
        DetalleEntrenamientoFragment detalleFragment = DetalleEntrenamientoFragment.newInstance(idEntrenamiento);

        // PASO 2: Detectar si estamos en landscape (modo dual pane)
        // En landscape, existe el contenedor fragment_detalle
        // En portrait, solo existe fragment_container
        boolean isDualPane = getActivity().findViewById(R.id.fragment_detalle) != null;

        if (isDualPane) {
            // ======== MODO LANDSCAPE (Horizontal) ========
            // Dos paneles visibles simultáneamente
            // Solo se actualiza el panel derecho, el izquierdo (lista) permanece

            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_detalle, detalleFragment)  // Reemplaza el panel derecho
                .commit();  // No añade a back stack (no hay navegación atrás)

        } else {
            // ======== MODO PORTRAIT (Vertical) ========
            // Un solo panel visible
            // Se reemplaza completamente la vista (lista → detalle)

            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detalleFragment)  // Reemplaza el contenedor único
                .addToBackStack(null)  // Añade a back stack: botón atrás vuelve a la lista
                .commit();
        }
    }

    // ==================== MÉTODOS PÚBLICOS - GESTIÓN DE DATOS ====================

    /**
     * Añade un nuevo entrenamiento a la lista y actualiza la vista.

     * FLUJO:
     * 1. Añade el entrenamiento a la lista estática
     * 2. Notifica al adapter que los datos han cambiado
     * 3. El adapter redibuja el ListView automáticamente

     * USADO POR:
     * - NuevoEntrenamientoDialogFragment: Cuando el usuario crea un entrenamiento

     * ¿QUÉ ES notifyDataSetChanged()?
     * Le dice al adapter: "Los datos cambiaron, redibuja todas las vistas"
     * El adapter llama a getView() para cada item visible

     * IMPORTANTE:
     * Como la lista es estática, el nuevo entrenamiento se mantiene
     * incluso después de rotaciones o navegación.
     *
     * @param entrenamiento Objeto Entrenamiento a añadir a la lista
     */
    public void agregarEntrenamiento(Entrenamiento entrenamiento) {
        // Añadir a la lista
        entrenamientos.add(entrenamiento);

        // Notificar al adapter que debe actualizarse
        // Esto hace que el ListView muestre el nuevo item
        adapter.notifyDataSetChanged();
    }

    /**
     * Busca y retorna un entrenamiento por su ID.
     * Método ESTÁTICO para acceso desde cualquier parte de la app.

     * FLUJO:
     * 1. Verifica que la lista no sea null
     * 2. Itera por todos los entrenamientos
     * 3. Compara el ID de cada uno con el ID buscado
     * 4. Retorna el primero que coincida
     * 5. Si no encuentra ninguno, retorna null

     * USADO POR:
     * - DetalleEntrenamientoFragment: Para obtener los datos del entrenamiento a mostrar

     * ¿POR QUÉ ESTÁTICO?
     * - Puede llamarse sin instancia del fragment: ListaEntrenamientosFragment.getEntrenamientoPorId(1)
     * - Útil cuando el fragment no está activo pero necesitas los datos
     * - Acceso directo a la lista estática
     *
     * @param id ID único del entrenamiento a buscar
     * @return Objeto Entrenamiento si lo encuentra, null si no existe
     */
    public static Entrenamiento getEntrenamientoPorId(int id) {
        // Verificar que la lista esté inicializada
        if (entrenamientos != null) {
            // Iterar por todos los entrenamientos
            for (Entrenamiento e : entrenamientos) {
                // Comparar ID
                if (e.getId() == id) {
                    return e;  // Encontrado, retornar
                }
            }
        }

        // No encontrado o lista null
        return null;
    }

    /**
     * Genera un nuevo ID único para un entrenamiento.

     * ALGORITMO:
     * 1. Busca el ID más alto en la lista actual
     * 2. Le suma 1
     * 3. Retorna ese valor como nuevo ID único

     * EJEMPLO:
     * Lista actual: [ID:1, ID:2, ID:3, ID:4]
     * maxId = 4
     * nuevo ID = 5

     * USADO POR:
     * - NuevoEntrenamientoDialogFragment: Al crear un entrenamiento nuevo

     * ¿POR QUÉ ESTE MÉTODO?
     * - Garantiza que cada entrenamiento tenga un ID único
     * - Previene conflictos de IDs duplicados
     * - Simple de implementar y mantener

     * NOTA:
     * En una app real con base de datos, el ID lo genera la BD automáticamente.
     * Aquí lo hacemos manualmente porque usamos una lista en memoria.
     *
     * @return Nuevo ID único (máximo actual + 1)
     */
    public int generarNuevoId() {
        int maxId = 0;

        // Buscar el ID más alto
        for (Entrenamiento e : entrenamientos) {
            if (e.getId() > maxId) {
                maxId = e.getId();  // Actualizar el máximo
            }
        }

        // Retornar el siguiente ID disponible
        return maxId + 1;
    }
}

