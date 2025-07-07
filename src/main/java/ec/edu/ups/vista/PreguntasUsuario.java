package ec.edu.ups.vista;

import javax.swing.*;

public class PreguntasUsuario extends JFrame {

    private JPanel panelPrincipal;

    private JTextField txtAmigo;
    private JTextField txtPasatiempo;
    private JTextField txtCiudad;
    private JTextField txtCancion;
    private JTextField txtColor;

    private JTextField txtDia;
    private JTextField txtMes;
    private JTextField txtAño;

    private JButton btnGuardar;

    public PreguntasUsuario() {
        setTitle("Preguntas personales");
        setContentPane(panelPrincipal);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JTextField getTxtAmigo() {
        return txtAmigo;
    }

    public JTextField getTxtPasatiempo() {
        return txtPasatiempo;
    }

    public JTextField getTxtCiudad() {
        return txtCiudad;
    }

    public JTextField getTxtCancion() {
        return txtCancion;
    }

    public JTextField getTxtColor() {
        return txtColor;
    }

    public JTextField getTxtDia() {
        return txtDia;
    }

    public JTextField getTxtMes() {
        return txtMes;
    }

    public JTextField getTxtAño() {
        return txtAño;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }
}
