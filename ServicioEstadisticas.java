package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioEstadisticas {
    public static double promedio(List<SesionEntrenamiento> sesiones) {
        return sesiones.stream()
                .mapToDouble(SesionEntrenamiento::getValor)
                .average()
                .orElse(Double.NaN);
    }

    public static double mejorMarca(List<SesionEntrenamiento> sesiones, boolean maximizar) {
        if (sesiones.isEmpty()) return Double.NaN;
        if (maximizar)
            return sesiones.stream().mapToDouble(SesionEntrenamiento::getValor).max().orElse(Double.NaN);
        else
            return sesiones.stream().mapToDouble(SesionEntrenamiento::getValor).min().orElse(Double.NaN);
    }

    public static List<SesionEntrenamiento> ordenarPorFecha(List<SesionEntrenamiento> sesiones) {
        return sesiones.stream()
                .sorted(Comparator.comparing(SesionEntrenamiento::getFecha))
                .collect(Collectors.toList());
    }

    public static double promedioPorUbicacion(List<SesionEntrenamiento> sesiones, String ubicacion) {
        return sesiones.stream()
                .filter(s -> ubicacion.equals(s.getUbicacion()))
                .mapToDouble(SesionEntrenamiento::getValor)
                .average()
                .orElse(Double.NaN);
    }
}
