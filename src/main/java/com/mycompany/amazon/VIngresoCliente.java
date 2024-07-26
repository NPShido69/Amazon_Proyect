package com.mycompany.amazon;

import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VIngresoCliente extends JFrame implements ActionListener, FocusListener {
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtMail;
    private JTextField txtTelefono;
    private JRadioButton rbMasculino;
    private JRadioButton rbFemenino;
    private JCheckBox cbPromociones;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private CCliente controlador;

    /**
     * Constructor de la clase VIngresoCliente.
     * @param controlador El controlador que manejará la lógica de negocio.
     */
    public VIngresoCliente(CCliente controlador) {
        this.controlador = controlador;
        initComponents(); // Inicializar los componentes de la interfaz
    }

    /**
     * Método para inicializar los componentes de la interfaz.
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra la ventana al presionar el botón de cerrar
        setSize(400, 400); // Establece el tamaño de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setAlwaysOnTop(true); // Mantener la ventana siempre al frente

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Configurar el layout del panel

        // Inicializar los campos de texto
        txtCedula = new JTextField(10); // Campo para ingresar la cédula
        txtCedula.addFocusListener(this); // Agregar FocusListener
        txtNombre = new JTextField(10); // Campo para ingresar el nombre
        txtDireccion = new JTextField(10); // Campo para ingresar la dirección
        txtMail = new JTextField(10); // Campo para ingresar el email
        txtTelefono = new JTextField(10); // Campo para ingresar el teléfono

        // Inicializar los botones de radio para el género
        rbMasculino = new JRadioButton("Masculino", true); // Botón de radio para género masculino seleccionado por defecto
        rbFemenino = new JRadioButton("Femenino"); // Botón de radio para género femenino
        ButtonGroup grupoGenero = new ButtonGroup(); // Agrupar los botones de radio
        grupoGenero.add(rbMasculino);
        grupoGenero.add(rbFemenino);

        // Inicializar la casilla de verificación para promociones
        cbPromociones = new JCheckBox("Recibir promociones"); // Casilla para recibir promociones

        // Inicializar los botones de guardar y cancelar
        btnGuardar = new JButton("Guardar"); // Botón para guardar los datos
        btnCancelar = new JButton("Cancelar"); // Botón para cancelar la operación

        // Añadir componentes al panel
        panel.add(new JLabel("Cedula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Direccion:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Mail:"));
        panel.add(txtMail);
        panel.add(new JLabel("Telefono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Genero:"));
        panel.add(rbMasculino);
        panel.add(rbFemenino);
        panel.add(cbPromociones);
        panel.add(btnGuardar);
        panel.add(btnCancelar);

        // Acción del botón guardar
        btnGuardar.addActionListener(this); // Asignar el manejador de eventos al botón guardar
        // Acción del botón cancelar
        btnCancelar.addActionListener(e -> dispose()); // Cerrar la ventana al presionar cancelar

        getContentPane().add(panel); // Añadir el panel al contenido de la ventana
    }

    /**
     * Maneja las acciones de los botones.
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            // Obtener los valores de los campos
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String mail = txtMail.getText();
            String telefono = txtTelefono.getText();
            boolean generoMasculino = rbMasculino.isSelected();
            boolean generoFemenino = rbFemenino.isSelected();
            boolean recibirPromociones = cbPromociones.isSelected();

            // Verificar que todos los campos estén completos
            if (cedula.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || mail.isEmpty() || telefono.isEmpty() || (!generoMasculino && !generoFemenino)) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un nuevo cliente con los datos ingresados
            MCliente cliente = new MCliente();
            cliente.setCedula(cedula);
            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setMail(mail);
            cliente.setTelefono(telefono);
            cliente.setGenero(generoMasculino);
            cliente.setPromocion(recibirPromociones);

            // Intentar agregar el nuevo cliente al controlador
            try {
                controlador.agregarCliente(cliente); // Llamar al método del controlador para agregar el cliente
                // Limpiar los campos después de guardar
                txtCedula.setText("");
                txtNombre.setText("");
                txtDireccion.setText("");
                txtMail.setText("");
                txtTelefono.setText("");
                rbMasculino.setSelected(true); // Seleccionar "Masculino" por defecto
                rbFemenino.setSelected(false);
                cbPromociones.setSelected(false);
                JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Mostrar mensaje de error si hay una excepción
            }
        }
    }

    /**
     * Maneja el evento de perder el foco del campo de texto.
     * @param e El evento de foco.
     */
    @Override
public void focusLost(FocusEvent e) {
    if (e.getSource() == txtCedula) {
        String cedula = txtCedula.getText();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int resultado = controlador.validarCedula(cedula);
                if (resultado != 0) {
                    String mensajeError = controlador.getMensajeError(resultado);
                    JOptionPane.showMessageDialog(this, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
                } else if (controlador.cedulaExiste(cedula)) {
                    JOptionPane.showMessageDialog(this, "Cédula ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error al validar la cédula: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

    @Override
    public void focusGained(FocusEvent e) {
        // No se requiere acción al ganar el foco
    }

    /**
     * Muestra la ventana.
     */
    public void mostrar() {
        setVisible(true); // Hacer visible la ventana
    }
}
