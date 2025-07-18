package ec.edu.ups.vista;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoListar extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTable tblListas;
    private JButton btnListar;
    private DefaultTableModel modelo;


    public CarritoListar(MensajeInternacionalizacionHandler mensajes) {
        setContentPane(panelPrincipal);
        setTitle("Listar Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Nombre","Productos", "SubTotal", "Total"};
        modelo.setColumnIdentifiers(columnas);
        tblListas.setModel(modelo);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTable getTblListas() {
        return tblListas;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

}
