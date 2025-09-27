package org.example;
import java.util.List;

public interface EntrenamientoDAO {
    SesionEntrenamiento crear(SesionEntrenamiento sesion) throws Exception;
    List<SesionEntrenamiento> buscarPorAtletaId(long atletaId) throws Exception;
    List<SesionEntrenamiento> buscarTodos() throws Exception;
}
