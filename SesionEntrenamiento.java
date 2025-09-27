package org.example;
import java.time.LocalDate;

public class SesionEntrenamiento {
    private Long id;
    private Long atletaId;
    private LocalDate fecha;
    private String tipo;
    private Double valor;
    private String unidad;
    private String ubicacion;
    private String pais;

    public SesionEntrenamiento() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAtletaId() { return atletaId; }
    public void setAtletaId(Long atletaId) { this.atletaId = atletaId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        return "SesionEntrenamiento{" + "id=" + id + ", atletaId=" + atletaId + ", fecha=" + fecha + ", tipo='" + tipo + '\'' + ", valor=" + valor + ", unidad='" + unidad + '\'' + ", ubicacion='" + ubicacion + '\'' + ", pais='" + pais + '\'' + '}';
    }
}