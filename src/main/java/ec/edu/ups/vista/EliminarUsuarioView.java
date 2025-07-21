package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EliminarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField textEliminar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JTable tblUsuarios;
    private DefaultTableModel modelo;


    public EliminarUsuarioView(MensajeInternacionalizacionHandler mensajes) {
        super("Lista de Usuarios", true, true, true, true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        setContentPane(panelPrincipal);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Nombre","Apellido", "Usuario", "Rol"};
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);
    }

    public JTextField getTextEliminar() {
        return textEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }
}
