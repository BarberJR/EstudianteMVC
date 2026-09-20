package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EstudianteController {

    private EstudianteView vista;

    private ArrayList<Estudiante> estudiantes;

    // Guarda los últimos resultados mostrados
    private ArrayList<Estudiante> ultimosResultados;

    private int siguienteId = 1;

    // Sirve para cambiar entre ascendente y descendente
    private boolean ordenAscendente = true;

    public EstudianteController(EstudianteView vista) {

        this.vista = vista;

        estudiantes = new ArrayList<>();
        ultimosResultados = new ArrayList<>();

        cargarEstudiantes();
    }

    private void cargarEstudiantes() {

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Ana García",
                "Ingeniería de Sistemas",
                4.5
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Carlos López",
                "Ingeniería Civil",
                3.8
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "María Rodríguez",
                "Medicina",
                4.9
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "José Martínez",
                "Derecho",
                3.5
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Laura Sánchez",
                "Administración",
                4.1
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Andrés Torres",
                "Ingeniería de Sistemas",
                3.9
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Valentina Gómez",
                "Psicología",
                4.3
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Luis Herrera",
                "Economía",
                3.7
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Sofía Díaz",
                "Ingeniería Civil",
                4.6
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Juliana Morales",
                "Medicina",
                4.8
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Ana Milena Ruiz",
                "Derecho",
                4.0
        ));

        estudiantes.add(new Estudiante(
                siguienteId++,
                "Carlos Andrés Paz",
                "Administración",
                3.6
        ));
    }

    // =====================================================
    // ENUNCIADO 1 - AGREGAR ESTUDIANTE
    // =====================================================

    public void agregarEstudiante(
            String nombre,
            String carrera,
            double promedio) {

        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {

            vista.mostrarError(
                    "El nombre no puede estar vacío."
            );

            return;
        }

        // Validar promedio
        if (promedio < 0.0 || promedio > 5.0) {

            vista.mostrarError(
                    "El promedio debe estar entre 0.0 y 5.0."
            );

            return;
        }

        // Validar carrera
        if (carrera == null || carrera.trim().isEmpty()) {

            vista.mostrarError(
                    "La carrera no puede estar vacía."
            );

            return;
        }

        // El Controlador crea el objeto
        Estudiante nuevoEstudiante = new Estudiante(
                siguienteId++,
                nombre.trim(),
                carrera.trim(),
                promedio
        );

        // Agregar a la lista
        estudiantes.add(nuevoEstudiante);

        // Mostrar confirmación
        vista.mostrarConfirmacion(
                "Estudiante agregado correctamente."
        );

        // Después de agregar mostramos todos
        mostrarTodos();
    }

    // =====================================================
    // ENUNCIADO 2 - ORDENAR
    // =====================================================

    public void ordenarPor(String criterio) {

        // Verificar si hay resultados para ordenar
        if (ultimosResultados == null
                || ultimosResultados.isEmpty()) {

            vista.mostrarError(
                    "No hay resultados para ordenar."
            );

            return;
        }

        // Ordenar por nombre
        if (criterio.equals("Nombre")) {

            ultimosResultados.sort(
                    Comparator.comparing(
                            Estudiante::getNombre,
                            String.CASE_INSENSITIVE_ORDER
                    )
            );

        }

        // Ordenar por promedio
        else if (criterio.equals("Promedio")) {

            ultimosResultados.sort(
                    Comparator.comparingDouble(
                            Estudiante::getPromedio
                    )
            );

        }

        // Si se pidió descendente
        if (!ordenAscendente) {

            java.util.Collections.reverse(
                    ultimosResultados
            );
        }

        // Mostrar los resultados ordenados
        vista.mostrarEstudiantes(
                convertirAFilas(ultimosResultados)
        );

        // Cambiar el orden para el siguiente clic
        ordenAscendente = !ordenAscendente;
    }

    // =====================================================
    // ENUNCIADO 3 - MOSTRAR TODOS
    // =====================================================

    public void mostrarTodos() {

        // Guardamos los resultados actuales
        ultimosResultados = new ArrayList<>(
                estudiantes
        );

        // Mostrar todos en la Vista
        vista.mostrarEstudiantes(
                convertirAFilas(ultimosResultados)
        );
    }

    // =====================================================
    // BUSCAR ESTUDIANTE
    // =====================================================

    public void buscarEstudiante(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {

            vista.mostrarError(
                    "Escriba un nombre para buscar."
            );

            return;
        }

        ArrayList<Estudiante> resultados =
                new ArrayList<>();

        String texto = nombre.trim().toLowerCase();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante.getNombre()
                    .toLowerCase()
                    .contains(texto)) {

                resultados.add(estudiante);
            }
        }

        // Guardar últimos resultados
        ultimosResultados = new ArrayList<>(
                resultados
        );

        if (resultados.isEmpty()) {

            vista.mostrarEstudiantes(
                    new ArrayList<>()
            );

            vista.mostrarEstado(
                    "No se encontró ningún estudiante."
            );

        } else {

            vista.mostrarEstudiantes(
                    convertirAFilas(resultados)
            );

            vista.mostrarEstado(
                    "Se encontraron "
                    + resultados.size()
                    + " estudiante(s)."
            );
        }
    }

    // =====================================================
    // CONVERTIR ESTUDIANTES A FILAS
    // =====================================================

    private List<Object[]> convertirAFilas(
            List<Estudiante> lista) {

        List<Object[]> filas = new ArrayList<>();

        for (Estudiante estudiante : lista) {

            Object[] fila = {

                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getCarrera(),
                String.format(
                        "%.2f",
                        estudiante.getPromedio()
                )
            };

            filas.add(fila);
        }

        return filas;
    }

    public ArrayList<Estudiante> getEstudiantes() {

        return estudiantes;
    }
}