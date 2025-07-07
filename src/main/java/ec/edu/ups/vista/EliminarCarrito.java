package ec.edu.ups.vista;

import javax.swing.*;

public class EliminarCarrito extends JInternalFrame {

    private JTextField textEliminar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JScrollPane tablaEliminar;
    private JComboBox listaProductos;
    private JPanel panelPrincipal;

    public EliminarCarrito() {
        setContentPane(panelPrincipal);
        setTitle("Eliminar Carrito");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
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

    public JScrollPane getTablaEliminar() {
        return tablaEliminar;
    }


    public JComboBox getListaProductos() {
        return listaProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
