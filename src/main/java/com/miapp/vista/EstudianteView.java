package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstudianteView extends JFrame {

    private JTextField txtNombreBuscar;

    private JTextField txtNombre;
    private JTextField txtCarrera;
    private JTextField txtPromedio;

    private JButton btnBuscar;
    private JButton btnMostrarTodos;
    private JButton btnAgregar;

    private JComboBox<String> comboCriterio;
    private JButton btnOrdenar;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JLabel lblEstado;

    private EstudianteController controlador;

    public EstudianteView() {

        iniciarComponentes();

        iniciarEventos();
    }

    private void iniciarComponentes() {

        setTitle("Búsqueda de Estudiantes - MVC NetBeans");

        setSize(850, 600);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        // ==========================================
        // PANEL SUPERIOR
        // ==========================================

        JPanel panelSuperior = new JPanel();

        panelSuperior.setLayout(
                new BoxLayout(
                        panelSuperior,
                        BoxLayout.Y_AXIS
                )
        );

        // ==========================================
        // BUSCAR
        // ==========================================

        JPanel panelBuscar = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        10
                )
        );

        panelBuscar.setBorder(
                BorderFactory.createTitledBorder(
                        "Buscar estudiante"
                )
        );

        panelBuscar.add(
                new JLabel("Nombre:")
        );

        txtNombreBuscar =
                new JTextField(18);

        panelBuscar.add(
                txtNombreBuscar
        );

        btnBuscar =
                new JButton("Buscar");

        // Botón azul
        btnBuscar.setBackground(
                new Color(70, 130, 180)
        );

        btnBuscar.setForeground(
                Color.WHITE
        );

        btnBuscar.setFocusPainted(false);

        panelBuscar.add(
                btnBuscar
        );

        btnMostrarTodos =
                new JButton("Mostrar todos");

        // Botón gris
        btnMostrarTodos.setBackground(
                new Color(220, 220, 220)
        );

        btnMostrarTodos.setFocusPainted(false);

        panelBuscar.add(
                btnMostrarTodos
        );

        panelSuperior.add(
                panelBuscar
        );

        // ==========================================
        // AGREGAR ESTUDIANTE
        // ==========================================

        JPanel panelAgregar = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        10
                )
        );

        panelAgregar.setBorder(
                BorderFactory.createTitledBorder(
                        "Agregar estudiante"
                )
        );

        panelAgregar.add(
                new JLabel("Nombre:")
        );

        txtNombre =
                new JTextField(10);

        panelAgregar.add(
                txtNombre
        );

        panelAgregar.add(
                new JLabel("Carrera:")
        );

        txtCarrera =
                new JTextField(12);

        panelAgregar.add(
                txtCarrera
        );

        panelAgregar.add(
                new JLabel("Promedio:")
        );

        txtPromedio =
                new JTextField(5);

        panelAgregar.add(
                txtPromedio
        );

        btnAgregar =
                new JButton("Agregar");

        // Botón verde
        btnAgregar.setBackground(
                new Color(70, 170, 80)
        );

        btnAgregar.setForeground(
                Color.WHITE
        );

        btnAgregar.setFocusPainted(false);

        panelAgregar.add(
                btnAgregar
        );

        panelSuperior.add(
                panelAgregar
        );

        // ==========================================
        // ORDENAR
        // ==========================================

        JPanel panelOrdenar = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        10
                )
        );

        panelOrdenar.setBorder(
                BorderFactory.createTitledBorder(
                        "Ordenar resultados"
                )
        );

        panelOrdenar.add(
                new JLabel("Criterio:")
        );

        comboCriterio =
                new JComboBox<>(
                        new String[]{
                            "Nombre",
                            "Promedio"
                        }
                );

        panelOrdenar.add(
                comboCriterio
        );

        btnOrdenar =
                new JButton("Ordenar");

        // Botón naranja sencillo
        btnOrdenar.setBackground(
                new Color(230, 170, 70)
        );

        btnOrdenar.setFocusPainted(false);

        panelOrdenar.add(
                btnOrdenar
        );

        panelSuperior.add(
                panelOrdenar
        );

        add(
                panelSuperior,
                BorderLayout.NORTH
        );

        // ==========================================
        // TABLA
        // ==========================================

        String[] columnas = {
            "ID",
            "Nombre",
            "Carrera",
            "Promedio"
        };

        modeloTabla =
                new DefaultTableModel(
                        columnas,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna) {

                        return false;
                    }
                };

        tabla =
                new JTable(modeloTabla);

        tabla.setRowHeight(25);

        JScrollPane scroll =
                new JScrollPane(tabla);

        scroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Resultados"
                )
        );

        add(
                scroll,
                BorderLayout.CENTER
        );

        // ==========================================
        // ESTADO
        // ==========================================

        lblEstado =
                new JLabel(
                        "Listo."
                );

        lblEstado.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        add(
                lblEstado,
                BorderLayout.SOUTH
        );
    }

    private void iniciarEventos() {

        // ==========================================
        // BUSCAR
        // ==========================================

        btnBuscar.addActionListener(e -> {

            controlador.buscarEstudiante(
                    txtNombreBuscar.getText()
            );
        });

        // ==========================================
        // MOSTRAR TODOS
        // ==========================================

        btnMostrarTodos.addActionListener(e -> {

            controlador.mostrarTodos();
        });

        // ==========================================
        // AGREGAR
        // ==========================================

        btnAgregar.addActionListener(e -> {

            agregarEstudiante();
        });

        // ==========================================
        // ORDENAR
        // ==========================================

        btnOrdenar.addActionListener(e -> {

            String criterio =
                    (String) comboCriterio.getSelectedItem();

            controlador.ordenarPor(
                    criterio
            );
        });
    }

    // ==========================================
    // AGREGAR DESDE FORMULARIO
    // ==========================================

    private void agregarEstudiante() {

        String nombre =
                txtNombre.getText();

        String carrera =
                txtCarrera.getText();

        String textoPromedio =
                txtPromedio
                        .getText()
                        .replace(
                                ",",
                                "."
                        );

        double promedio;

        try {

            promedio =
                    Double.parseDouble(
                            textoPromedio
                    );

        } catch (NumberFormatException e) {

            mostrarError(
                    "El promedio debe ser un número."
            );

            return;
        }

        // La Vista solo manda los datos
        controlador.agregarEstudiante(
                nombre,
                carrera,
                promedio
        );
    }

    // ==========================================
    // MOSTRAR ESTUDIANTES
    // ==========================================

    public void mostrarEstudiantes(
            List<Object[]> filas) {

        modeloTabla.setRowCount(0);

        for (Object[] fila : filas) {

            modeloTabla.addRow(fila);
        }

        mostrarEstado(
                "Se encontraron "
                + filas.size()
                + " estudiante(s)."
        );
    }

    // ==========================================
    // CONFIRMACIÓN
    // ==========================================

    public void mostrarConfirmacion(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
        );

        txtNombre.setText("");
        txtCarrera.setText("");
        txtPromedio.setText("");

        txtNombre.requestFocus();
    }

    // ==========================================
    // ERROR
    // ==========================================

    public void mostrarError(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        mostrarEstado(
                "Error: " + mensaje
        );
    }

    // ==========================================
    // MENSAJE DE ESTADO
    // ==========================================

    public void mostrarEstado(
            String mensaje) {

        lblEstado.setText(
                mensaje
        );
    }

    // ==========================================
    // CONECTAR CONTROLADOR
    // ==========================================

    public void setControlador(
            EstudianteController controlador) {

        this.controlador =
                controlador;
    }
}