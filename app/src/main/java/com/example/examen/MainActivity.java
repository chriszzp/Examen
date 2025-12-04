/**
 * CLASE: MainActivity.java
 *
 * PROPÓSITO:
 * Actividad principal de la aplicación que gestiona los Fragments y la navegación.
 * Maneja dos modos de visualización: Portrait y Landscape (Master-Detail Pattern).
 *
 * FUNCIONALIDADES PRINCIPALES:
 * 1. Detecta la orientación del dispositivo (portrait/landscape)
 * 2. Carga los fragments apropiados según la orientación
 * 3. Maneja la rotación de pantalla correctamente
 * 4. Gestiona el menú de opciones (botón añadir entrenamiento)
 * 5. Muestra el diálogo para crear nuevos entrenamientos
 *
 * MODOS DE VISUALIZACIÓN:
 * - PORTRAIT (Vertical): Muestra solo la lista de entrenamientos
 * - LANDSCAPE (Horizontal): Muestra lista + detalles en dos paneles
 *
 * PATRÓN DE DISEÑO:
 * Master-Detail Pattern: Lista maestra y panel de detalles
 *
 * CICLO DE VIDA:
 * onCreate() → Detecta orientación → Carga fragments → Gestiona rotaciones
 */
package com.example.examen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * Actividad principal que gestiona la interfaz de usuario y los fragments.
 */
public class MainActivity extends AppCompatActivity {

    // ==================== CONSTANTES ====================

    /** Tag para logs de debugging - útil para filtrar en Logcat */
    private static final String TAG = "MainActivity";

    // ==================== CICLO DE VIDA ====================

    /**
     * Método onCreate: Punto de entrada de la Activity.
     * Se ejecuta cuando se crea la actividad por primera vez o después de una rotación.
     *
     * FLUJO:
     * 1. Establece el layout correspondiente a la orientación
     * 2. Detecta si estamos en portrait o landscape
     * 3. Carga los fragments necesarios
     * 4. Maneja la rotación si es necesario
     *
     * @param savedInstanceState Bundle con datos guardados (null en primera carga)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establece el layout principal
        // Android selecciona automáticamente entre layout/ y layout-land/
        setContentView(R.layout.activity_main);

        // Verificar orientación usando Configuration
        int orientation = getResources().getConfiguration().orientation;
        boolean isLandscape = (orientation == Configuration.ORIENTATION_LANDSCAPE);

        // Verificar si estamos en landscape buscando el contenedor de detalles
        // Si existe fragment_detalle, estamos en modo dual pane (landscape)
        boolean isDualPane = findViewById(R.id.fragment_detalle) != null;

        // Logs para debugging - Útiles para ver qué está pasando
        Log.d(TAG, "onCreate - Orientación: " + (isLandscape ? "LANDSCAPE" : "PORTRAIT"));
        Log.d(TAG, "onCreate - isDualPane: " + isDualPane);
        Log.d(TAG, "onCreate - savedInstanceState: " + (savedInstanceState != null ? "NOT NULL" : "NULL"));

        // Decidir qué hacer según si es primera carga o rotación
        if (savedInstanceState == null) {
            // PRIMERA CARGA: La app se está iniciando por primera vez
            Log.d(TAG, "Primera carga, cargando fragments...");
            cargarFragments(isDualPane);
        } else {
            // ROTACIÓN DETECTADA: La app se está recreando después de rotar
            if (isDualPane) {
                // Rotación a landscape: necesitamos dos fragments (lista + detalle)
                Log.d(TAG, "Rotación a landscape detectada");
                manejarLandscape();
            } else {
                // Rotación a portrait: necesitamos solo un fragment (lista)
                Log.d(TAG, "Rotación a portrait detectada");
                manejarPortrait();
            }
        }
    }

    // ==================== GESTIÓN DE ORIENTACIÓN ====================

    /**
     * Maneja el modo landscape (horizontal) con dos paneles.
     *
     * PROBLEMA QUE RESUELVE:
     * Al rotar de portrait a landscape, Android intenta restaurar el fragment
     * que estaba en fragment_container, pero ese ID no existe en landscape.
     *
     * SOLUCIÓN:
     * 1. Ejecuta transacciones pendientes
     * 2. Remueve TODOS los fragments existentes (limpieza)
     * 3. Carga nuevos fragments en los contenedores correctos:
     *    - fragment_lista: Lista de entrenamientos
     *    - fragment_detalle: Detalles del primer entrenamiento
     *
     * IMPORTANTE: Usa commitNow() en lugar de commit() para aplicar cambios
     * inmediatamente (síncrono) antes de continuar.
     */
    private void manejarLandscape() {
        Log.d(TAG, "Manejando landscape - preparando fragments");

        // Ejecutar transacciones pendientes del FragmentManager
        getSupportFragmentManager().executePendingTransactions();

        // PASO CRÍTICO: Remover todos los fragments existentes
        // Esto previene conflictos con fragments "huérfanos" de portrait
        for (androidx.fragment.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                Log.d(TAG, "Removiendo fragment existente: " + fragment.getClass().getSimpleName());
                getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow(); // Síncrono - espera a que termine
            }
        }

