package org.example;
import java.time.LocalDate;
import java.util.Objects;

public class Atleta {
    private Long id;
    private String nombre;
    private Integer edad;
    private String disciplina;
    private String departamento;
    private String nacionalidad;
    private LocalDate fechaIngreso;

    public Atleta() {}

    public Atleta(Long id, String nombre, Integer edad, String disciplina, String departamento, String nacionalidad, LocalDate fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.disciplina = disciplina;
        this.departamento = departamento;
        this.nacionalidad = nacionalidad;
        this.fechaIngreso = fechaIngreso;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    @Override
    public String toString() {
        return "Atleta{" + "id=" + id + ", nombre='" + nombre + '\'' + ", edad=" + edad + ", disciplina='" + disciplina + '\'' + ", departamento='" + departamento + '\'' + ", nacionalidad='" + nacionalidad + '\'' + ", fechaIngreso=" + fechaIngreso + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Atleta)) return false;
        Atleta atleta = (Atleta) o;
        return Objects.equals(id, atleta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
