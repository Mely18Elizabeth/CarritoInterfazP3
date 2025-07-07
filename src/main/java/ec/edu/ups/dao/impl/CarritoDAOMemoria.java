package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> carritos;



    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<Carrito>();
    }

    public List<Carrito> buscarPorNombreUsuario(String nombreUsuario) {
        List<Carrito> resultados = new ArrayList<>();
        for (Carrito c : carritos) { // suponiendo lista carritos
            if (c.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)) {
                resultados.add(c);
            }
        }
        return resultados;
    }


    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getUsuario().getNombre().equalsIgnoreCase(carrito.getUsuario().getNombre())) {
                carritos.set(i, carrito);  // Reemplaza el carrito completo
                break;
            }
        }
    }

    public void eliminarPorNombreUsuario(String nombreUsuario) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getUsuario().getNombre().equalsIgnoreCase(nombreUsuario)) {
                iterator.remove();
                break; // si solo quieres eliminar el primero
            }
        }
    }


    @Override
    public List<Carrito> listarTodos() {
        return carritos;
    }

    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getUsuario().equals(usuario)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

}