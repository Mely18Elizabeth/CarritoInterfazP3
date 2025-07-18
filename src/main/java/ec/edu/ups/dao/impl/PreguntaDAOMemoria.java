package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;

import java.util.Arrays;
import java.util.List;

public class PreguntaDAOMemoria implements PreguntaDAO {

    private final List<String> preguntas = Arrays.asList(
            "Nombre de Amigo",
            "Pasatiempo",
            "Ciudad de Nacimiento",
            "Canción Favorita",
            "Color Favorito"
    );

    @Override
    public List<String> obtenerTodasLasPreguntas() {
        return preguntas;
    }
}
