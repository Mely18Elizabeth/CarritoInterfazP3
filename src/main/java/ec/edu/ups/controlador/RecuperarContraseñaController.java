package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Respuestas;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.RecuperarContraseñaView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecuperarContraseñaController {
    private UsuarioDAO usuarioDAO;
    private RecuperarContraseñaView view;
    private Usuario usuarioEncontrado;

    private final List<String> preguntas = List.of(
            "Nombre de Amigo",
            "Pasatiempo",
            "Ciudad de Nacimiento",
            "Canción Favorita",
            "Color Favorito"
    );

    public RecuperarContraseñaController(UsuarioDAO usuarioDAO, RecuperarContraseñaView view) {
        this.usuarioDAO = usuarioDAO;
        this.view = view;

        view.getRecuperarContraseñaButton().addActionListener(e -> verificarRespuestas());
    }

    public void cargarPreguntas() {
        List<Integer> indices = new ArrayList<>(List.of(0,1,2,3,4));
        Collections.shuffle(indices);
        List<Integer> seleccionados = indices.subList(0, 3);

        view.getTxtPre1().setText(preguntas.get(seleccionados.get(0)));
        view.getTxtPre2().setText(preguntas.get(seleccionados.get(1)));
        view.getTxtPre3().setText(preguntas.get(seleccionados.get(2)));

        view.getTxtPre1().putClientProperty("indice", seleccionados.get(0));
        view.getTxtPre2().putClientProperty("indice", seleccionados.get(1));
        view.getTxtPre3().putClientProperty("indice", seleccionados.get(2));
    }

    private void verificarRespuestas() {
        String nombre = view.getTxtNombre().getText().trim();
        String apellido = view.getTxtApellido().getText().trim();

        usuarioEncontrado = usuarioDAO.buscarPorNombreApellido(nombre, apellido);

        if (usuarioEncontrado == null) {
            JOptionPane.showMessageDialog(view, "Usuario no encontrado");
            return;
        }

        Respuestas resp = usuarioEncontrado.getRespuestas();
        if (resp == null) {
            JOptionPane.showMessageDialog(view, "No hay respuestas de seguridad registradas para este usuario.");
            return;
        }

        String res1 = view.getTxtRes1().getText().trim();
        String res2 = view.getTxtRes2().getText().trim();
        String res3 = view.getTxtRes3().getText().trim();

        int i1 = (int) view.getTxtPre1().getClientProperty("indice");
        int i2 = (int) view.getTxtPre2().getClientProperty("indice");
        int i3 = (int) view.getTxtPre3().getClientProperty("indice");

        boolean ok = compararRespuestaPorIndice(resp, i1, res1)
                && compararRespuestaPorIndice(resp, i2, res2)
                && compararRespuestaPorIndice(resp, i3, res3);

        if (ok) {
            view.getTextAreaContraseña().setText(
                    "Usuario: " + usuarioEncontrado.getUsername() +
                            "\nContraseña: " + usuarioEncontrado.getContrasenia()
            );
        } else {
            view.getTextAreaContraseña().setText("Respuestas incorrectas.");
        }
    }

    private boolean compararRespuestaPorIndice(Respuestas r, int indice, String respuestaIngresada) {
        switch (indice) {
            case 0: return r.getNombreAmigo().equalsIgnoreCase(respuestaIngresada);
            case 1: return r.getPasatiempo().equalsIgnoreCase(respuestaIngresada);
            case 2: return r.getCiudadNacimiento().equalsIgnoreCase(respuestaIngresada);
            case 3: return r.getCancionFav().equalsIgnoreCase(respuestaIngresada);
            case 4: return r.getColorFav().equalsIgnoreCase(respuestaIngresada);
            default: return false;
        }
    }
}
