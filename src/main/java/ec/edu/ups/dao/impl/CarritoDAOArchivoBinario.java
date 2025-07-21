package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final File archivo;
    private final List<Carrito> carritos;

    public CarritoDAOArchivoBinario(String ruta) {
        this.archivo = new File(ruta, "carritos.dat");
        this.carritos = new ArrayList<>();
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creando archivo binario de carritos: " + e.getMessage());
            }
        } else {
            cargarDesdeArchivo();
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        if (!archivo.exists() || archivo.length() == 0) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                carritos.clear();
                carritos.addAll((List<Carrito>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error leyendo archivo binario de carritos: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.err.println("Error guardando archivo binario de carritos: " + e.getMessage());
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
                guardarEnArchivo(); // o método equivalente
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
