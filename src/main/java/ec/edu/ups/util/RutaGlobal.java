package ec.edu.ups.util;

public class RutaGlobal {
    private static String rutaBase;

    public static void setRutaBase(String ruta) {
        RutaGlobal.rutaBase = ruta;
    }

    public static String getRutaBase() {
        return rutaBase;
    }
}
