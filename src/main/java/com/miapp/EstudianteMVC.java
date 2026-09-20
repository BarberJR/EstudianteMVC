package com.miapp;

import com.miapp.controlador.EstudianteController;
import com.miapp.vista.EstudianteView;

public class EstudianteMVC {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            EstudianteView vista = new EstudianteView();

            EstudianteController controlador =
                    new EstudianteController(vista);

            vista.setControlador(controlador);

            vista.setVisible(true);

            controlador.mostrarTodos();
        });
    }
}