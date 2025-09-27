package org.example;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportadorCsv {
    private static final DateTimeFormatter DF = DateTimeFormatter.ISO_DATE;

    public static void exportarSesiones(List<SesionEntrenamiento> sesiones, String ruta) throws Exception {
        try (FileWriter w = new FileWriter(ruta)) {
            w.append("id,atletaId,fecha,tipo,valor,unidad,ubicacion,pais\n");
            for (SesionEntrenamiento s : sesiones) {
                w.append(String.valueOf(s.getId())).append(',')
                        .append(String.valueOf(s.getAtletaId())).append(',')
                        .append(s.getFecha()!=null?s.getFecha().format(DF):"").append(',')
                        .append(s.getTipo()!=null?s.getTipo():"").append(',')
                        .append(s.getValor()!=null?String.valueOf(s.getValor()):"").append(',')
                        .append(s.getUnidad()!=null?s.getUnidad():"").append(',')
                        .append(s.getUbicacion()!=null?s.getUbicacion():"").append(',')
                        .append(s.getPais()!=null?s.getPais():"")
                        .append('\n');
            }
        }
    }
}
