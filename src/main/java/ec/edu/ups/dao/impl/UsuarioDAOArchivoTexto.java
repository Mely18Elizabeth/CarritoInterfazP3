package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {

    private final File archivo;

    public UsuarioDAOArchivoTexto(String ruta) {
        this.archivo = new File(ruta, "usuarios.txt");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creando archivo de usuarios: " + e.getMessage());
            }
        }
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }


    @Override
    public void crear(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            escribirUsuario(bw, usuario);
        } catch (IOException e) {
            System.err.println("Error escribiendo usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario usuarioActualizado) {
        List<Usuario> usuarios = listarTodos();
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            usuarios.add(usuarioActualizado);
        }
        guardarTodos(usuarios);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarPorNombreApellido(String nombre, String apellido) {
        for (Usuario u : listarTodos()) {
            if (u.getNombre() != null && u.getApellido() != null &&
                    u.getNombre().equalsIgnoreCase(nombre) && u.getApellido().equalsIgnoreCase(apellido)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarTodos(usuarios);
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Usuario usuario = null;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Username:")) {
                    usuario = new Usuario();
                    usuario.setUsername(linea.split(":")[1].trim());
                } else if (linea.startsWith("Contrasenia:")) {
                    if (usuario != null) {
                        usuario.setContrasenia(linea.split(":")[1].trim());
                    }
                } else if (linea.startsWith("Nombre:")) {
                    if (usuario != null) {
                        usuario.setNombre(linea.split(":")[1].trim());
                    }
                } else if (linea.startsWith("Apellido:")) {
                    if (usuario != null) {
                        usuario.setApellido(linea.split(":")[1].trim());
                    }
                } else if (linea.startsWith("Rol:")) {
                    if (usuario != null) {
                        String rolStr = linea.split(":")[1].trim();
                        usuario.setRol(Rol.valueOf(rolStr));
                    }
                } else if (linea.startsWith("---")) {
                    if (usuario != null) {
                        usuarios.add(usuario);
                        usuario = null;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    // Métodos privados auxiliares

    private void escribirUsuario(BufferedWriter bw, Usuario usuario) throws IOException {
        bw.write("Username: " + usuario.getUsername());
        bw.newLine();
        bw.write("Contrasenia: " + usuario.getContrasenia());
        bw.newLine();
        bw.write("Nombre: " + usuario.getNombre());
        bw.newLine();
        bw.write("Apellido: " + usuario.getApellido());
        bw.newLine();
        bw.write("Rol: " + usuario.getRol());
        bw.newLine();
        bw.write("---");
        bw.newLine();
    }

    private void guardarTodos(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Usuario u : usuarios) {
                escribirUsuario(bw, u);
            }
        } catch (IOException e) {
            System.err.println("Error guardando usuarios: " + e.getMessage());
        }
    }
}
