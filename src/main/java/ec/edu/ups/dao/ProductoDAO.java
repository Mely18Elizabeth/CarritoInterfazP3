package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

public interface ProductoDAO {

    void crear(Producto producto);

    Producto buscarPorCodigo(String codigo);  // CAMBIO a String

    List<Producto> buscarPorNombre(String nombre);

    void actualizar(Producto producto);

    void eliminar(String codigo);  // CAMBIO a String
    void eliminar(Producto producto); // ✅ Añadir esta línea


    List<Producto> listarTodos();
}
