package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class RecuperarContraseñaView extends JFrame {

    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtRes1;
    private JTextField txtRes2;
    private JTextField txtRes3;
    private JTextField txtPre1;
    private JTextField txtPre2;
    private JTextField txtPre3;
    private JTextArea textAreaContraseña;
    private JButton recuperarContraseñaButton;

    public RecuperarContraseñaView(MensajeInternacionalizacionHandler mensajes) {
        setTitle("Recuperar Contraseña");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtRes1() {
        return txtRes1;
    }

    public JTextField getTxtRes2() {
        return txtRes2;
    }

    public JTextField getTxtRes3() {
        return txtRes3;
    }

    public JTextField getTxtPre1() {
        return txtPre1;
    }

    public JTextField getTxtPre2() {
        return txtPre2;
    }

    public JTextField getTxtPre3() {
        return txtPre3;
    }

    public JTextArea getTextAreaContraseña() {
        return textAreaContraseña;
    }

    public JButton getRecuperarContraseñaButton() {
        return recuperarContraseñaButton;
    }
}
