package ec.edu.ups.modelo;

import java.util.GregorianCalendar;

public class Respuestas {
    private String NombreAmigo, pasatiempo;
    private String ciudadNacimiento;
    private String cancionFav, colorFav;
    private int dia, mes, anio;

    public Respuestas(String nombreAmigo, String pasatiempo, String ciudadNacimiento, String cancionFav, String colorFav, int dia, int mes, int anio) {
        NombreAmigo = nombreAmigo;
        this.pasatiempo = pasatiempo;
        this.ciudadNacimiento = ciudadNacimiento;
        this.cancionFav = cancionFav;
        this.colorFav = colorFav;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public String getNombreAmigo() {
        return NombreAmigo;
    }

    public void setNombreAmigo(String nombreAmigo) {
        NombreAmigo = nombreAmigo;
    }

    public String getPasatiempo() {
        return pasatiempo;
    }

    public void setPasatiempo(String pasatiempo) {
        this.pasatiempo = pasatiempo;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public String getCancionFav() {
        return cancionFav;
    }

    public void setCancionFav(String cancionFav) {
        this.cancionFav = cancionFav;
    }

    public String getColorFav() {
        return colorFav;
    }

    public void setColorFav(String colorFav) {
        this.colorFav = colorFav;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAño(int anio) {
        this.anio = anio;
    }
}
