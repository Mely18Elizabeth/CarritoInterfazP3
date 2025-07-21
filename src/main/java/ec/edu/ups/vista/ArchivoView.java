package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ArchivoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JLabel lblTituloCarrito;
    private JButton btnMemoriaDao;
    private JTextField txtRuta;
    private JButton btnRuta;
    private JButton btnATexto;
    private JButton btnABinario;
    private JButton btnAceptar;

    public ArchivoView() {
        setContentPane(panelPrincipal);
        setTitle("Archivos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(false);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getLblTituloCarrito() {
        return lblTituloCarrito;
    }

    public void setLblTituloCarrito(JLabel lblTituloCarrito) {
        this.lblTituloCarrito = lblTituloCarrito;
    }

    public JButton getBtnMemoriaDao() {
        return btnMemoriaDao;
    }

    public void setBtnMemoriaDao(JButton btnMemoriaDao) {
        this.btnMemoriaDao = btnMemoriaDao;
    }

    public JTextField getTxtRuta() {
        return txtRuta;
    }

    public void setTxtRuta(JTextField txtRuta) {
        this.txtRuta = txtRuta;
    }

    public JButton getBtnRuta() {
        return btnRuta;
    }

    public void setBtnRuta(JButton btnRuta) {
        this.btnRuta = btnRuta;
    }

    public JButton getBtnATexto() {
        return btnATexto;
    }

    public void setBtnATexto(JButton btnATexto) {
        this.btnATexto = btnATexto;
    }

    public JButton getBtnABinario() {
        return btnABinario;
    }

    public void setBtnABinario(JButton btnABinario) {
        this.btnABinario = btnABinario;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }
}
