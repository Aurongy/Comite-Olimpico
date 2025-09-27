package org.example;

import java.util.List;

public class CalculadoraPlanilla {
    private final double pagoBasePorEntrenamiento;
    private final double bonoPorEntrenamientoExtranjero;
    private final double bonoPorSuperarMejorMarca;

    public CalculadoraPlanilla(double pagoBasePorEntrenamiento, double bonoPorEntrenamientoExtranjero, double bonoPorSuperarMejorMarca) {
        this.pagoBasePorEntrenamiento = pagoBasePorEntrenamiento;
        this.bonoPorEntrenamientoExtranjero = bonoPorEntrenamientoExtranjero;
        this.bonoPorSuperarMejorMarca = bonoPorSuperarMejorMarca;
    }

    public double calcularPagoMensual(List<SesionEntrenamiento> sesiones, double mejorMarcaHistorica) {
        if (sesiones == null || sesiones.isEmpty()) return 0.0;
        double totalBase = sesiones.size() * pagoBasePorEntrenamiento;
        long internacionales = sesiones.stream().filter(s -> "Internacional".equalsIgnoreCase(s.getUbicacion()) || "Internacional".equalsIgnoreCase(s.getPais())).count();
        double bonoInt = internacionales * bonoPorEntrenamientoExtranjero;
        long quiebranMejor = sesiones.stream().filter(s -> s.getValor() > mejorMarcaHistorica).count();
        double bonoMejor = quiebranMejor * bonoPorSuperarMejorMarca;
        return totalBase + bonoInt + bonoMejor;
    }
}
