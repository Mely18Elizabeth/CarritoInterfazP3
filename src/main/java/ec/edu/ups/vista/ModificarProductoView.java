package ec.edu.ups.vista;

import javax.swing.*;

public class ModificarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnActualizar;

    public ModificarProductoView() {
        super("Modificar Producto", true, true, true, true);
        setContentPane(panelPrincipal);
        setSize(500, 400);
        setVisible(false); // inicialmente oculta
    }

    // Getters para acceder desde el controlador
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }
}
