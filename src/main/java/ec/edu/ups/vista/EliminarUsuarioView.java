package ec.edu.ups.vista;

import javax.swing.*;

public class EliminarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField textEliminar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JScrollPane scrollTabla;
    private JTable tblUsuarios;

    public EliminarUsuarioView() {
        setContentPane(panelPrincipal);
        setTitle("Eliminar Usuario");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(false);
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
