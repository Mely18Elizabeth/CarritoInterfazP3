package ec.edu.ups.vista;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion;
    private JButton btnCancelar;
    private JButton recuperarContraseñaButton;
    private JComboBox comboBoxIdioma;
    private JButton btnRegistro;

    public LoginView() {
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

    public JComboBox getComboBoxIdioma() {
        return comboBoxIdioma;
    }

    public void setComboBoxIdioma(JComboBox comboBoxIdioma) {
        this.comboBoxIdioma = comboBoxIdioma;
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

}