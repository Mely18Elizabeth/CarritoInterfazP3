package ec.edu.ups;

import ec.edu.ups.controlador.*;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.vista.*;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            LoginView loginView = new LoginView();
            RecuperarContraseñaView recuperarView = new RecuperarContraseñaView();
            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
            CarritoListar carritoListarView = new CarritoListar();
            EliminarCarrito eliminarCarritoView = new EliminarCarrito();

            RecuperarContraseñaController recuperarController = new RecuperarContraseñaController(usuarioDAO, recuperarView);
            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);
            UsuarioListaView usuarioListaView = new UsuarioListaView();
            EliminarUsuarioView eliminarUsuarioView = new EliminarUsuarioView();
            EditarUsuario editarUsuario = new EditarUsuario();

            usuarioController.setEliminarUsuarioView(eliminarUsuarioView);
            usuarioController.setUsuarioListaView(usuarioListaView);
            usuarioController.setEditarUsuarioView(editarUsuario);


            loginView.setVisible(true);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                    if (usuarioAutenticado != null) {
                        MenuPrincipalView principalView = new MenuPrincipalView();

                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoListaView productoListaView = new ProductoListaView();
                        ModificarProductoView modificarProductoView = new ModificarProductoView();
                        EliminarProductoView eliminarProductoView = new EliminarProductoView();

                        EditarUsuario editarUsuario = new EditarUsuario();

                        EliminarCarrito eliminarCarritoView = new EliminarCarrito();
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                        CarritoListar itemCarritoView = new CarritoListar();
                        EditarCarritoView editarCarritoView = new EditarCarritoView();

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

                        carritoController.setCarritoListarView(itemCarritoView);
                        carritoController.setCarritoEliminarView(eliminarCarritoView);

                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

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
                            principalView.getMenuItemUsuarioLista().setEnabled(true); // Habilita lista
                            principalView.getMenuItemUsuarioLista().addActionListener(ev -> principalView.mostrarVentana(usuarioListaView));
                        }

                        principalView.getMenuItemCrearCarrito().addActionListener(ev -> principalView.mostrarVentana(carritoAnadirView));
                        principalView.getMenuItemCarrito().addActionListener(ev -> principalView.mostrarVentana(itemCarritoView));
                        principalView.getMenuItemEliminarCarrito().addActionListener(ev -> principalView.mostrarVentana(eliminarCarritoView));
                        principalView.getMenuItemModificarCarrito().addActionListener(ev -> principalView.mostrarVentana(editarCarritoView));

                        principalView.getMenuItemIdiomaEspanol().addActionListener(ev -> principalView.cambiarIdioma("es", "EC"));
                        principalView.getMenuItemIdiomaIngles().addActionListener(ev -> principalView.cambiarIdioma("en", "US"));
                        principalView.getMenuItemIdiomaFrances().addActionListener(ev -> principalView.cambiarIdioma("fr", "FR"));

                        principalView.getMenuItemSalir().addActionListener(ev -> {
                            principalView.dispose();
                            LoginView nuevoLogin = new LoginView();
                            UsuarioController nuevoControlador = new UsuarioController(usuarioDAO, nuevoLogin);
                            nuevoLogin.setVisible(true);
                        });

                        principalView.setVisible(true);
                    }
                }
            });

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
