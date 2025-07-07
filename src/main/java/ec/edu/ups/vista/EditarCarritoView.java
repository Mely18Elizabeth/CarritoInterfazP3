package ec.edu.ups.vista;

import javax.swing.*;

public class EditarCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox<String> txtProductosLista;
    private JComboBox<Integer> cbxCantidad;
    private JTextField txtUsuario;

    public EditarCarritoView() {
        setContentPane(panelPrincipal);
        setTitle("Editar Carrito");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(false);
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JComboBox<String> getTxtProductosLista() {
        return txtProductosLista;
    }

    public JComboBox<Integer> getCbxCantidad() {
        return cbxCantidad;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
