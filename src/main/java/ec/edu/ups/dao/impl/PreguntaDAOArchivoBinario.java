package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreguntaDAOArchivoBinario implements PreguntaDAO {

    private final String rutaArchivo;

    public PreguntaDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        try {
            new File(rutaArchivo).createNewFile();
            // Si el archivo está vacío, inicializa con preguntas por defecto
            if (new File(rutaArchivo).length() == 0) {
                inicializarPreguntasPorDefecto();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo binario de preguntas: " + e.getMessage());
        }
    }

    private void inicializarPreguntasPorDefecto() {
        List<String> preguntasPorDefecto = Arrays.asList(
                "Nombre de Amigo",
                "Pasatiempo",
                "Ciudad de Nacimiento",
                "Canción Favorita",
                "Color Favorito"
        );
        escribirTodasLasPreguntas(preguntasPorDefecto);
    }

    private List<String> leerTodasLasPreguntas() {
        List<String> preguntas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            preguntas = (List<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Archivo no existe
        } catch (EOFException e) {
            // Archivo vacío
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return preguntas;
    }

    private void escribirTodasLasPreguntas(List<String> preguntas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(preguntas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> obtenerTodasLasPreguntas() {
        return leerTodasLasPreguntas();
    }

    // Métodos adicionales si necesitas guardar/sobrescribir preguntas individualmente
    public void guardarPregunta(String pregunta) {
        List<String> preguntas = leerTodasLasPreguntas();
        preguntas.add(pregunta);
        escribirTodasLasPreguntas(preguntas);
    }

    public void sobrescribirPreguntas(List<String> nuevasPreguntas) {
        escribirTodasLasPreguntas(nuevasPreguntas);
    }
}
