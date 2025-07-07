package ec.edu.ups.vista;

import javax.swing.*;

public class CarritoListar extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTable tblListas;
    private JButton btnListar;
    private JComboBox<String> listaUsuarios;

    public CarritoListar() {
        setContentPane(panelPrincipal); // Usa el panel generado por el .form
        setTitle("Listar Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(false);
    }

    // Getters para acceso a los componentes

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTable getTblListas() {
        return tblListas;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JComboBox<String> getListaUsuarios() {
        return listaUsuarios;
    }
}
