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
            LoginView loginView = new LoginView();
            RecuperarContraseñaView recuperarView = new RecuperarContraseñaView();
            RecuperarContraseñaController recuperarController = new RecuperarContraseñaController(usuarioDAO, recuperarView);

            loginView.setVisible(true);
            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                    if (usuarioAutenticado != null) {

                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();

                        MenuPrincipalView principalView = new MenuPrincipalView();
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                        EliminarProductoView eliminarProductoView = new EliminarProductoView();
                        EliminarUsuarioView eliminarUsuarioView = new EliminarUsuarioView();
                        CarritoListar itemCarritoView = new CarritoListar();
                        ModificarProductoView modificarProductoView = new ModificarProductoView();
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoListaView productoListaView = new ProductoListaView();
                        EliminarCarrito eliminarCarritoView = new EliminarCarrito();

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
                                usuarioAutenticado  // <--- aquí está la clave
                        );

                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                        if (usuarioAutenticado.getRol().equals(Rol.ADMINISTRADOR)) {
                            principalView.getMenuItemCrearProducto().addActionListener(ev -> {
                                if (!productoAnadirView.isVisible()) {
                                    principalView.getjDesktopPane().add(productoAnadirView);
                                    productoAnadirView.setVisible(true);
                                }
                            });
                            principalView.getMenuItemActualizarProducto().addActionListener(ev -> {
                                if (!modificarProductoView.isVisible()) {
                                    principalView.getjDesktopPane().add(modificarProductoView);
                                    modificarProductoView.setVisible(true);
                                }
                            });
                            principalView.getMenuItemBuscarProducto().addActionListener(ev -> {
                                if (!productoListaView.isVisible()) {
                                    principalView.getjDesktopPane().add(productoListaView);
                                    productoListaView.setVisible(true);
                                }
                            });
                            principalView.getMenuItemEliminarProducto().addActionListener(ev -> {
                                if (!eliminarProductoView.isVisible()) {
                                    principalView.getjDesktopPane().add(eliminarProductoView);
                                    eliminarProductoView.setVisible(true);
                                }
                            });
                            principalView.getMenuItemEliminarUsuario().addActionListener(ev -> {
                                if (!eliminarUsuarioView.isVisible()) {
                                    principalView.getjDesktopPane().add(eliminarUsuarioView);
                                    eliminarUsuarioView.setVisible(true);
                                }
                            });
                            principalView.getMenuItemUsuarioLista().addActionListener(ev -> {
                                if (!itemCarritoView.isVisible()) {
                                    principalView.getjDesktopPane().add(itemCarritoView);
                                    itemCarritoView.setVisible(true);
                                }
                            });
                        } else {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        principalView.getMenuItemCrearCarrito().addActionListener(ev -> {
                            if (!carritoAnadirView.isVisible()) {
                                principalView.getjDesktopPane().add(carritoAnadirView);
                                carritoAnadirView.setVisible(true);
                            }
                        });
                        principalView.getMenuItemCarrito().addActionListener(ev -> {
                            if (!itemCarritoView.isVisible()) {
                                principalView.getjDesktopPane().add(itemCarritoView);
                                itemCarritoView.setVisible(true);
                            }
                        });
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
                            String amigo = preguntas.getTxtAmigo().getText();
                            String pasatiempo = preguntas.getTxtPasatiempo().getText();
                            String ciudad = preguntas.getTxtCiudad().getText();
                            String cancion = preguntas.getTxtCancion().getText();
                            String color = preguntas.getTxtColor().getText();
                            int dia = Integer.parseInt(preguntas.getTxtDia().getText());
                            int mes = Integer.parseInt(preguntas.getTxtMes().getText());
                            int anio = Integer.parseInt(preguntas.getTxtAño().getText());
                            respuestasGuardadas[0] = new Respuestas(amigo, pasatiempo, ciudad, cancion, color, dia, mes, anio);
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
                System.out.println("Click en botón Recuperar Contraseña"); // 🧪 Línea nueva
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
