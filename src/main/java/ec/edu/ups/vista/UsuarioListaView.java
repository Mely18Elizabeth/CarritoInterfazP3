package ec.edu.ups.vista;

import javax.swing.*;

public class UsuarioListaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblUsuarios;
    private JButton btnListar;

    public UsuarioListaView() {
        super("Lista de Usuarios", true, true, true, true);
        setContentPane(panelPrincipal);
        setSize(500, 400);
        setVisible(false);  // No visible por defecto
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public void setTblUsuarios(JTable tblUsuarios) {
        this.tblUsuarios = tblUsuarios;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }
}
