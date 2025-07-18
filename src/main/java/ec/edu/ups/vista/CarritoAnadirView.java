package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoAnadirView extends JInternalFrame {
    private JButton btnBuscar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;
    private JPanel panelPrincipal;
    private JLabel lblTituloCarrito;
    private JLabel lblcodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel LblCantidad;
    private JLabel lblSubTotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    public CarritoAnadirView(MensajeInternacionalizacionHandler mensajes){

        super("Carrito de Compras", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(450, 500);

        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio", "Cantidad", "Subtotal"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);

        cargarDatos();

    }

    private void cargarDatos(){
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++){
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
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

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("carrito.titulo"));

        btnBuscar.setText(mensajes.get("carrito.btn.buscar"));
        btnAnadir.setText(mensajes.get("carrito.btn.anadir"));
        btnGuardar.setText(mensajes.get("carrito.btn.guardar"));
        btnLimpiar.setText(mensajes.get("carrito.btn.limpiar"));

        lblTituloCarrito.setText(mensajes.get("carrito.titulo"));  // si quieres repetir título en etiqueta
        lblcodigo.setText(mensajes.get("carrito.lbl.codigo"));
        lblNombre.setText(mensajes.get("carrito.lbl.nombre"));
        lblPrecio.setText(mensajes.get("carrito.lbl.precio"));
        LblCantidad.setText(mensajes.get("carrito.lbl.cantidad"));
        lblSubTotal.setText(mensajes.get("carrito.lbl.subtotal"));
        lblIva.setText(mensajes.get("carrito.lbl.iva"));
        lblTotal.setText(mensajes.get("carrito.lbl.total"));
    }

}