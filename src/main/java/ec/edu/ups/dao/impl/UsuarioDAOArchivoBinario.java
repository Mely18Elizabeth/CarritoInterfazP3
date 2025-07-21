package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivoBinario implements UsuarioDAO {

    private final File archivo;
    private final List<Usuario> usuarios;

    public UsuarioDAOArchivoBinario(String ruta) {
        this.archivo = new File(ruta, "usuarios.dat");
        this.usuarios = new ArrayList<>();
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creando archivo binario de usuarios: " + e.getMessage());
            }
        } else {
            cargarDesdeArchivo(); // <-- importante
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        if (!archivo.exists() || archivo.length() == 0) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                usuarios.clear();
                usuarios.addAll((List<Usuario>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error leyendo archivo binario de usuarios: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error guardando archivo binario de usuarios: " + e.getMessage());
        }
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        guardarEnArchivo();
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                guardarEnArchivo();
                return;
            }
        }
        usuarios.add(usuarioActualizado);
        guardarEnArchivo();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario buscarPorNombreApellido(String nombre, String apellido) {
        return usuarios.stream()
                .filter(u -> u.getNombre() != null && u.getApellido() != null
                        && u.getNombre().equalsIgnoreCase(nombre)
                        && u.getApellido().equalsIgnoreCase(apellido))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarEnArchivo();
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
}
