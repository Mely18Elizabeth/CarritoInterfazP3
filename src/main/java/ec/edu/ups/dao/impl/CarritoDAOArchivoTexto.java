package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoDAOArchivoTexto implements CarritoDAO {

    private final File archivo;
    private final List<Carrito> carritos;

    public CarritoDAOArchivoTexto(String ruta) {
        this.archivo = new File(ruta, "carritos.txt");
        this.carritos = new ArrayList<>();
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creando archivo de texto de carritos: " + e.getMessage());
            }
        } else {
            cargarDesdeArchivo();
        }
    }

    private void cargarDesdeArchivo() {
        if (!archivo.exists()) return;
        carritos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Carrito carrito = null;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Carrito:")) {
                    String[] datos = linea.split(";");
                    int codigo = Integer.parseInt(datos[1]);
                    String username = datos[2];
                    String nombre = datos[3];
                    String apellido = datos[4];
                    carrito = new Carrito();
                    carrito.setCodigo(codigo);

                    Usuario usuario = new Usuario();
                    usuario.setUsername(username);
                    usuario.setNombre(nombre);
                    usuario.setApellido(apellido);

                    carrito.setUsuario(usuario);
                    carrito.obtenerItems().clear();
                    carritos.add(carrito);
                } else if (linea.startsWith("Item:") && carrito != null) {
                    String[] datos = linea.split(";");
                    String codigoProd = datos[1];
                    String nombreProd = datos[2];
                    double precioProd = Double.parseDouble(datos[3]);
                    int cantidad = Integer.parseInt(datos[4]);
                    Producto producto = new Producto(codigoProd, nombreProd, precioProd);
                    carrito.agregarProducto(producto, cantidad);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de texto de carritos: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Carrito carrito : carritos) {
                Usuario u = carrito.getUsuario();
                pw.println("Carrito;" + carrito.getCodigo() + ";" + u.getUsername() + ";" + u.getNombre() + ";" + u.getApellido());
                for (ItemCarrito item : carrito.obtenerItems()) {
                    Producto p = item.getProducto();
                    pw.println("Item;" + p.getCodigo() + ";" + p.getNombre() + ";" + p.getPrecio() + ";" + item.getCantidad());
                }
            }
        } catch (IOException e) {
            System.err.println("Error guardando archivo de texto de carritos: " + e.getMessage());
        }
    }

    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        guardarEnArchivo();
    }

    @Override
    public void actualizar(Carrito carritoActualizado) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carritoActualizado.getCodigo()) {
                carritos.set(i, carritoActualizado);
                guardarEnArchivo();
                return;
            }
        }
        carritos.add(carritoActualizado);
        guardarEnArchivo();
    }



    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    @Override
    public List<Carrito> buscarPorNombreUsuario(String nombreUsuario) {
        return carritos.stream()
                .filter(c -> c.getUsuario() != null &&
                        c.getUsuario().getUsername().equalsIgnoreCase(nombreUsuario))
                .collect(Collectors.toList());
    }


    @Override
    public void eliminarPorNombreUsuario(String nombreUsuario) {
        carritos.removeIf(c -> c.getUsuario() != null &&
                c.getUsuario().getUsername().equalsIgnoreCase(nombreUsuario));
        guardarEnArchivo();
    }
}