        // Cargar fragment de LISTA en el panel izquierdo
        Log.d(TAG, "Cargando fragment de lista en landscape");
        ListaEntrenamientosFragment listaFragment = new ListaEntrenamientosFragment();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_lista, listaFragment, "lista") // Tag "lista" para identificarlo
            .commitNow();

        // Cargar fragment de DETALLE en el panel derecho
        Log.d(TAG, "Cargando fragment de detalle en landscape");
        DetalleEntrenamientoFragment detalleFragment = DetalleEntrenamientoFragment.newInstance(1);
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_detalle, detalleFragment, "detalle") // Tag "detalle"
            .commitNow();

        Log.d(TAG, "Fragments en landscape cargados correctamente");
    }

    /**
     * Maneja el modo portrait (vertical) con un solo panel.
     *
     * FLUJO:
     * 1. Ejecuta transacciones pendientes
     * 2. Identifica qué tipo de fragment debería mostrarse
     * 3. Limpia todos los fragments
     * 4. Crea un nuevo fragment del tipo apropiado
     *
     * IMPORTANTE: No podemos "mover" fragments entre contenedores.
     * Debemos crear nuevos fragments con los datos apropiados.
     */
    private void manejarPortrait() {
        Log.d(TAG, "Manejando portrait - preparando fragments");

        // Ejecutar transacciones pendientes
        getSupportFragmentManager().executePendingTransactions();

        // PASO 1: Identificar qué había en landscape para decidir qué mostrar
        androidx.fragment.app.Fragment fragmentDetalle =
            getSupportFragmentManager().findFragmentById(R.id.fragment_detalle);
        androidx.fragment.app.Fragment fragmentContainer =
            getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        Log.d(TAG, "Fragments encontrados:");
        Log.d(TAG, "  - fragment_detalle: " + (fragmentDetalle != null ? fragmentDetalle.getClass().getSimpleName() : "null"));
        Log.d(TAG, "  - fragment_container: " + (fragmentContainer != null ? fragmentContainer.getClass().getSimpleName() : "null"));

        // PASO 2: Determinar qué fragment crear
        androidx.fragment.app.Fragment nuevoFragment = null;

        // Si había un detalle en landscape, obtener su ID para recrearlo
        if (fragmentDetalle instanceof DetalleEntrenamientoFragment) {
            Log.d(TAG, "Había DetalleFragment en landscape, recreándolo para portrait");
            // Obtener el ID del entrenamiento del fragment de detalle
            Bundle args = fragmentDetalle.getArguments();
            if (args != null) {
                int idEntrenamiento = args.getInt("id_entrenamiento", 1);
                nuevoFragment = DetalleEntrenamientoFragment.newInstance(idEntrenamiento);
            }
        }
        // Si ya había algo en container y NO viene de landscape, mantenerlo
        else if (fragmentContainer != null) {
            Log.d(TAG, "Ya hay fragment en container, lo mantenemos");
            // En este caso no hacemos nada, el fragment ya está donde debe
            return;
        }

        // PASO 3: Limpiar TODOS los fragments
        Log.d(TAG, "Limpiando todos los fragments...");
        for (androidx.fragment.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                Log.d(TAG, "Removiendo fragment: " + fragment.getClass().getSimpleName());
                getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow();
            }
        }

        // PASO 4: Cargar el fragment apropiado en fragment_container
        if (nuevoFragment != null) {
            Log.d(TAG, "Cargando " + nuevoFragment.getClass().getSimpleName() + " en portrait");
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, nuevoFragment)
                .commitNow();
        } else {
            // Por defecto, cargar la lista
            Log.d(TAG, "Cargando lista por defecto en portrait");
            ListaEntrenamientosFragment listaFragment = new ListaEntrenamientosFragment();
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, listaFragment)
                .commitNow();
        }

        Log.d(TAG, "manejarPortrait completado");
    }

    /**
     * Carga los fragments apropiados en la primera ejecución.
     *
     * @param isDualPane true si estamos en landscape (dos paneles), false si portrait
     */
    private void cargarFragments(boolean isDualPane) {
        ListaEntrenamientosFragment listaFragment = new ListaEntrenamientosFragment();

        if (isDualPane) {
            // LANDSCAPE: Cargar dos fragments lado a lado

            // Fragment de lista en panel izquierdo
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_lista, listaFragment)
                .commit(); // Asíncrono está bien aquí (primera carga)

            // Fragment de detalle en panel derecho (muestra el primer entrenamiento)
            DetalleEntrenamientoFragment detalleFragment = DetalleEntrenamientoFragment.newInstance(1);
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_detalle, detalleFragment)
                .commit();
        } else {
            // PORTRAIT: Cargar solo la lista
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, listaFragment)
                .commit();
        }
    }

    // ==================== MENÚ DE OPCIONES ====================

    /**
     * Infla el menú de opciones en la ActionBar.
     * Este menú contiene el botón [+] para añadir entrenamientos.
     *
     * @param menu El menú donde se inflarán las opciones
     * @return true para mostrar el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú desde res/menu/main_menu.xml
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Maneja los clicks en los items del menú.
     *
     * @param item El item del menú que fue seleccionado
     * @return true si el evento fue manejado, false si no
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verificar qué item fue clickeado
        if (item.getItemId() == R.id.menu_anadir) {
            // El botón [+] fue presionado
            mostrarDialogoNuevoEntrenamiento();
            return true; // Evento manejado
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Muestra el diálogo para crear un nuevo entrenamiento.
     * Este diálogo permite ingresar:
     * - Nombre del entrenamiento
     * - Descripción
     * - Duración
     * - Dificultad (mediante Spinner)
     * - Icono (mediante selector visual)
     */
    private void mostrarDialogoNuevoEntrenamiento() {
        // Crear instancia del DialogFragment
        NuevoEntrenamientoDialogFragment dialog = new NuevoEntrenamientoDialogFragment();

        // Mostrar el diálogo
        // Parámetros:
        // - getSupportFragmentManager(): Gestor de fragments
        // - "NuevoEntrenamientoDialog": Tag para identificar el diálogo
        dialog.show(getSupportFragmentManager(), "NuevoEntrenamientoDialog");
    }
}