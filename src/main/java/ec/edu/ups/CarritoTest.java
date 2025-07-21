package ec.edu.ups;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.servicio.CarritoService;
import ec.edu.ups.servicio.CarritoServiceImpl;

public class CarritoTest {

    public static void main(String[] args) {

        CarritoService carrito = new CarritoServiceImpl();

        Producto p1 = new Producto("123", "Mouse", 15.0);
        Producto p2 = new Producto("22", "Teclado", 25.0);

        carrito.agregarProducto(p1, 2);
        carrito.agregarProducto(p2, 1);

        System.out.println("Contenido del carrito:");
        for (ItemCarrito item : carrito.obtenerItems()) {
            System.out.println("- " + item);
        }

        double total = carrito.calcularTotal();
        System.out.println("Total: $" + total);

        System.out.println("¿Carrito vacío? " + carrito.estaVacio());

        carrito.eliminarProducto(1);
        carrito.vaciarCarrito();

        System.out.println("Carrito vaciado. ¿Vacío ahora? " + carrito.estaVacio());
    }
}