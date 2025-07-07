package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.CarritoAnadirView;
import ec.edu.ups.vista.CarritoListar;

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


    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             Usuario usuarioActual) {  // <-- Agregado usuarioActual

        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carrito = new Carrito();
        this.carrito.setUsuario(usuarioActual);  // Asigna usuario al carrito
        configurarEventosEnVistas();
    }


    public void setCarritoListarView(CarritoListar carritoListarView) {
        this.carritoListarView = carritoListarView;

        // Configurar acción botón Listar todos
        carritoListarView.getBtnListar().addActionListener(e -> {
            List<Carrito> carritos = carritoDAO.listarTodos();
            DefaultTableModel model = (DefaultTableModel) carritoListarView.getTblListas().getModel();
            model.setRowCount(0);  // limpiar tabla

            for (Carrito c : carritos) {
                for (ItemCarrito item : c.obtenerItems()) {
                    model.addRow(new Object[]{
                            c.getCodigo(),
                            item.getProducto().getCodigo(),
                            item.getProducto().getNombre(),
                            item.getProducto().getPrecio(),
                            item.getCantidad(),
                            item.getProducto().getPrecio() * item.getCantidad()
                    });
                }
            }
        });
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
        System.out.println(carritoDAO.listarTodos());
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
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad()
            });
        }
    }

    private void mostrarTotales() {
        carritoAnadirView.getTxtSubtotal().setText(String.valueOf(carrito.calcularSubtotal()));
        carritoAnadirView.getTxtIva().setText(String.valueOf(carrito.calcularIVA()));
        carritoAnadirView.getTxtTotal().setText(String.valueOf(carrito.calcularTotal()));
    }
}
