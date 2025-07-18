package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private final List<Carrito> carritos;
    private int proximoCodigo = 1;

    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<>();
    }

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(proximoCodigo++);
        carritos.add(carrito);
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                return;
            }
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    @Override
    public void eliminarPorNombreUsuario(String nombreUsuario) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)) {
                iterator.remove();
            }
        }
    }

    public List<Carrito> buscarPorNombreUsuario(String nombreUsuario) {
        List<Carrito> resultados = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)) {
                resultados.add(c);
            }
        }
        return resultados;
    }

}
