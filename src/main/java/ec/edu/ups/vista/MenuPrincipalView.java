package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.modelo.Rol; // Import Rol enum

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuario;
    private JMenu menuIdioma;
    private JMenu menuSalir;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;

    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemModificarCarrito;
    private JMenuItem menuItemCarrito;
    private JMenuItem menuItemCrearCarrito;

    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemUsuarioLista;
    private JMenuItem menuItemEditarUsuario;

    private JMenuItem menuItemIdiomaEspanol;
    private JMenuItem menuItemIdiomaIngles;
    private JMenuItem menuItemIdiomaFrances;
    private JDesktopPane jDesktopPane;
    private JMenuItem menuItemSalir;

    public MenuPrincipalView() {
        mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler("es", "EC");
        initComponents();
    }

    private void initComponents() {
        jDesktopPane = new FondoConCarrito();
        menuBar = new JMenuBar();

        menuProducto = new JMenu(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito = new JMenu(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuUsuario = new JMenu(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuIdioma = new JMenu(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir = new JMenu(mensajeInternacionalizacionHandler.get("menu.salir"));

        menuItemCrearProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.crear"));
        menuItemEliminarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.eliminar"));
        menuItemActualizarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.actualizar"));
        menuItemBuscarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.buscar"));

        menuItemCrearCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.crear"));
        menuItemCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.item"));
        menuItemEliminarCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.eliminar"));
        menuItemModificarCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.actualizar"));

        menuItemEliminarUsuario = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.usuario.eliminar"));
        menuItemUsuarioLista = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.usuario.lista"));
        menuItemEditarUsuario = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.usuario.editar"));

        menuItemIdiomaEspanol = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.fr"));

        menuItemSalir = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.salir.salir"));

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemModificarCarrito);

        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.add(menuItemUsuarioLista);
        menuUsuario.add(menuItemEditarUsuario);

        menuIdioma.add(menuItemIdiomaEspanol);
        menuIdioma.add(menuItemIdiomaIngles);
        menuIdioma.add(menuItemIdiomaFrances);

        menuSalir.add(menuItemSalir);

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    public JMenuItem getMenuItemUsuarioLista() {
        return menuItemUsuarioLista;
    }

    public JMenuItem getMenuItemIdiomaEspanol() {
        return menuItemIdiomaEspanol;
    }

    public JMenuItem getMenuItemIdiomaIngles() {
        return menuItemIdiomaIngles;
    }

    public JMenuItem getMenuItemIdiomaFrances() {
        return menuItemIdiomaFrances;
    }

    public JMenuItem getMenuItemCarrito() {
        return menuItemCarrito;
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    public JMenuItem getMenuItemModificarCarrito() {
        return menuItemModificarCarrito;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public JMenuItem getMenuItemEditarUsuario() {
        return menuItemEditarUsuario;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void configureMenusForRole(Rol role) {
        menuItemCrearProducto.setEnabled(true);
        menuItemActualizarProducto.setEnabled(true);
        menuItemEliminarProducto.setEnabled(true);
        menuItemBuscarProducto.setEnabled(true);

        menuItemEliminarUsuario.setEnabled(true);
        menuItemUsuarioLista.setEnabled(true);
        menuItemEditarUsuario.setEnabled(true);

        menuItemEliminarCarrito.setEnabled(true);
        menuItemModificarCarrito.setEnabled(true);
        menuItemCrearCarrito.setEnabled(true);
        menuItemCarrito.setEnabled(true);

        menuItemIdiomaEspanol.setEnabled(true);
        menuItemIdiomaIngles.setEnabled(true);
        menuItemIdiomaFrances.setEnabled(true);
        menuItemSalir.setEnabled(true);

        if (role.equals(Rol.ADMINISTRADOR)) {
            menuItemCrearProducto.setEnabled(true);
            menuItemActualizarProducto.setEnabled(true);
            menuItemEliminarProducto.setEnabled(true);
            menuItemBuscarProducto.setEnabled(true);

            menuItemEliminarUsuario.setEnabled(true);
            menuItemUsuarioLista.setEnabled(true);
            menuItemEditarUsuario.setEnabled(true);

            menuItemEliminarCarrito.setEnabled(true);
            menuItemModificarCarrito.setEnabled(true);
            menuItemCrearCarrito.setEnabled(true);
            menuItemCarrito.setEnabled(true);

        } else if (role.equals(Rol.USUARIO)) {
            menuItemCrearProducto.setEnabled(true);
            menuItemBuscarProducto.setEnabled(true);

            menuItemUsuarioLista.setEnabled(true);
            menuItemEditarUsuario.setEnabled(true);

            menuItemEliminarCarrito.setEnabled(true);
            menuItemModificarCarrito.setEnabled(true);
            menuItemCrearCarrito.setEnabled(true);
            menuItemCarrito.setEnabled(true);
        }
    }

    public void deshabilitarMenusAdministrador() {
        menuItemCrearProducto.setEnabled(false);
        menuItemActualizarProducto.setEnabled(false);
        menuItemEliminarProducto.setEnabled(false);
        menuItemEliminarUsuario.setEnabled(false);
        menuItemEditarUsuario.setEnabled(false);
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensajeInternacionalizacionHandler.setLenguaje(lenguaje, pais);

        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));

        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuIdioma.setText(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir"));

        menuItemCrearProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.crear"));
        menuItemEliminarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.eliminar"));
        menuItemActualizarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.actualizar"));
        menuItemBuscarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.buscar"));

        menuItemCrearCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.crear"));
        menuItemCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.item"));
        menuItemEliminarCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.eliminar"));
        menuItemModificarCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.actualizar"));

        menuItemEliminarUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.eliminar"));
        menuItemUsuarioLista.setText(mensajeInternacionalizacionHandler.get("menu.usuario.lista"));
        menuItemEditarUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario.editar"));

        menuItemIdiomaEspanol.setText(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles.setText(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances.setText(mensajeInternacionalizacionHandler.get("menu.idioma.fr"));

        menuItemSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir.salir"));
    }

    public void mostrarVentana(JInternalFrame ventana) {
        if (!ventana.isVisible()) {
            jDesktopPane.add(ventana);
            ventana.setVisible(true);
        }
        try {
            ventana.setSelected(true);
            ventana.toFront();
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
