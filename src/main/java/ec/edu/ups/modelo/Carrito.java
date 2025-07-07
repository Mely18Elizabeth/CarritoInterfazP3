package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {

    private final double IVA = 0.12;
    private Usuario usuario;
    private  int contador = 1;
    private int codigo;

    private GregorianCalendar fechaCreacion;

    private List<ItemCarrito> items;

    public Carrito() {
        codigo = contador++;
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void eliminarProducto(String codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo().equals(codigoProducto)) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo().equals(producto.getCodigo())) {
                // Si ya existe, sumamos la cantidad
                item.setCantidad(item.getCantidad() + cantidad);
                return; // Salimos, no agregamos uno nuevo
            }
        }
        // Si no existe, lo añadimos nuevo
        items.add(new ItemCarrito(producto, cantidad));
    }


    @Override
    public String toString() {
        return "Carrito{" +
                "IVA=" + IVA +
                ", codigo=" + codigo +
                ", fechaCreacion=" + fechaCreacion +
                ", items=" + items +
                '}';
    }
}
