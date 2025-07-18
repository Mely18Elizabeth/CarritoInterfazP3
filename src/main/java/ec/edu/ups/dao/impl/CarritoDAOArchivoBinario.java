package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final String rutaArchivo;
    private final UsuarioDAO usuarioDAO; // Necesario para reconstruir el usuario
    private final ProductoDAO productoDAO; // Necesario para reconstruir los productos

    public CarritoDAOArchivoBinario(String rutaArchivo, UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        this.rutaArchivo = rutaArchivo;
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        try {
            new File(rutaArchivo).createNewFile();
        } catch (IOException e) {
            System.err.println("Error al crear el archivo binario de carritos: " + e.getMessage());
        }
    }

    private List<Carrito> leerTodosLosCarritos() {
        List<Carrito> carritos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            carritos = (List<Carrito>) ois.readObject();
            // Post-procesamiento para re-asociar objetos si no se serializan completamente
            // Esto es crucial si Usuario y Producto no se serializan en Carrito
            for (Carrito carrito : carritos) {
                // Re-asociar usuario
                if (carrito.getUsuario() != null) {
                    Usuario usuarioReal = usuarioDAO.buscarPorUsername(carrito.getUsuario().getUsername());
                    carrito.setUsuario(usuarioReal);
                }
                // Re-asociar productos en items
                for (ItemCarrito item : carrito.obtenerItems()) {
                    if (item.getProducto() != null) {
                        Producto productoReal = productoDAO.buscarPorCodigo(item.getProducto().getCodigo());
                        item.setProducto(productoReal);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe
        } catch (EOFException e) {
            // Archivo vacío
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return carritos;
    }

    private void escribirTodosLosCarritos(List<Carrito> carritos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Carrito carrito) {
        List<Carrito> carritos = leerTodosLosCarritos();
        // Asignar un código si es necesario (si no lo hace el modelo)
        if (carrito.getCodigo() == 0) { // Asumiendo 0 significa no asignado
            int maxCodigo = carritos.stream().mapToInt(Carrito::getCodigo).max().orElse(0);
            carrito.setCodigo(maxCodigo + 1);
        }
        carritos.add(carrito);
        escribirTodosLosCarritos(carritos);
    }

    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> carritos = leerTodosLosCarritos();
        Optional<Carrito> existingCarrito = carritos.stream()
                .filter(c -> c.getCodigo() == carrito.getCodigo())
                .findFirst();

        if (existingCarrito.isPresent()) {
            int index = carritos.indexOf(existingCarrito.get());
            carritos.set(index, carrito);
            escribirTodosLosCarritos(carritos);
        } else {
            System.out.println("Carrito a actualizar no encontrado: " + carrito.getCodigo());
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        return leerTodosLosCarritos();
    }

    @Override
    public List<Carrito> buscarPorNombreUsuario(String nombreUsuario) {
        List<Carrito> resultados = new ArrayList<>();
        for (Carrito c : listarTodos()) {
            if (c.getUsuario() != null && c.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)) {
                resultados.add(c);
            }
        }
        return resultados;
    }

    @Override
    public void eliminarPorNombreUsuario(String nombreUsuario) {
        List<Carrito> carritos = leerTodosLosCarritos();
        boolean removed = carritos.removeIf(c -> c.getUsuario() != null && c.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario));
        if (removed) {
            escribirTodosLosCarritos(carritos);
        }
    }
}
