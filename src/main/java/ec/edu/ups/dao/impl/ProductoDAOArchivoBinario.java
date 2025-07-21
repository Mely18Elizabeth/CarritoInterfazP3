package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoBinario implements ProductoDAO {

    private final File archivo;

    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo, "productos.dat");
    }

    private List<Producto> cargarDesdeArchivo() {
        if (!archivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Producto>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarEnArchivo(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            throw new RuntimeException("Error guardando productos: " + e.getMessage());
        }
    }

    @Override
    public void crear(Producto producto) {
        List<Producto> productos = cargarDesdeArchivo();
        productos.add(producto);
        guardarEnArchivo(productos);
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        return cargarDesdeArchivo().stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminar(String codigo) {
        List<Producto> productos = cargarDesdeArchivo();
        productos.removeIf(p -> p.getCodigo().equals(codigo));
        guardarEnArchivo(productos);
    }

    @Override
    public void eliminar(Producto producto) {
        eliminar(producto.getCodigo());
    }

    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = cargarDesdeArchivo();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(producto.getCodigo())) {
                productos.set(i, producto);
                break;
            }
        }
        guardarEnArchivo(productos);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : cargarDesdeArchivo()) {
            if (p.getNombre().startsWith(nombre)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    @Override
    public List<Producto> listarTodos() {
        return cargarDesdeArchivo();
    }
}
