package ec.edu.ups;

import ec.edu.ups.controlador.*;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // DAOs
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            // Vistas iniciales
            MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("es", "EC");
            LoginView loginView = new LoginView(mensajes);
            RecuperarContraseñaView recuperarView = new RecuperarContraseñaView(mensajes);
            UsuarioListaView usuarioListaView = new UsuarioListaView(mensajes);
            EliminarUsuarioView eliminarUsuarioView = new EliminarUsuarioView(mensajes);
            CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensajes);

            // Controladores
            RecuperarContraseñaController recuperarController = new RecuperarContraseñaController(usuarioDAO, recuperarView);
            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            // Vistas adicionales de usuario
            EditarUsuario editarUsuario = new EditarUsuario();

            usuarioController.setEliminarUsuarioView(eliminarUsuarioView);
            usuarioController.setUsuarioListaView(usuarioListaView);
            usuarioController.setEditarUsuarioView(editarUsuario);

            // Mostrar login
            loginView.setVisible(true);

            // Lógica tras cerrar login
            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                    if (usuarioAutenticado != null) {
                        // Inicializar vista principal y vistas relacionadas
                        MenuPrincipalView principalView = new MenuPrincipalView();

                        // Vistas producto
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoListaView productoListaView = new ProductoListaView();
                        ModificarProductoView modificarProductoView = new ModificarProductoView();
                        EliminarProductoView eliminarProductoView = new EliminarProductoView();

                        // Vistas carrito
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensajes);
                        CarritoListar carritoListarView = new CarritoListar(mensajes);
                        EliminarCarrito eliminarCarritoView = new EliminarCarrito(mensajes);
                        EditarCarritoView editarCarritoView = new EditarCarritoView();

                        // Controladores
                        ProductoController productoController = new ProductoController(
                                productoDAO,
                                productoAnadirView,
                                productoListaView,
                                carritoAnadirView,
                                modificarProductoView,
                                eliminarProductoView
                        );

                        CarritoController carritoController = new CarritoController(
                                carritoDAO,
                                productoDAO,
                                carritoAnadirView,
                                usuarioAutenticado
                        );

                        carritoController.setCarritoListarView(carritoListarView);
                        carritoController.setCarritoEliminarView(eliminarCarritoView);

                        // Mostrar bienvenida
                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                        // Configuración de menú según rol
                        if (usuarioAutenticado.getRol().equals(Rol.ADMINISTRADOR)) {
                            principalView.getMenuItemCrearProducto().addActionListener(ev -> principalView.mostrarVentana(productoAnadirView));
                            principalView.getMenuItemActualizarProducto().addActionListener(ev -> principalView.mostrarVentana(modificarProductoView));
                            principalView.getMenuItemBuscarProducto().addActionListener(ev -> principalView.mostrarVentana(productoListaView));
                            principalView.getMenuItemEliminarProducto().addActionListener(ev -> principalView.mostrarVentana(eliminarProductoView));

                            principalView.getMenuItemEditarUsuario().addActionListener(ev -> principalView.mostrarVentana(editarUsuario));
                            principalView.getMenuItemEliminarUsuario().addActionListener(ev -> principalView.mostrarVentana(eliminarUsuarioView));
                            principalView.getMenuItemUsuarioLista().addActionListener(ev -> principalView.mostrarVentana(usuarioListaView));
                        } else {
                            principalView.deshabilitarMenusAdministrador();
                            principalView.getMenuItemUsuarioLista().setEnabled(true);
                            principalView.getMenuItemUsuarioLista().addActionListener(ev -> principalView.mostrarVentana(usuarioListaView));
                        }

                        // Acciones de carrito
                        principalView.getMenuItemCrearCarrito().addActionListener(ev -> principalView.mostrarVentana(carritoAnadirView));
                        principalView.getMenuItemCarrito().addActionListener(ev -> principalView.mostrarVentana(carritoListarView));
                        principalView.getMenuItemEliminarCarrito().addActionListener(ev -> principalView.mostrarVentana(eliminarCarritoView));
                        principalView.getMenuItemModificarCarrito().addActionListener(ev -> principalView.mostrarVentana(editarCarritoView));

                        // Idiomas
                        principalView.getMenuItemIdiomaEspanol().addActionListener(ev -> principalView.cambiarIdioma("es", "EC"));
                        principalView.getMenuItemIdiomaIngles().addActionListener(ev -> principalView.cambiarIdioma("en", "US"));
                        principalView.getMenuItemIdiomaFrances().addActionListener(ev -> principalView.cambiarIdioma("fr", "FR"));

                        // Salir
                        principalView.getMenuItemSalir().addActionListener(ev -> {
                            principalView.dispose();
                            LoginView nuevoLogin = new LoginView(mensajes);
                            UsuarioController nuevoControlador = new UsuarioController(usuarioDAO, nuevoLogin);
                            nuevoLogin.setVisible(true);
                        });

                        principalView.setVisible(true);
                    }
                }
            });

            // Acción para registrar usuario
            loginView.getBtnRegistro().addActionListener(ev -> {
                UsuarioAñadirView usuarioAñadirView = new UsuarioAñadirView();
                final Respuestas[] respuestasGuardadas = new Respuestas[1];

                usuarioAñadirView.getBtnPreguntas().addActionListener(e -> {
                    PreguntasUsuario preguntas = new PreguntasUsuario();
                    preguntas.setVisible(true);
                    preguntas.getBtnGuardar().addActionListener(ev2 -> {
                        try {
                            respuestasGuardadas[0] = new Respuestas(
                                    preguntas.getTxtAmigo().getText(),
                                    preguntas.getTxtPasatiempo().getText(),
                                    preguntas.getTxtCiudad().getText(),
                                    preguntas.getTxtCancion().getText(),
                                    preguntas.getTxtColor().getText(),
                                    Integer.parseInt(preguntas.getTxtDia().getText()),
                                    Integer.parseInt(preguntas.getTxtMes().getText()),
                                    Integer.parseInt(preguntas.getTxtAño().getText())
                            );
                            JOptionPane.showMessageDialog(preguntas, "Guardado");
                            preguntas.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(preguntas, "Error en formato de fecha");
                        }
                    });
                });

                usuarioAñadirView.getBtnGuardar().addActionListener(e -> {
                    String username = usuarioAñadirView.getTxtUsuario().getText();
                    String contrasenia = usuarioAñadirView.getTxtContraseña().getText();
                    String nombre = usuarioAñadirView.getTxtNombre().getText();
                    String apellido = usuarioAñadirView.getTxtApellido().getText();
                    Rol rol = (Rol) usuarioAñadirView.getCbxRol().getSelectedItem();

                    if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                        JOptionPane.showMessageDialog(usuarioAñadirView, "Faltan campos por llenar.");
                        return;
                    }

                    Usuario nuevoUsuario = new Usuario(username, contrasenia, nombre, apellido, rol);
                    nuevoUsuario.setRespuestas(respuestasGuardadas[0]);
                    usuarioDAO.crear(nuevoUsuario);
                    JOptionPane.showMessageDialog(usuarioAñadirView, "Usuario registrado exitosamente");
                    usuarioAñadirView.dispose();
                });

                usuarioAñadirView.setVisible(true);
            });

            // Acción para recuperación de contraseña
            loginView.getRecuperarContraseñaButton().addActionListener(ev -> {
                recuperarView.getTxtNombre().setText("");
                recuperarView.getTxtApellido().setText("");
                recuperarView.getTxtRes1().setText("");
                recuperarView.getTxtRes2().setText("");
                recuperarView.getTxtRes3().setText("");
                recuperarView.getTextAreaContraseña().setText("");
                recuperarController.cargarPreguntas();
                recuperarView.setVisible(true);
            });
        });
    }
}
