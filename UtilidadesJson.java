package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.util.List;

public class UtilidadesJson {
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void guardarAtletas(List<Atleta> atletas, String ruta) throws Exception {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(new File(ruta), atletas);
    }

    public static Atleta[] cargarAtletas(String ruta) throws Exception {
        return MAPPER.readValue(new File(ruta), Atleta[].class);
    }

    public static void guardarSesiones(List<SesionEntrenamiento> sesiones, String ruta) throws Exception {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(new File(ruta), sesiones);
    }

    public static SesionEntrenamiento[] cargarSesiones(String ruta) throws Exception {
        return MAPPER.readValue(new File(ruta), SesionEntrenamiento[].class);
    }
}

