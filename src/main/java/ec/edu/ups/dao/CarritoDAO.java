package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

public interface CarritoDAO {

    void crear(Carrito carrito);

    Carrito buscarPorCodigo(int codigo);

    void actualizar(Carrito carrito);

    List<Carrito> listarTodos();

    List<Carrito> listarPorUsuario(Usuario usuario);
    List<Carrito> buscarPorNombreUsuario(String nombreUsuario);
    void eliminarPorNombreUsuario(String nombreUsuario);




}