package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;

public class EstudianteController {

    private EstudianteView vista;
    private List<Estudiante> estudiantes;

    private int siguienteId = 1;

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        this.vista.setControlador(this);
        cargarDatos();
    }

    private void cargarDatos() {

        estudiantes = new ArrayList<>();

        estudiantes.add(new Estudiante(siguienteId++, "Ana García", "Ingeniería de Sistemas", 4.5));
        estudiantes.add(new Estudiante(siguienteId++, "Carlos López", "Ingeniería Civil", 3.8));
        estudiantes.add(new Estudiante(siguienteId++, "María Rodríguez", "Medicina", 4.9));
        estudiantes.add(new Estudiante(siguienteId++, "José Martínez", "Derecho", 3.5));
        estudiantes.add(new Estudiante(siguienteId++, "Laura Sánchez", "Administración", 4.1));
        estudiantes.add(new Estudiante(siguienteId++, "Andrés Torres", "Ingeniería de Sistemas", 3.9));
        estudiantes.add(new Estudiante(siguienteId++, "Valentina Gómez", "Psicología", 4.3));
        estudiantes.add(new Estudiante(siguienteId++, "Luis Herrera", "Economía", 3.7));
        estudiantes.add(new Estudiante(siguienteId++, "Sofía Díaz", "Ingeniería Civil", 4.6));
        estudiantes.add(new Estudiante(siguienteId++, "Juliana Morales", "Medicina", 4.8));
        estudiantes.add(new Estudiante(siguienteId++, "Ana Milena Ruiz", "Derecho", 4.0));
        estudiantes.add(new Estudiante(siguienteId++, "Carlos Andrés Paz", "Administración", 3.6));
    }

    public void agregarEstudiante(String nombre, String carrera, double promedio) {

        if (nombre == null || nombre.trim().isEmpty()) {
            vista.mostrarError("El nombre no puede estar vacío.");
            return;
        }

        if (carrera == null || carrera.trim().isEmpty()) {
            vista.mostrarError("La carrera no puede estar vacía.");
            return;
        }

        if (promedio < 0.0 || promedio > 5.0) {
            vista.mostrarError("El promedio debe estar entre 0.0 y 5.0.");
            return;
        }

        Estudiante nuevo = new Estudiante(
                siguienteId++,
                nombre.trim(),
                carrera.trim(),
                promedio
        );

        estudiantes.add(nuevo);

        vista.mostrarConfirmacion(
                "Estudiante " + nuevo.getNombre() + " agregado correctamente."
        );

        vista.mostrarEstudiantes(convertirAFilas(estudiantes));
    }

    public void mostrarTodos() {
        vista.mostrarEstudiantes(convertirAFilas(estudiantes));
    }

    public void buscarEstudiante(String criterio) {

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

            vista.mostrarEstudiantes(new ArrayList<>());

        } else if (resultados.size() == 1) {

            vista.mostrarEstudiante(
                    convertirAFila(resultados.get(0))
            );

        } else {

            vista.mostrarEstudiantes(
                    convertirAFilas(resultados)
            );
        }
    }

    private Object[] convertirAFila(Estudiante e) {

        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getCarrera(),
            String.format("%.2f", e.getPromedio())
        };
    }

    private List<Object[]> convertirAFilas(List<Estudiante> lista) {

        List<Object[]> filas = new ArrayList<>();

        for (Estudiante e : lista) {
            filas.add(convertirAFila(e));
        }

        return filas;
    }
}