package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;

import javax.swing.*;

public class UsuarioAñadirView extends JFrame {

    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JComboBox<Rol> cbxRol;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JTextField txtUsuario;
    private JTextField txtApellido;
    private JTextField txtContraseña;
    private JButton btnPreguntas;


    public UsuarioAñadirView() {
        setTitle("Registrar nuevo usuario");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        cbxRol.addItem(Rol.ADMINISTRADOR);
        cbxRol.addItem(Rol.USUARIO);
    }



    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JComboBox<Rol> getCbxRol() {
        return cbxRol;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JButton getBtnPreguntas() {
        return btnPreguntas;
    }

    public void setBtnPreguntas(JButton btnPreguntas) {
        this.btnPreguntas = btnPreguntas;
    }

}
