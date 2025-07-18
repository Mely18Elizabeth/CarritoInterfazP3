package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    public ProductoDAOMemoria() {
        productos = new ArrayList<>();
    }

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (Producto p : productos) {
            if (codigo.equals(p.getCodigo())) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String codigo) {
        if (codigo == null) return;
        productos.removeIf(p -> codigo.equals(p.getCodigo()));
    }

    @Override
    public void actualizar(Producto producto) {
        if (producto == null || producto.getCodigo() == null) return;
        for (int i = 0; i < productos.size(); i++) {
            if (producto.getCodigo().equals(productos.get(i).getCodigo())) {
                productos.set(i, producto);
                break;
            }
        }
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        if (nombre == null) return productosEncontrados;
        for (Producto producto : productos) {
            if (producto.getNombre() != null && producto.getNombre().startsWith(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }

    @Override
    public void eliminar(Producto producto) {
        productos.remove(producto);
    }
}
