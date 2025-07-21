package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditarCarritoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton btnBuscar;
    private JTable tblListas;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox<String> txtProductosLista;
    private JComboBox<Integer> cbxCantidad;
    private JTextField txtUsuario;
    private DefaultTableModel modelo;

    public EditarCarritoView() {
        setContentPane(panelPrincipal);
        setTitle("Editar Carrito");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        String[] columnas = {"Código", "Producto", "Cantidad", "Precio Unitario", "Subtotal"};
        modelo = new DefaultTableModel(columnas, 0);
        tblListas.setModel(modelo);

        cargarCantidades();
    }

    private void cargarCantidades() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public JTable getTblListas() {
        return tblListas;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblProductos() {
        return tblListas;
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
