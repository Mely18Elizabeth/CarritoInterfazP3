package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Objects;

public class EliminarProductoView extends JInternalFrame {
    private JTextField textEliminar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JScrollPane tablaEliminar;
    private DefaultTableModel modelo;

    public EliminarProductoView() {

        setContentPane(panelPrincipal);
        setTitle("Eliminar producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);

    }

    public JTextField getTextEliminar() {
        return textEliminar;
    }

    public void setTextEliminar(JTextField textEliminar) {
        this.textEliminar = textEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JScrollPane getTablaEliminar() {
        return tablaEliminar;
    }

    public void setTablaEliminar(JScrollPane tablaEliminar) {
        this.tablaEliminar = tablaEliminar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarProductosEnTabla(List<Producto> productos) {
        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        modelo.setRowCount(0);
        for (Producto p : productos) {
            Object[] fila = { p.getCodigo(), p.getNombre(), p.getPrecio() };
            modelo.addRow(fila);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EliminarProductoView that = (EliminarProductoView) o;
        return Objects.equals(textEliminar, that.textEliminar) && Objects.equals(btnBuscar, that.btnBuscar) && Objects.equals(btnEliminar, that.btnEliminar) && Objects.equals(tblProductos, that.tblProductos) && Objects.equals(panelPrincipal, that.panelPrincipal) && Objects.equals(tablaEliminar, that.tablaEliminar) && Objects.equals(modelo, that.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textEliminar, btnBuscar, btnEliminar, tblProductos, panelPrincipal, tablaEliminar, modelo);
    }
}
