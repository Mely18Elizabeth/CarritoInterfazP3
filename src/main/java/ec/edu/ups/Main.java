package ec.edu.ups;

import ec.edu.ups.controlador.*;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.excepciones.CedulaInvalidaException;
import ec.edu.ups.excepciones.ContraseniaInvalidaException;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.ValidadorUsuario;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ArchivoView archivoView = new ArchivoView();
            JFrame ventanaInicial = new JFrame("Seleccionar tipo de almacenamiento");
            ventanaInicial.setContentPane(archivoView.getPanelPrincipal());
            ventanaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventanaInicial.setSize(600, 400);
            ventanaInicial.setLocationRelativeTo(null);
            ventanaInicial.setVisible(true);

            // Acción para seleccionar carpeta
            archivoView.getBtnRuta().addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    archivoView.getTxtRuta().setText(chooser.getSelectedFile().getAbsolutePath());
                }
            });

            ActionListener iniciarConTipo = e -> {
                String tipo = ((JButton) e.getSource()).getText().toUpperCase();
                String ruta = archivoView.getTxtRuta().getText().trim();

                if (!tipo.contains("MEMORIA") && ruta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta de carpeta.");
                    return;
                }

                UsuarioDAO usuarioDAO;
                ProductoDAO productoDAO;
                CarritoDAO carritoDAO;

                if (tipo.contains("TEXTO")) {
                    usuarioDAO = new UsuarioDAOArchivoTexto(ruta);
                    productoDAO = new ProductoDAOArchivoTexto(ruta);
                    carritoDAO = new CarritoDAOArchivoTexto(ruta);
                } else if (tipo.contains("BINARIO")) {
                    usuarioDAO = new UsuarioDAOArchivoBinario(ruta);
                    productoDAO = new ProductoDAOArchivoBinario(ruta);
                    carritoDAO = new CarritoDAOArchivoBinario(ruta);
                } else {
                    usuarioDAO = new UsuarioDAOMemoria();
                    productoDAO = new ProductoDAOMemoria();
                    carritoDAO = new CarritoDAOMemoria();
                }

                ventanaInicial.dispose();
                // Crear las vistas y controladores como siempre
                MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("es", "EC");
                LoginView loginView = new LoginView(mensajes);
                RecuperarContraseñaView recuperarView = new RecuperarContraseñaView(mensajes);
                UsuarioListaView usuarioListaView = new UsuarioListaView(mensajes);
                EliminarUsuarioView eliminarUsuarioView = new EliminarUsuarioView(mensajes);
                CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensajes);
                EditarUsuario editarUsuario = new EditarUsuario();

                RecuperarContraseñaController recuperarController = new RecuperarContraseñaController(usuarioDAO, recuperarView);
                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

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
                            carritoController.setEditarCarritoView(editarCarritoView);
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

                    // Botón para abrir preguntas de seguridad
                    usuarioAñadirView.getBtnPreguntas().addActionListener(eventoPreguntas -> {
                        PreguntasUsuario preguntas = new PreguntasUsuario();
                        preguntas.setVisible(true);

                        preguntas.getBtnGuardar().addActionListener(eventoGuardarPreguntas -> {
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

// Botón para guardar el usuario
                    usuarioAñadirView.getBtnGuardar().addActionListener(eventoGuardarUsuario -> {
                        String username = usuarioAñadirView.getTxtUsuario().getText().trim();
                        String contrasenia = usuarioAñadirView.getTxtContraseña().getText().trim();
                        String nombre = usuarioAñadirView.getTxtNombre().getText().trim();
                        String apellido = usuarioAñadirView.getTxtApellido().getText().trim();
                        Rol rol = (Rol) usuarioAñadirView.getCbxRol().getSelectedItem();

                        // Verificar campos obligatorios
                        if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                            JOptionPane.showMessageDialog(usuarioAñadirView, "Faltan campos por llenar.");
                            return;
                        }

                        try {
                            ValidadorUsuario.validarCedula(username);
                            ValidadorUsuario.validarContrasenia(contrasenia);
                        } catch (CedulaInvalidaException | ContraseniaInvalidaException ex) {
                            JOptionPane.showMessageDialog(usuarioAñadirView, ex.getMessage());
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
            };

            archivoView.getBtnMemoriaDao().addActionListener(iniciarConTipo);
            archivoView.getBtnATexto().addActionListener(iniciarConTipo);
            archivoView.getBtnABinario().addActionListener(iniciarConTipo);
        });
    }
}
