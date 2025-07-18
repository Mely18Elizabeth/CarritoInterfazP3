package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAOArchivoTexto implements ProductoDAO {

    private final String rutaArchivo;
    private static final String SEPARADOR_CAMPOS = "|";

    public ProductoDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        try {
            new FileWriter(rutaArchivo, true).close();
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo de productos: " + e.getMessage());
        }
    }

    private String productoToString(Producto producto) {
        return producto.getCodigo() + SEPARADOR_CAMPOS +
                producto.getNombre() + SEPARADOR_CAMPOS +
                producto.getPrecio();
    }

    private Producto stringToProducto(String linea) {
        String[] parts = linea.split("\\" + SEPARADOR_CAMPOS);
        if (parts.length != 3) return null;

        try {
            String codigo = parts[0];
            String nombre = parts[1];
            double precio = Double.parseDouble(parts[2]);
            return new Producto(codigo, nombre, precio);
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear línea de producto: " + linea);
            return null;
        }
    }

    private List<Producto> leerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto producto = stringToProducto(linea);
                if (producto != null) {
                    productos.add(producto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private void sobrescribirArchivo(List<Producto> productos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (Producto p : productos) {
                bw.write(productoToString(p));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Producto producto) {
        List<Producto> productos = leerTodosLosProductos();
        if (productos.stream().noneMatch(p -> p.getCodigo().equals(producto.getCodigo()))) {
            productos.add(producto);
            sobrescribirArchivo(productos);
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
        Optional<Producto> existing = productos.stream()
                .filter(p -> p.getCodigo().equals(producto.getCodigo()))
                .findFirst();

        if (existing.isPresent()) {
            int index = productos.indexOf(existing.get());
            productos.set(index, producto);
            sobrescribirArchivo(productos);
        }
    }

    @Override
    public void eliminar(String codigo) {
        List<Producto> productos = leerTodosLosProductos();
        productos.removeIf(p -> p.getCodigo().equals(codigo));
        sobrescribirArchivo(productos);
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
