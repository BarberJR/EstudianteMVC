package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador: gestiona la lógica entre la Vista y el Modelo.
 * Contiene la colección de estudiantes y responde a las búsquedas y a las altas.
 *
 * IMPORTANTE (MVC): el Controlador es el ÚNICO que conoce tanto la Vista
 * como el Modelo. Es el responsable de traducir objetos Estudiante
 * (Modelo) a Object[] / List<Object[]> (datos "neutros") antes de
 * entregárselos a la Vista. La Vista nunca recibe ni conoce la clase
 * Estudiante directamente, y tampoco la construye: eso se hace aquí.
 */
public class EstudianteController {

    // ── Vista ─────────────────────────────────────────────────────────────────
    private EstudianteView vista;

    // ── Colección de estudiantes (fuente de datos) ────────────────────────────
    // Se usa ArrayList y no un arreglo fijo, porque debe poder crecer
    // cuando el usuario registra estudiantes en tiempo de ejecución.
    private List<Estudiante> estudiantes;

    // Contador para asignar el ID del próximo estudiante registrado.
    private int siguienteId = 1;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        this.vista.setControlador(this);
        cargarDatos();
    }

    // ── Carga de datos iniciales ──────────────────────────────────────────────

    /**
     * Inicializa la lista de estudiantes con datos de ejemplo.
     * En un proyecto real estos datos vendrían de una base de datos o servicio.
     */
    private void cargarDatos() {
        estudiantes = new ArrayList<>();

        estudiantes.add(new Estudiante(siguienteId++, "Ana García",        "Ingeniería de Sistemas", 4.5));
        estudiantes.add(new Estudiante(siguienteId++, "Carlos López",      "Ingeniería Civil",       3.8));
        estudiantes.add(new Estudiante(siguienteId++, "María Rodríguez",   "Medicina",               4.9));
        estudiantes.add(new Estudiante(siguienteId++, "José Martínez",     "Derecho",                3.5));
        estudiantes.add(new Estudiante(siguienteId++, "Laura Sánchez",     "Administración",         4.1));
        estudiantes.add(new Estudiante(siguienteId++, "Andrés Torres",     "Ingeniería de Sistemas", 3.9));
        estudiantes.add(new Estudiante(siguienteId++, "Valentina Gómez",   "Psicología",             4.3));
        estudiantes.add(new Estudiante(siguienteId++, "Luis Herrera",      "Economía",               3.7));
        estudiantes.add(new Estudiante(siguienteId++, "Sofía Díaz",        "Ingeniería Civil",       4.6));
        estudiantes.add(new Estudiante(siguienteId++, "Juliana Morales",   "Medicina",               4.8));
        estudiantes.add(new Estudiante(siguienteId++, "Ana Milena Ruiz",   "Derecho",                4.0));
        estudiantes.add(new Estudiante(siguienteId++, "Carlos Andrés Paz", "Administración",         3.6));
    }

    // ── Alta de un nuevo estudiante ───────────────────────────────────────────

    /**
     * Registra un nuevo estudiante a partir de datos simples enviados por la Vista.
     * La Vista NO construye el Estudiante: solo envía String, String y double.
     *
     * @param nombre   nombre capturado en el formulario
     * @param carrera  carrera capturada en el formulario
     * @param promedio promedio capturado en el formulario (entre 0.0 y 5.0)
     */
    public void agregarEstudiante(String nombre, String carrera, double promedio) {

        // Validación: el nombre no puede estar vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            vista.mostrarError("El nombre no puede estar vacío.");
            return;
        }

        // Validación: la carrera no puede estar vacía
        if (carrera == null || carrera.trim().isEmpty()) {
            vista.mostrarError("La carrera no puede estar vacía.");
            return;
        }

        // Validación: rango del promedio
        if (promedio < 0.0 || promedio > 5.0) {
            vista.mostrarError("El promedio debe estar entre 0.0 y 5.0.");
            return;
        }

        // El Controlador es quien construye el objeto del Modelo
        Estudiante nuevo = new Estudiante(siguienteId++, nombre.trim(), carrera.trim(), promedio);
        estudiantes.add(nuevo);

        vista.mostrarConfirmacion("Estudiante \"" + nuevo.getNombre()
                + "\" registrado con el ID " + nuevo.getId() + ".");

        // Refresca la tabla con todos los estudiantes, incluido el nuevo
        vista.mostrarEstudiantes(convertirAFilas(estudiantes));
    }

    // ── Listado completo ──────────────────────────────────────────────────────

    /**
     * Muestra en la Vista todos los estudiantes registrados.
     */
    public void mostrarTodos() {
        vista.mostrarEstudiantes(convertirAFilas(estudiantes));
    }

    // ── Lógica de búsqueda ────────────────────────────────────────────────────

    /**
     * Busca estudiantes cuyo nombre contenga el criterio (sin distinción de mayúsculas).
     * Luego llama a vista.mostrarEstudiante(fila) para una coincidencia,
     * o a vista.mostrarEstudiantes(filas) cuando hay varias.
     *
     * @param criterio texto ingresado por el usuario en la Vista
     */
    public void buscarEstudiante(String criterio) {

        // Validación básica
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError("Por favor ingrese un nombre para buscar.");
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            if (e.getNombre().toLowerCase().contains(criterioBajo)) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>()); // mostrará mensaje vacío
        } else if (resultados.size() == 1) {
            // Un solo resultado: se convierte a fila y se usa vista.mostrarEstudiante(fila)
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            // Varios resultados: se convierte toda la lista antes de enviarla a la Vista
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

    // ── Traducción Modelo → datos para la Vista ───────────────────────────────
    // Estos métodos son el "puente" que evita que la Vista dependa de Estudiante.

    /**
     * Convierte un Estudiante (Modelo) en un arreglo genérico que la Vista
     * puede pintar sin conocer la clase Estudiante.
     */
    private Object[] convertirAFila(Estudiante e) {
        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getCarrera(),
            String.format("%.2f", e.getPromedio())
        };
    }

    /**
     * Convierte una lista de Estudiante en una lista de filas genéricas.
     */
    private List<Object[]> convertirAFilas(List<Estudiante> lista) {
        List<Object[]> filas = new ArrayList<>();
        for (Estudiante e : lista) {
            filas.add(convertirAFila(e));
        }
        return filas;
    }
}
