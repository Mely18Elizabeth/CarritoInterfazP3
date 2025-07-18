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
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de CarritoDAO que persiste los datos en un archivo de texto.
 * Utiliza un estilo de programación tradicional con bucles y estructuras claras.
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {

    private final String ruta;
    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;

    private static final String SEPARADOR_CAMPOS = "|";
    private static final String SEPARADOR_PRODUCTOS = ";";
    private static final String SEPARADOR_PRODUCTO_DETALLE = ",";

    /**
     * Constructor que inyecta las dependencias necesarias.
     * @param usuarioDAO El DAO para buscar usuarios por su username.
     * @param productoDAO El DAO para buscar productos por su código.
     */
    public CarritoDAOArchivoTexto(String ruta, UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        this.ruta = ruta;
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        try {
            new FileWriter(ruta, true).close();
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo de carritos: " + e.getMessage());
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> carritos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Carrito carrito = stringToCarrito(linea);
                if (carrito != null) {
                    carritos.add(carrito);
                }
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return carritos;
    }

    /**
     * Sobrescribe el archivo completo con la lista de carritos proporcionada.
     * Se usa para actualizar y eliminar.
     * @param carritos La lista completa de carritos a guardar.
     */
    private void sobrescribirArchivo(List<Carrito> carritos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, false))) {
            for (Carrito c : carritos) {
                bw.write(carritoToString(c));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convierte un objeto Carrito a una cadena de texto para guardarla en el archivo.
     * @param carrito El objeto Carrito a convertir.
     * @return Una cadena de texto con el formato definido.
     */
    private String carritoToString(Carrito carrito) {
        StringBuilder sb = new StringBuilder();
        sb.append(carrito.getCodigo()).append(SEPARADOR_CAMPOS);
        sb.append(carrito.getUsuario().getUsername()).append(SEPARADOR_CAMPOS);

        List<String> partesDeProductos = new ArrayList<>();
        for (ItemCarrito item : carrito.obtenerItems()) {
            String parte = item.getProducto().getCodigo() + SEPARADOR_PRODUCTO_DETALLE + item.getCantidad();
            partesDeProductos.add(parte);
        }
        sb.append(String.join(SEPARADOR_PRODUCTOS, partesDeProductos));

        return sb.toString();
    }

    /**
     * Convierte una línea de texto del archivo a un objeto Carrito.
     * @param linea La cadena de texto leída del archivo.
     * @return Un objeto Carrito completamente reconstruido.
     */
    private Carrito stringToCarrito(String linea) {
        String[] parts = linea.split("\\" + SEPARADOR_CAMPOS, -1);
        if (parts.length < 2) return null;

        try {
            String username = parts[1];

            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario == null) {
                System.err.println("No se encontró el usuario '" + username + "' al cargar el carrito.");
                return null;
            }

            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);

            if (parts.length > 2 && !parts[2].isEmpty()) {
                String[] productosData = parts[2].split(SEPARADOR_PRODUCTOS);
                for (String productoInfo : productosData) {
                    String[] productoParts = productoInfo.split(SEPARADOR_PRODUCTO_DETALLE);
                    if (productoParts.length == 2) {
                        // ✅ Tratar el código como String
                        String productoCodigo = productoParts[0];
                        int cantidad = Integer.parseInt(productoParts[1]);

                        Producto productoOriginal = productoDAO.buscarPorCodigo(productoCodigo);
                        if (productoOriginal != null) {
                            carrito.agregarProducto(productoOriginal, cantidad);
                        } else {
                            System.err.println("No se encontró el producto con código '" + productoCodigo + "' al cargar el carrito.");
                        }
                    }
                }
            }

            return carrito;
        } catch (NumberFormatException e) {
            System.err.println("Error de formato de número al parsear la línea de carrito: " + linea);
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public void crear(Carrito carrito) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(carritoToString(carrito));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> carritos = listarTodos();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
        sobrescribirArchivo(carritos);
    }

    @Override
    public List<Carrito> buscarPorNombreUsuario(String nombreUsuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : listarTodos()) {
            if (carrito.getUsuario() != null &&
                    carrito.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }

    @Override
    public void eliminarPorNombreUsuario(String nombreUsuario) {
        List<Carrito> carritos = listarTodos();
        carritos.removeIf(carrito -> carrito.getUsuario() != null &&
                carrito.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario));
        sobrescribirArchivo(carritos);
    }
}