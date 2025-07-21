package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion;
    private JButton btnCancelar;
    private JButton recuperarContraseñaButton;
    private JButton btnRegistro;
    private JLabel labelSesion;
    private JLabel lblCedula;
    private JLabel lblContraseña;
    private JLabel lblIdioma;
    private JButton btnEspañol;
    private JButton btnIngles;
    private JButton btnFrances;

    private MensajeInternacionalizacionHandler mensajes;

    public LoginView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
        setContentPane(panelPrincipal);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JButton getRecuperarContraseñaButton() {
        return recuperarContraseñaButton;
    }

    public void setRecuperarContraseñaButton(JButton recuperarContraseñaButton) {
        this.recuperarContraseñaButton = recuperarContraseñaButton;
    }

    public JButton getBtnRegistro() {
        return btnRegistro;
    }

    public void setBtnRegistro(JButton btnRegistro) {
        this.btnRegistro = btnRegistro;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnEspañol() {
        return btnEspañol;
    }

    public void setBtnEspañol(JButton btnEspañol) {
        this.btnEspañol = btnEspañol;
    }

    public JButton getBtnIngles() {
        return btnIngles;
    }

    public void setBtnIngles(JButton btnIngles) {
        this.btnIngles = btnIngles;
    }

    public JButton getBtnFrances() {
        return btnFrances;
    }

    public void setBtnFrances(JButton btnFrances) {
        this.btnFrances = btnFrances;
    }

    public MensajeInternacionalizacionHandler getMensajes() {
        return mensajes;
    }

    public void setMensajes(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;
    }
}