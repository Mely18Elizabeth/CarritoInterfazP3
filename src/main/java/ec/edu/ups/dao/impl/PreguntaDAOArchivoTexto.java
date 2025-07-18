package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOArchivoTexto implements PreguntaDAO {

    private final String rutaArchivo;

    public PreguntaDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        try {
            // Asegura que el archivo exista. Si no existe, lo crea.
            // Si existe, no hace nada.
            new FileWriter(rutaArchivo, true).close();
            // Si el archivo está vacío, inicializa con preguntas por defecto
            if (new File(rutaArchivo).length() == 0) {
                inicializarPreguntasPorDefecto();
            }
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo de preguntas: " + e.getMessage());
        }
    }

    private void inicializarPreguntasPorDefecto() {
        List<String> preguntasPorDefecto = new ArrayList<>();
        preguntasPorDefecto.add("Nombre de Amigo");
        preguntasPorDefecto.add("Pasatiempo");
        preguntasPorDefecto.add("Ciudad de Nacimiento");
        preguntasPorDefecto.add("Canción Favorita");
        preguntasPorDefecto.add("Color Favorito");
        sobrescribirPreguntas(preguntasPorDefecto);
    }

    @Override
    public List<String> obtenerTodasLasPreguntas() {
        List<String> preguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                preguntas.add(linea);
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe, se devolverá una lista vacía
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preguntas;
    }

    public void guardarPregunta(String pregunta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            bw.write(pregunta);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sobrescribirPreguntas(List<String> nuevasPreguntas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (String pregunta : nuevasPreguntas) {
                bw.write(pregunta);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
