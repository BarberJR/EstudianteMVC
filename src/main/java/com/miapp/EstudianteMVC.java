/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.miapp;

import com.miapp.vista.EstudianteView;
import com.miapp.controlador.EstudianteController;

public class EstudianteMVC {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            EstudianteView vista = new EstudianteView();

            new EstudianteController(vista);

            vista.setVisible(true);
        });
    }
}