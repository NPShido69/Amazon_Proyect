package com.mycompany.amazon;

import javax.swing.*;
import java.awt.event.*;

public class VModificarCliente extends JFrame implements ActionListener {
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
    private CCliente cCliente;
    private MCliente cliente;

    /**
     * Constructor de la clase VModificarCliente.
     * @param cCliente El controlador de clientes.
     * @param cliente El cliente a modificar.
     */
    public VModificarCliente(CCliente cCliente, MCliente cliente) {
        this.cCliente = cCliente;
        this.cliente = cliente;
        initComponents(); // Inicializar los componentes de la interfaz
    }

    /**
     * Método para inicializar los componentes de la interfaz.
     */
    private void initComponents() {
        setTitle("Modificar Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Inicializar los campos de texto con los datos del cliente
        txtCedula = new JTextField(10);
        txtCedula.setText(cliente.getCedula());
        txtCedula.setEnabled(false);

        txtNombre = new JTextField(cliente.getNombre(), 10);
        txtDireccion = new JTextField(cliente.getDireccion(), 10);
        txtMail = new JTextField(cliente.getMail(), 10);
        txtTelefono = new JTextField(cliente.getTelefono(), 10);

        // Inicializar los botones de radio para el género
        rbMasculino = new JRadioButton("Masculino");
        rbFemenino = new JRadioButton("Femenino");
        ButtonGroup grupoGenero = new ButtonGroup();
        grupoGenero.add(rbMasculino);
        grupoGenero.add(rbFemenino);
        if (cliente.getGenero()) {
            rbMasculino.setSelected(true);
        } else {
            rbFemenino.setSelected(true);
        }

        // Inicializar la casilla de verificación para promociones
        cbPromociones = new JCheckBox("Recibir promociones", cliente.getPromocion());

        // Inicializar los botones de guardar y cancelar
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        // Añadir componentes al panel
        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Mail:"));
        panel.add(txtMail);
        panel.add(new JLabel("Telefono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Género:"));
        panel.add(rbMasculino);
        panel.add(rbFemenino);
        panel.add(new JLabel("Promociones:"));
        panel.add(cbPromociones);
        panel.add(btnGuardar);
        panel.add(btnCancelar);

        // Acción del botón guardar
        btnGuardar.addActionListener(this);
        // Acción del botón cancelar
        btnCancelar.addActionListener(e -> dispose());

        getContentPane().add(panel);
    }

    /**
     * Maneja las acciones de los botones.
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            // Obtener los valores de los campos
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String mail = txtMail.getText();
            String telefono = txtTelefono.getText();
            boolean generoMasculino = rbMasculino.isSelected();
            boolean generoFemenino = rbFemenino.isSelected();
            boolean recibirPromociones = cbPromociones.isSelected();

            // Verificar que todos los campos estén completos
            if (nombre.isEmpty() || direccion.isEmpty() || mail.isEmpty() || telefono.isEmpty() || (!generoMasculino && !generoFemenino)) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar los datos del cliente
            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setMail(mail);
            cliente.setTelefono(telefono);
            cliente.setGenero(generoMasculino);
            cliente.setPromocion(recibirPromociones);

            JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar la ventana después de guardar
        }
    }

    /**
     * Muestra la ventana.
     */
    public void mostrar() {
        setVisible(true);
    }
}






