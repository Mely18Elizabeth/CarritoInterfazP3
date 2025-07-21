package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoTexto implements ProductoDAO {

    private final File archivo;

    public ProductoDAOArchivoTexto(String rutaArchivo) {
        this.archivo = new File(rutaArchivo, "productos.txt");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear el archivo de texto: " + e.getMessage());
            }
        }
    }

    @Override
    public void crear(Producto producto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getPrecio());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el producto: " + e.getMessage());
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
    public void eliminar(String codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo().equals(codigo));
        guardarLista(productos);
    }

    @Override
    public void eliminar(Producto producto) {
        eliminar(producto.getCodigo());
    }

    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = listarTodos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(producto.getCodigo())) {
                productos.set(i, producto);
                break;
            }
        }
        guardarLista(productos);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : listarTodos()) {
            if (p.getNombre().startsWith(nombre)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String codigo = partes[0].trim();
                    String nombre = partes[1].trim();
                    double precio = Double.parseDouble(partes[2].trim());
                    productos.add(new Producto(codigo, nombre, precio));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer archivo de productos: " + e.getMessage());
        }
        return productos;
    }

    private void guardarLista(List<Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Producto p : productos) {
                writer.write(p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir productos: " + e.getMessage());
        }
    }
}
