package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAOArchivoBinario implements ProductoDAO {

    private final String rutaArchivo;

    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        try {
            new File(rutaArchivo).createNewFile();
        } catch (IOException e) {
            System.err.println("Error al crear el archivo binario de productos: " + e.getMessage());
        }
    }

    private List<Producto> leerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            productos = (List<Producto>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Archivo no existe
        } catch (EOFException e) {
            // Archivo vacío
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private void escribirTodosLosProductos(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Producto producto) {
        List<Producto> productos = leerTodosLosProductos();
        if (productos.stream().noneMatch(p -> p.getCodigo().equals(producto.getCodigo()))) {
            productos.add(producto);
            escribirTodosLosProductos(productos);
        } else {
            System.out.println("Producto con código " + producto.getCodigo() + " ya existe.");
        }
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        return listarTodos().stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto p : listarTodos()) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = leerTodosLosProductos();
        Optional<Producto> existingProduct = productos.stream()
                .filter(p -> p.getCodigo().equals(producto.getCodigo()))
                .findFirst();

        if (existingProduct.isPresent()) {
            int index = productos.indexOf(existingProduct.get());
            productos.set(index, producto);
            escribirTodosLosProductos(productos);
        } else {
            System.out.println("Producto a actualizar no encontrado: " + producto.getCodigo());
        }
    }

    @Override
    public void eliminar(String codigo) {
        List<Producto> productos = leerTodosLosProductos();
        boolean removed = productos.removeIf(p -> p.getCodigo().equals(codigo));
        if (removed) {
            escribirTodosLosProductos(productos);
        }
    }

    @Override
    public void eliminar(Producto producto) {
        eliminar(producto.getCodigo());
    }

    @Override
    public List<Producto> listarTodos() {
        return leerTodosLosProductos();
    }
}
