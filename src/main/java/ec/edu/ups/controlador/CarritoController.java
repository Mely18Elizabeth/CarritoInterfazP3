package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.CarritoAnadirView;
import ec.edu.ups.vista.CarritoListar;
import ec.edu.ups.vista.EditarCarritoView;
import ec.edu.ups.vista.EliminarCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private Carrito carrito;
    private CarritoListar carritoListarView;
    private EliminarCarrito carritoEliminarView;
    private EditarCarritoView editarCarritoView;
    private Carrito carritoEditando;


    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             Usuario usuarioActual) {

        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carrito = new Carrito();
        this.carrito.setUsuario(usuarioActual);
        configurarEventosEnVistas();
    }

    private void cargarTablaEditar() {
        DefaultTableModel modelo = (DefaultTableModel) editarCarritoView.getTblProductos().getModel();
        modelo.setRowCount(0);

        for (ItemCarrito item : carritoEditando.obtenerItems()) {
            Producto p = item.getProducto();
            int cantidad = item.getCantidad();
            double precio = p.getPrecio();
            double subtotal = precio * cantidad;

            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    cantidad,
                    precio,
                    subtotal
            });
        }
    }

    private void cargarProductosEnCombo() {
        List<Producto> productos = productoDAO.listarTodos();
        DefaultComboBoxModel<String> modeloProductos = new DefaultComboBoxModel<>();

        for (Producto p : productos) {
            modeloProductos.addElement(p.getCodigo() + " - " + p.getNombre());
        }
        editarCarritoView.getTxtProductosLista().setModel(modeloProductos);
    }


    private void actualizarTotalesEditar() {
        if (carritoEditando == null) return;
        double subtotal = carritoEditando.calcularSubtotal();
        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        editarCarritoView.getTxtSubtotal().setText(String.format("%.2f", subtotal));
        editarCarritoView.getTxtIva().setText(String.format("%.2f", iva));
        editarCarritoView.getTxtTotal().setText(String.format("%.2f", total));
    }

    private void limpiarTotalesEditar() {
        editarCarritoView.getTxtSubtotal().setText("");
        editarCarritoView.getTxtIva().setText("");
        editarCarritoView.getTxtTotal().setText("");
    }

    private void guardarCambiosEditarCarrito() {
        if (carritoEditando == null) {
            JOptionPane.showMessageDialog(editarCarritoView, "Primero busque un carrito válido.");
            return;
        }

        int productoIndex = editarCarritoView.getTxtProductosLista().getSelectedIndex();
        if (productoIndex < 0) {
            JOptionPane.showMessageDialog(editarCarritoView, "Seleccione un producto para agregar.");
            return;
        }

        Producto productoSeleccionado = productoDAO.listarTodos().get(productoIndex);
        int cantidadSeleccionada = (int) editarCarritoView.getCbxCantidad().getSelectedItem();

        ItemCarrito itemExistente = null;
        for (ItemCarrito item : carritoEditando.obtenerItems()) {
            if (item.getProducto().getCodigo().equals(productoSeleccionado.getCodigo())) {
                itemExistente = item;
                break;
            }
        }

        if (itemExistente != null) {
            int nuevaCantidad = itemExistente.getCantidad() + cantidadSeleccionada;
            if (nuevaCantidad > 20) {
                JOptionPane.showMessageDialog(editarCarritoView, "La cantidad total no puede superar 20.");
                return;
            }
            itemExistente.setCantidad(nuevaCantidad);
        } else {
            if (cantidadSeleccionada > 20) {
                JOptionPane.showMessageDialog(editarCarritoView, "La cantidad no puede ser mayor a 20.");
                return;
            }
            carritoEditando.obtenerItems().add(new ItemCarrito(productoSeleccionado, cantidadSeleccionada));
        }

        carritoDAO.actualizar(carritoEditando);

        cargarTablaEditar();
        actualizarTotalesEditar();

        JOptionPane.showMessageDialog(editarCarritoView, "Carrito actualizado correctamente.");
    }


    private void buscarCarritoParaEditar() {
        String username = editarCarritoView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(editarCarritoView, "Ingrese el nombre de usuario para buscar.");
            return;
        }

        List<Carrito> carritos = carritoDAO.buscarPorNombreUsuario(username);

        if (carritos.isEmpty()) {
            JOptionPane.showMessageDialog(editarCarritoView, "No se encontró carrito para el usuario: " + username);
            carritoEditando = null;
            limpiarTotalesEditar();
            DefaultTableModel modelo = (DefaultTableModel) editarCarritoView.getTblProductos().getModel();
            modelo.setRowCount(0);
            return;
        }

        carritoEditando = carritos.get(0);
        cargarTablaEditar();
        actualizarTotalesEditar();
    }

    public void setEditarCarritoView(EditarCarritoView editarCarritoView) {
        this.editarCarritoView = editarCarritoView;
        inicializarEditarCarritoView();  // carga productos, cantidades, listeners, etc
    }


    private void inicializarEditarCarritoView() {
        List<Producto> productos = productoDAO.listarTodos();
        DefaultComboBoxModel<String> modeloProductos = new DefaultComboBoxModel<>();
        for (Producto p : productos) {
            modeloProductos.addElement(p.getCodigo() + " - " + p.getNombre());
        }
        editarCarritoView.getTxtProductosLista().setModel(modeloProductos);

        DefaultComboBoxModel<Integer> modeloCantidades = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 20; i++) {
            modeloCantidades.addElement(i);
        }
        editarCarritoView.getCbxCantidad().setModel(modeloCantidades);

        String[] columnas = {"Código", "Producto", "Cantidad", "Precio Unitario", "Subtotal"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        editarCarritoView.getTblListas().setModel(modeloTabla);

        editarCarritoView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEditar());
        editarCarritoView.getBtnGuardar().addActionListener(e -> guardarCambiosEditarCarrito());
    }




    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProducto();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });
    }

    private void guardarCarrito() {
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito creado correctamente");

        Usuario usuario = carrito.getUsuario();
        carrito = new Carrito();
        carrito.setUsuario(usuario);

        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setRowCount(0);
        mostrarTotales();
    }


    private void anadirProducto() {
        String codigo = carritoAnadirView.getTxtCodigo().getText().trim();
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if(producto == null) {
            carritoAnadirView.mostrarMensaje("Producto no encontrado");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        } catch (NumberFormatException e) {
            carritoAnadirView.mostrarMensaje("Cantidad inválida");
            return;
        }

        carrito.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotales();
    }

    private void cargarProductos() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.getProducto().getPrecio(),
                    item.getProducto().getPrecio() * item.getCantidad()
            });

        }
    }

    private void mostrarTotales() {
        carritoAnadirView.getTxtSubtotal().setText(String.valueOf(carrito.calcularSubtotal()));
        carritoAnadirView.getTxtIva().setText(String.valueOf(carrito.calcularIVA()));
        carritoAnadirView.getTxtTotal().setText(String.valueOf(carrito.calcularTotal()));
    }

    public void setCarritoListarView(CarritoListar carritoListarView) {
        this.carritoListarView = carritoListarView;

        carritoListarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Carrito> carritos = carritoDAO.listarTodos();
                DefaultTableModel model = (DefaultTableModel) carritoListarView.getTblListas().getModel();
                model.setRowCount(0); // Limpiar tabla

                for (Carrito carrito : carritos) {
                    StringBuilder productosTexto = new StringBuilder();
                    for (ItemCarrito item : carrito.obtenerItems()) {
                        productosTexto.append(item.getProducto().getNombre())
                                .append(" x").append(item.getCantidad())
                                .append(", ");
                    }

                    if (productosTexto.length() > 0) {
                        productosTexto.setLength(productosTexto.length() - 2);
                    }

                    model.addRow(new Object[]{
                            carrito.getUsuario().getNombre(),
                            productosTexto.toString(),
                            carrito.calcularSubtotal(),
                            carrito.calcularIVA(),
                            carrito.calcularTotal()
                    });
                }

                if (carritos.isEmpty()) {
                    JOptionPane.showMessageDialog(carritoListarView, "No hay carritos registrados.");
                }
            }
        });
    }


    private void listarCarritosPorUsuario(String nombreUsuario) {
        List<Carrito> carritos = carritoDAO.buscarPorNombreUsuario(nombreUsuario);
        DefaultTableModel model = (DefaultTableModel) carritoListarView.getTblListas().getModel();
        model.setRowCount(0);

        for (Carrito carrito : carritos) {
            StringBuilder productosTexto = new StringBuilder();
            for (ItemCarrito item : carrito.obtenerItems()) {
                productosTexto.append(item.getProducto().getNombre())
                        .append(" x").append(item.getCantidad())
                        .append(", ");
            }

            if (productosTexto.length() > 0) {
                productosTexto.setLength(productosTexto.length() - 2);
            }

            model.addRow(new Object[]{
                    carrito.getUsuario().getNombre(),
                    productosTexto.toString(),
                    carrito.calcularSubtotal(),
                    carrito.calcularTotal()
            });
        }

        if (carritos.isEmpty()) {
            JOptionPane.showMessageDialog(carritoListarView, "No hay carritos registrados para el usuario.");
        }
    }


    public void setCarritoEliminarView(EliminarCarrito eliminarView) {
        this.carritoEliminarView = eliminarView;

        carritoEliminarView.getBtnBuscar().addActionListener(e -> {
            String nombreUsuario = carritoEliminarView.getTextEliminar().getText().trim();
            if (nombreUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(carritoEliminarView, "Ingrese el nombre de usuario para buscar.");
                return;
            }

            List<Carrito> carritos = carritoDAO.buscarPorNombreUsuario(nombreUsuario);
            if (carritos.isEmpty()) {
                JOptionPane.showMessageDialog(carritoEliminarView, "No se encontró carrito para el usuario.");
                carritoEliminarView.getListaProductos().removeAllItems();
                return;
            }

            Carrito carrito = carritos.get(0);
            carritoEliminarView.getListaProductos().removeAllItems();

            for (ItemCarrito item : carrito.obtenerItems()) {
                carritoEliminarView.getListaProductos().addItem(item.getProducto().getNombre());
            }
        });

        carritoEliminarView.getBtnEliminar().addActionListener(e -> {
            String productoNombre = (String) carritoEliminarView.getListaProductos().getSelectedItem();
            if (productoNombre == null) {
                JOptionPane.showMessageDialog(carritoEliminarView, "Seleccione un producto");
                return;
            }

            String nombreUsuario = carritoEliminarView.getTextEliminar().getText().trim();
            List<Carrito> carritos = carritoDAO.buscarPorNombreUsuario(nombreUsuario);
            if (carritos.isEmpty()) {
                JOptionPane.showMessageDialog(carritoEliminarView, "Carrito no encontrado para el usuario");
                return;
            }

            Carrito carrito = carritos.get(0);

            ItemCarrito itemEliminar = null;
            for (ItemCarrito item : carrito.obtenerItems()) {
                if (item.getProducto().getNombre().equals(productoNombre)) {
                    itemEliminar = item;
                    break;
                }
            }

            if (itemEliminar != null) {
                carrito.obtenerItems().remove(itemEliminar);
                carritoDAO.actualizar(carrito);
                JOptionPane.showMessageDialog(carritoEliminarView, "Producto eliminado del carrito");

                carritoEliminarView.getListaProductos().removeItem(productoNombre);

                if (carritoListarView != null) {
                    listarCarritosPorUsuario(nombreUsuario);
                }
            }
        });
    }


}
