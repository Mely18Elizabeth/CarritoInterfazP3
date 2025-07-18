package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.EditarUsuario;
import ec.edu.ups.vista.EliminarUsuarioView;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.UsuarioListaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private UsuarioListaView usuarioListaView;
    private EliminarUsuarioView eliminarUsuarioView;
    private EditarUsuario editarUsuarioView;



    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosEnVistas();
    }
    public void setEliminarUsuarioView(EliminarUsuarioView eliminarUsuarioView) {
        this.eliminarUsuarioView = eliminarUsuarioView;

        eliminarUsuarioView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        eliminarUsuarioView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
    }
    private void buscarUsuarioParaEliminar() {
        String username = eliminarUsuarioView.getTextEliminar().getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(eliminarUsuarioView, "Ingrese un nombre de usuario para buscar.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        DefaultTableModel modelo = (DefaultTableModel) eliminarUsuarioView.getTblUsuarios().getModel();
        modelo.setRowCount(0);

        if (usuario != null) {
            Object[] fila = {
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getUsername(),
                    usuario.getRol().name()
            };
            modelo.addRow(fila);
        } else {
            JOptionPane.showMessageDialog(eliminarUsuarioView, "Usuario no encontrado.");
        }
    }

    private void eliminarUsuario() {
        String username = eliminarUsuarioView.getTextEliminar().getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(eliminarUsuarioView, "Ingrese un nombre de usuario para eliminar.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(eliminarUsuarioView, "No se encontró al usuario.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                eliminarUsuarioView,
                "¿Está seguro de eliminar al usuario: " + username + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(username);
            ((DefaultTableModel) eliminarUsuarioView.getTblUsuarios().getModel()).setRowCount(0);
            JOptionPane.showMessageDialog(eliminarUsuarioView, "Usuario eliminado correctamente.");
        }
    }



    public void setUsuarioListaView(UsuarioListaView usuarioListaView) {
        this.usuarioListaView = usuarioListaView;

        usuarioListaView.getBtnListar().addActionListener(e -> listarUsuarios());
        usuarioListaView.getBtnBuscar().addActionListener(e -> buscarUsuario());
    }


    private void buscarUsuario() {
        String usernameBuscar = usuarioListaView.getTxtBuscar().getText().trim();
        if (usernameBuscar.isEmpty()) {
            usuarioListaView.mostrarMensaje("Ingrese un nombre de usuario para buscar.");
            return;
        }
        Usuario encontrado = usuarioDAO.buscarPorUsername(usernameBuscar);
        String[] columnas = {"Nombre", "Apellido", "Usuario", "Rol"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        if (encontrado != null) {
            Object[] fila = {
                    encontrado.getNombre(),
                    encontrado.getApellido(),
                    encontrado.getUsername(),
                    encontrado.getRol().name()
            };
            modelo.addRow(fila);
        } else {
            usuarioListaView.mostrarMensaje("Usuario no encontrado.");
        }
        usuarioListaView.getTblUsuarios().setModel(modelo);
    }



    private void autenticar() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        } else {
            loginView.dispose();
        }
    }



    private void configurarEventosEnVistas() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticar());
    }


    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    private void listarUsuarios() {
        String[] columnas = {"Nombre", "Apellido", "Usuario", "Rol"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Usuario u : usuarioDAO.listarTodos()) {
            Object[] fila = {
                    u.getNombre(),
                    u.getApellido(),
                    u.getUsername(),
                    u.getRol().name()
            };
            modelo.addRow(fila);
        }

        usuarioListaView.getTblUsuarios().setModel(modelo);
    }


    public void setEditarUsuarioView(EditarUsuario editarUsuarioView) {
        this.editarUsuarioView = editarUsuarioView;

        editarUsuarioView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEditar());
        editarUsuarioView.getBtnActualizar().addActionListener(e -> actualizarUsuario());
    }

    private void buscarUsuarioParaEditar() {
        String username = editarUsuarioView.getTxtBuscarUsuario().getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(editarUsuarioView, "Ingrese un nombre de usuario para buscar.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        DefaultTableModel modelo = (DefaultTableModel) editarUsuarioView.getTblUsuarios().getModel();
        modelo.setRowCount(0); // limpiar tabla

        if (usuario != null) {
            Object[] fila = {
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getUsername(),
                    usuario.getRol().name()
            };
            modelo.addRow(fila);

            // Opcional: llenar los campos de contraseña y rol con los datos actuales
            editarUsuarioView.getTxtContraseña().setText(usuario.getContrasenia());
            editarUsuarioView.getCbxRol().setSelectedItem(usuario.getRol());

        } else {
            JOptionPane.showMessageDialog(editarUsuarioView, "Usuario no encontrado.");
        }
    }

    private void actualizarUsuario() {
        String username = editarUsuarioView.getTxtBuscarUsuario().getText().trim();
        String nuevaContrasena = editarUsuarioView.getTxtContraseña().getText().trim();
        Rol nuevoRol = (Rol) editarUsuarioView.getCbxRol().getSelectedItem();

        if (username.isEmpty() || nuevaContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(editarUsuarioView, "Todos los campos deben estar llenos.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(editarUsuarioView, "Usuario no encontrado.");
            return;
        }

        usuario.setContrasenia(nuevaContrasena);
        usuario.setRol(nuevoRol);
        usuarioDAO.actualizar(usuario);

        JOptionPane.showMessageDialog(editarUsuarioView, "Usuario actualizado correctamente.");

        // Limpiar tabla y campos
        DefaultTableModel modelo = (DefaultTableModel) editarUsuarioView.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        editarUsuarioView.getTxtBuscarUsuario().setText("");
        editarUsuarioView.getTxtContraseña().setText("");
        editarUsuarioView.getCbxRol().setSelectedIndex(0);
    }

}
