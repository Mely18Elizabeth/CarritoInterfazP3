package ec.edu.ups.dao;

public enum TipoPersistencia {
        DAOMEMORIA("Memoria"),
        ARCHIVOTEXTO("Archivo de Texto"),
        ARCHIVOBINARIO("Archivo Binario");

        private final String nombreAmigable;

    TipoPersistencia(String nombreAmigable) {
        this.nombreAmigable = nombreAmigable;
    }

    @Override
    public String toString() {
        return nombreAmigable;
    }
}
