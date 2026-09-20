package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EstudianteView extends JFrame {

    private JTextField txtNombre;
    private JButton btnBuscar;
    private JButton btnMostrarTodos;

    private JTextField txtNuevoNombre;
    private JTextField txtNuevaCarrera;
    private JTextField txtNuevoPromedio;
    private JButton btnAgregar;

    private JTable tblResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado;

    private EstudianteController controlador;

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    private void initComponentes() {

        setTitle("Búsqueda de Estudiantes - MVC NetBeans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(800, 500);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel();

        panelSuperior.setLayout(
                new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)
        );

        // BUSCAR
        JPanel panelBusqueda = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 10)
        );

        panelBusqueda.setBorder(
                BorderFactory.createTitledBorder("Buscar estudiante")
        );

        txtNombre = new JTextField(20);

        btnBuscar = new JButton("Buscar");

        // Personalización sencilla
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));

        btnMostrarTodos = new JButton("Mostrar todos");

        btnMostrarTodos.setBackground(new Color(220, 220, 220));
        btnMostrarTodos.setFocusPainted(false);
        btnMostrarTodos.setFont(new Font("Arial", Font.BOLD, 12));

        panelBusqueda.add(new JLabel("Nombre:"));
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);

        // AGREGAR ESTUDIANTE
        JPanel panelAgregar = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 10)
        );

        panelAgregar.setBorder(
                BorderFactory.createTitledBorder("Agregar estudiante")
        );

        txtNuevoNombre = new JTextField(12);
        txtNuevaCarrera = new JTextField(12);
        txtNuevoPromedio = new JTextField(5);

        btnAgregar = new JButton("Agregar");

        // Personalización sencilla
        btnAgregar.setBackground(new Color(60, 160, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));

        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(txtNuevoNombre);

        panelAgregar.add(new JLabel("Carrera:"));
        panelAgregar.add(txtNuevaCarrera);

        panelAgregar.add(new JLabel("Promedio:"));
        panelAgregar.add(txtNuevoPromedio);

        panelAgregar.add(btnAgregar);

        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelAgregar);

        // TABLA
        String[] columnas = {
            "ID",
            "Nombre",
            "Carrera",
            "Promedio"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tblResultados = new JTable(modeloTabla);

        tblResultados.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(tblResultados);

        scroll.setBorder(
                BorderFactory.createTitledBorder("Resultados")
        );

        // MENSAJE INFERIOR
        lblEstado = new JLabel(
                "Ingrese un nombre y presione Buscar."
        );

        lblEstado.setBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(lblEstado, BorderLayout.SOUTH);
    }

    private void initEventos() {

        // BOTÓN BUSCAR
        btnBuscar.addActionListener((ActionEvent e) -> {

            if (controlador != null) {

                controlador.buscarEstudiante(
                        txtNombre.getText().trim()
                );
            }
        });

        // ENTER EN BUSCAR
        txtNombre.addActionListener(
                (ActionEvent e) -> btnBuscar.doClick()
        );

        // BOTÓN MOSTRAR TODOS
        btnMostrarTodos.addActionListener((ActionEvent e) -> {

            if (controlador != null) {
                controlador.mostrarTodos();
            }
        });

        // BOTÓN AGREGAR
        btnAgregar.addActionListener(
                (ActionEvent e) -> enviarNuevoEstudiante()
        );

        // ENTER PARA AGREGAR
        txtNuevoNombre.addActionListener(
                (ActionEvent e) -> btnAgregar.doClick()
        );

        txtNuevaCarrera.addActionListener(
                (ActionEvent e) -> btnAgregar.doClick()
        );

        txtNuevoPromedio.addActionListener(
                (ActionEvent e) -> btnAgregar.doClick()
        );
    }

    private void enviarNuevoEstudiante() {

        if (controlador == null) {
            return;
        }

        String nombre = txtNuevoNombre.getText().trim();

        String carrera = txtNuevaCarrera.getText().trim();

        String textoPromedio =
                txtNuevoPromedio.getText().trim().replace(',', '.');

        double promedio;

        try {

            promedio = Double.parseDouble(textoPromedio);

        } catch (NumberFormatException ex) {

            mostrarError(
                    "El promedio debe ser un número. Ejemplo: 4.2"
            );

            return;
        }

        // La Vista manda los datos al Controlador
        controlador.agregarEstudiante(
                nombre,
                carrera,
                promedio
        );
    }

    public void mostrarEstudiante(Object[] fila) {

        limpiarTabla();

        agregarFila(fila);

        setEstado("Se encontró 1 estudiante.");
    }

    public void mostrarEstudiantes(List<Object[]> filas) {

        limpiarTabla();

        if (filas == null || filas.isEmpty()) {

            setEstado(
                    "No se encontraron estudiantes."
            );

            return;
        }

        for (Object[] fila : filas) {

            agregarFila(fila);
        }

        setEstado(
                "Se encontraron "
                + filas.size()
                + " estudiante(s)."
        );
    }

    public void mostrarConfirmacion(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE
        );

        limpiarFormulario();

        setEstado(mensaje);
    }

    public void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        setEstado("Error: " + mensaje);
    }

    public void setControlador(
            EstudianteController controlador
    ) {

        this.controlador = controlador;
    }

    private void limpiarFormulario() {

        txtNuevoNombre.setText("");
        txtNuevaCarrera.setText("");
        txtNuevoPromedio.setText("");

        txtNuevoNombre.requestFocus();
    }

    private void agregarFila(Object[] fila) {

        modeloTabla.addRow(fila);
    }

    private void limpiarTabla() {

        modeloTabla.setRowCount(0);
    }

    private void setEstado(String texto) {

        lblEstado.setText(texto);
    }
}