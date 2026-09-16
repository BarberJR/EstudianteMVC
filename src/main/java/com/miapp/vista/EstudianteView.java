package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Vista: JFrame principal del módulo Estudiante.
 * Contiene un campo de búsqueda, un formulario de registro y una tabla
 * de resultados.
 *
 * IMPORTANTE (MVC): esta clase NO conoce ni importa el Modelo (Estudiante).
 * Solo trabaja con tipos genéricos (Object[], List<Object[]>) que el
 * Controlador le entrega ya preparados, y le envía al Controlador datos
 * simples (String, String, double). Así la Vista queda desacoplada del
 * Modelo y toda la comunicación pasa por el Controlador.
 */
public class EstudianteView extends JFrame {

    // ── Componentes UI ────────────────────────────────────────────────────────
    private JTextField        txtNombre;        // campo de búsqueda
    private JButton           btnBuscar;
    private JButton           btnMostrarTodos;

    private JTextField        txtNuevoNombre;   // formulario de registro
    private JTextField        txtNuevaCarrera;
    private JTextField        txtNuevoPromedio;
    private JButton           btnAgregar;

    private JTable            tblResultados;
    private DefaultTableModel modeloTabla;
    private JLabel            lblEstado;

    // ── Controlador ───────────────────────────────────────────────────────────
    private EstudianteController controlador;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ─────────────────────────────────────────

    private void initComponentes() {
        setTitle("Búsqueda de Estudiantes — MVC NetBeans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ── Panel superior — búsqueda + formulario de registro ────────────────
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

        // Barra de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar estudiante"));

        txtNombre = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(59, 139, 212));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);

        btnMostrarTodos = new JButton("Mostrar todos");
        btnMostrarTodos.setFocusPainted(false);

        panelBusqueda.add(new JLabel("Nombre:"));
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);

        // Formulario de registro
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar estudiante"));

        txtNuevoNombre   = new JTextField(14);
        txtNuevaCarrera  = new JTextField(14);
        txtNuevoPromedio = new JTextField(4);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(60, 150, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);

        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(txtNuevoNombre);
        panelAgregar.add(new JLabel("Carrera:"));
        panelAgregar.add(txtNuevaCarrera);
        panelAgregar.add(new JLabel("Promedio:"));
        panelAgregar.add(txtNuevoPromedio);
        panelAgregar.add(btnAgregar);

        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelAgregar);

        // ── Panel central — tabla de resultados ───────────────────────────────
        String[] columnas = {"ID", "Nombre", "Carrera", "Promedio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(24);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // ── Panel inferior — estado ───────────────────────────────────────────
        lblEstado = new JLabel("Ingrese un nombre y presione Buscar.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblEstado.setForeground(Color.GRAY);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll,        BorderLayout.CENTER);
        add(lblEstado,     BorderLayout.SOUTH);
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    private void initEventos() {
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombre.getText().trim());
            }
        });

        // También buscar al presionar Enter en el campo de texto
        txtNombre.addActionListener((ActionEvent e) -> btnBuscar.doClick());

        btnMostrarTodos.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.mostrarTodos();
            }
        });

        btnAgregar.addActionListener((ActionEvent e) -> enviarNuevoEstudiante());

        // Enter en cualquier campo del formulario también agrega
        txtNuevoNombre.addActionListener((ActionEvent e)   -> btnAgregar.doClick());
        txtNuevaCarrera.addActionListener((ActionEvent e)  -> btnAgregar.doClick());
        txtNuevoPromedio.addActionListener((ActionEvent e) -> btnAgregar.doClick());
    }

    /**
     * Captura los datos del formulario y se los envía al Controlador
     * como parámetros simples. La Vista no crea el objeto Estudiante:
     * solo convierte el texto del promedio a double para poder enviarlo.
     */
    private void enviarNuevoEstudiante() {
        if (controlador == null) {
            return;
        }

        String nombre        = txtNuevoNombre.getText().trim();
        String carrera       = txtNuevaCarrera.getText().trim();
        String textoPromedio = txtNuevoPromedio.getText().trim().replace(',', '.');

        double promedio;
        try {
            promedio = Double.parseDouble(textoPromedio);
        } catch (NumberFormatException ex) {
            // Error de formato: es un asunto de la entrada de texto, no una
            // regla de negocio. Las reglas (rango, nombre vacío) las valida
            // el Controlador.
            mostrarError("El promedio debe ser un número. Ejemplo: 4.2");
            return;
        }

        controlador.agregarEstudiante(nombre, carrera, promedio);
    }

    // ── Métodos públicos que llama el Controlador ─────────────────────────────
    // ninguno de estos métodos recibe un Estudiante: reciben
    // Object[] / List<Object[]> ya armados, que es lo único que la Vista
    // necesita saber para pintar la tabla.

    /**
     * Muestra una única fila en la tabla.
     * @param fila arreglo con {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        agregarFila(fila);
        setEstado("Se encontró 1 estudiante.");
    }

    /**
     * Muestra varias filas en la tabla.
     * @param filas lista de arreglos {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado("No se encontraron estudiantes con ese criterio.");
            return;
        }
        for (Object[] fila : filas) {
            agregarFila(fila);
        }
        setEstado("Se encontraron " + filas.size() + " estudiante(s).");
    }

    /**
     * Muestra un mensaje de confirmación tras registrar un estudiante
     * y deja el formulario listo para el siguiente registro.
     */
    public void mostrarConfirmacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
        setEstado(mensaje);
    }

    /**
     * Muestra un mensaje de error en la barra de estado.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }

    /**
     * Devuelve el texto ingresado en el campo de nombre.
     */
    public String getNombreBuscado() {
        return txtNombre.getText().trim();
    }

    // ── Setter del controlador ────────────────────────────────────────────────

    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    private void limpiarFormulario() {
        txtNuevoNombre.setText("");
        txtNuevaCarrera.setText("");
        txtNuevoPromedio.setText("");
        txtNuevoNombre.requestFocusInWindow();
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
