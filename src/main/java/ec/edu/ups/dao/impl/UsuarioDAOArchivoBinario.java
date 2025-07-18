package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOArchivoBinario implements UsuarioDAO {

    private final String rutaArchivo;

    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        // Asegura que el archivo exista, no es estrictamente necesario para ObjectOutputStream
        // pero puede ayudar a evitar FileNotFoundException en la primera lectura.
        try {
            new File(rutaArchivo).createNewFile();
        } catch (IOException e) {
            System.err.println("Error al crear el archivo binario de usuarios: " + e.getMessage());
        }
    }

    private List<Usuario> leerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            // Leer el objeto List<Usuario> completo
            usuarios = (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Archivo no existe, se devolverá una lista vacía
        } catch (EOFException e) {
            // Fin de archivo inesperado, significa que el archivo está vacío o corrupto
            // Se devuelve una lista vacía
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private void escribirTodosLosUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return listarTodos().stream()
                .filter(u -> u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void crear(Usuario usuario) {
        List<Usuario> usuarios = leerTodosLosUsuarios();
        if (usuarios.stream().noneMatch(u -> u.getUsername().equals(usuario.getUsername()))) {
            usuarios.add(usuario);
            escribirTodosLosUsuarios(usuarios);
        } else {
            System.out.println("Usuario con username " + usuario.getUsername() + " ya existe.");
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return listarTodos().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = leerTodosLosUsuarios();
        boolean removed = usuarios.removeIf(u -> u.getUsername().equals(username));
        if (removed) {
            escribirTodosLosUsuarios(usuarios);
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = leerTodosLosUsuarios();
        Optional<Usuario> existingUser = usuarios.stream()
                .filter(u -> u.getUsername().equals(usuario.getUsername()))
                .findFirst();

        if (existingUser.isPresent()) {
            int index = usuarios.indexOf(existingUser.get());
            usuarios.set(index, usuario);
            escribirTodosLosUsuarios(usuarios);
        } else {
            System.out.println("Usuario a actualizar no encontrado: " + usuario.getUsername());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return leerTodosLosUsuarios();
    }

    @Override
    public Usuario buscarPorNombreApellido(String nombre, String apellido) {
        return listarTodos().stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombre) && u.getApellido().equalsIgnoreCase(apellido))
                .findFirst()
                .orElse(null);
    }
}
