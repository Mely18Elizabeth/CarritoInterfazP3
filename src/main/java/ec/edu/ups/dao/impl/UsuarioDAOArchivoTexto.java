package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Respuestas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {

    private final String rutaArchivo;
    private static final String SEPARADOR_CAMPOS = "|";
    private static final String SEPARADOR_RESPUESTAS = "#"; // Para separar los campos de Respuestas

    public UsuarioDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        try {
            new FileWriter(rutaArchivo, true).close(); // Asegura que el archivo exista
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo de usuarios: " + e.getMessage());
        }
    }

    private String usuarioToString(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append(usuario.getUsername()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getContrasenia()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getNombre()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getApellido()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getRol().name()); // Guarda el nombre del enum

        if (usuario.getRespuestas() != null) {
            Respuestas r = usuario.getRespuestas();
            sb.append(SEPARADOR_CAMPOS).append(r.getNombreAmigo()).append(SEPARADOR_RESPUESTAS);
            sb.append(r.getPasatiempo()).append(SEPARADOR_RESPUESTAS);
            sb.append(r.getCiudadNacimiento()).append(SEPARADOR_RESPUESTAS);
            sb.append(r.getCancionFav()).append(SEPARADOR_RESPUESTAS);
            sb.append(r.getColorFav()).append(SEPARADOR_RESPUESTAS);
            sb.append(r.getDia()).append(SEPARADOR_RESPUESTAS);
            sb.append(r.getMes()).append(SEPARADOR_RESPUESTAS);
            sb.append(r.getAnio());
        }
        return sb.toString();
    }

    private Usuario stringToUsuario(String linea) {
        String[] parts = linea.split("\\" + SEPARADOR_CAMPOS);
        if (parts.length < 5) return null; // Mínimo 5 campos para usuario básico

        try {
            String username = parts[0];
            String contrasenia = parts[1];
            String nombre = parts[2];
            String apellido = parts[3];
            Rol rol = Rol.valueOf(parts[4]); // Convierte el String de vuelta a enum

            Usuario usuario = new Usuario(username, contrasenia, nombre, apellido, rol);

            if (parts.length > 5) { // Si hay respuestas
                String[] respuestasParts = parts[5].split("\\" + SEPARADOR_RESPUESTAS);
                if (respuestasParts.length == 8) {
                    Respuestas respuestas = new Respuestas(
                            respuestasParts[0],
                            respuestasParts[1],
                            respuestasParts[2],
                            respuestasParts[3],
                            respuestasParts[4],
                            Integer.parseInt(respuestasParts[5]), // Asegúrate de que este valor sea un número válido
                            Integer.parseInt(respuestasParts[6]), // Asegúrate de que este valor sea un número válido
                            Integer.parseInt(respuestasParts[7])  // Asegúrate de que este valor sea un número válido
                    );
                    usuario.setRespuestas(respuestas);
                }
            }
            return usuario;
        } catch (IllegalArgumentException e) {
            System.err.println("Error al convertir el rol o al parsear línea de usuario: " + linea + " - " + e.getMessage());
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: línea de usuario incompleta: " + linea + " - " + e.getMessage());
            return null;
        }
    }


    private List<Usuario> leerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario usuario = stringToUsuario(linea);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe, se creará al intentar escribir
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private void sobrescribirArchivo(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (Usuario u : usuarios) {
                bw.write(usuarioToString(u));
                bw.newLine();
            }
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
        // Evitar duplicados por username
        if (usuarios.stream().noneMatch(u -> u.getUsername().equals(usuario.getUsername()))) {
            usuarios.add(usuario);
            sobrescribirArchivo(usuarios);
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
            sobrescribirArchivo(usuarios);
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = leerTodosLosUsuarios();
        Optional<Usuario> existingUser  = usuarios.stream()
                .filter(u -> u.getUsername().equals(usuario.getUsername()))
                .findFirst();

        if (existingUser .isPresent()) {
            int index = usuarios.indexOf(existingUser .get());
            usuarios.set(index, usuario);
            sobrescribirArchivo(usuarios);
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
