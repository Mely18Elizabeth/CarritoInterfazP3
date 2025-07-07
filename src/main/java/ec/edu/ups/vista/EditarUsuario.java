package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditarUsuario extends JInternalFrame {

    private JTextField txtBuscarUsuario;
    private JButton btnBuscar;
    private JTable tblUsuarios;
    private JButton btnActualizar;
    private JComboBox cbxRol;
    private JTextField txtContraseña;
    private JPanel panelPrincipal;
    private DefaultTableModel modelo;


    public EditarUsuario() {
        setContentPane(panelPrincipal); // Usa el panel diseñado en el .form
        setTitle("Editar Usuario");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Nombre","Apellido", "Usuario", "Rol"};
        modelo.setColumnIdentifiers(columnas);
        cbxRol.setModel(new DefaultComboBoxModel<>(Rol.values())); // 👈 Carga los roles

        tblUsuarios.setModel(modelo);
    }

    // Getters para acceder a los componentes desde el controlador

    public JTextField getTxtBuscarUsuario() {
        return txtBuscarUsuario;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JComboBox getCbxRol() {
        return cbxRol;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
