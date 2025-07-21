package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ModificarProductoView modificarProductoView;
    private final EliminarProductoView eliminarProductoView;
    private final ProductoListaView productoListaView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;


    public ProductoController(
            ProductoDAO productoDAO,
            ProductoAnadirView productoAnadirView,
            ProductoListaView productoListaView,
            CarritoAnadirView carritoAnadirView,
            ModificarProductoView modificarProductoView,
            EliminarProductoView eliminarProductoView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.carritoAnadirView = carritoAnadirView;
        this.modificarProductoView = modificarProductoView;
        this.eliminarProductoView = eliminarProductoView;

        this.configurarEventosEnVistas();
    }


    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        modificarProductoView.getBtnBuscar().addActionListener(e -> {
            String codigo = modificarProductoView.getTxtBuscar().getText();
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                modificarProductoView.getTxtNombre().setText(producto.getNombre());
                modificarProductoView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                JOptionPane.showMessageDialog(modificarProductoView, "Producto no encontrado");
            }
        });

        modificarProductoView.getBtnActualizar().addActionListener(e -> {
            String codigo = modificarProductoView.getTxtBuscar().getText();
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                producto.setNombre(modificarProductoView.getTxtNombre().getText());
                double precio = Double.parseDouble(modificarProductoView.getTxtPrecio().getText());
                producto.setPrecio(precio);
                productoDAO.actualizar(producto);

                JOptionPane.showMessageDialog(modificarProductoView, "Producto actualizado");
                productoListaView.cargarDatos(productoDAO.listarTodos());
            } else {
                JOptionPane.showMessageDialog(modificarProductoView, "Producto no encontrado para actualizar");
            }
        });

        eliminarProductoView.getBtnEliminar().addActionListener(e -> {
            int filaSeleccionada = eliminarProductoView.getTblProductos().getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(eliminarProductoView, "Seleccione un producto para eliminar.");
                return;
            }

            String codigo = eliminarProductoView.getTblProductos().getValueAt(filaSeleccionada, 0).toString();

            int confirmacion = JOptionPane.showConfirmDialog(
                    eliminarProductoView,
                    "¿Está seguro de que desea eliminar el producto con código: " + codigo + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoDAO.eliminar(producto);
                    JOptionPane.showMessageDialog(eliminarProductoView, "Producto eliminado correctamente.");

                    eliminarProductoView.cargarProductosEnTabla(productoDAO.listarTodos());

                    productoListaView.cargarDatos(productoDAO.listarTodos());
                } else {
                    JOptionPane.showMessageDialog(eliminarProductoView, "El producto ya no existe.");
                }
            }
        });

        eliminarProductoView.getBtnBuscar().addActionListener(e -> {
            String texto = eliminarProductoView.getTextEliminar().getText().trim();

            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(eliminarProductoView, "Ingrese un código o nombre para buscar.");
                return;
            }

            Producto producto = productoDAO.buscarPorCodigo(texto);
            if (producto != null) {
                eliminarProductoView.cargarProductosEnTabla(List.of(producto));
            } else {
                List<Producto> productos = productoDAO.buscarPorNombre(texto);
                if (productos.isEmpty()) {
                    JOptionPane.showMessageDialog(eliminarProductoView, "No se encontró ningún producto.");
                    eliminarProductoView.cargarProductosEnTabla(List.of());
                } else {
                    eliminarProductoView.cargarProductosEnTabla(productos);
                }
            }
        });



        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigoONombre();
            }
        });

        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProductoAlCarrito();
            }
        });

    }

    private void anadirProductoAlCarrito() {
        try {
            String codigo = carritoAnadirView.getTxtCodigo().getText();
            String nombre = carritoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(carritoAnadirView.getTxtPrecio().getText());
            int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());

            double subtotalProducto = precio * cantidad;

            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
            Object[] fila = {codigo, nombre, precio, cantidad, subtotalProducto};
            modelo.addRow(fila);

            actualizarTotales();

        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje("Ingrese valores válidos");
        }
    }


    private void actualizarTotales() {
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        double subtotal = 0.0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            subtotal += (double) modelo.getValueAt(i, 4);
        }

        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", subtotal));
        carritoAnadirView.getTxtIva().setText(String.format("%.2f", iva));
        carritoAnadirView.getTxtTotal().setText(String.format("%.2f", total));
    }


    private void guardarProducto() {
        String codigo = productoAnadirView.getTxtCodigo().getText();
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
    }


    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoPorCodigoONombre() {
        String textoBusqueda = carritoAnadirView.getTxtCodigo().getText().trim();

        Producto producto = productoDAO.buscarPorCodigo(textoBusqueda);

        if (producto == null) {
            List<Producto> productos = productoDAO.buscarPorNombre(textoBusqueda);
            if (productos.isEmpty()) {
                carritoAnadirView.mostrarMensaje("No se encontró el producto");
                carritoAnadirView.getTxtNombre().setText("");
                carritoAnadirView.getTxtPrecio().setText("");
            } else {
                producto = productos.get(0);
                carritoAnadirView.getTxtCodigo().setText(producto.getCodigo());
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            }
        } else {
            carritoAnadirView.getTxtCodigo().setText(producto.getCodigo());
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }
    }


}