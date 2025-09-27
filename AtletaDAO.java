package org.example;
import java.util.List;
import java.util.Optional;

public interface AtletaDAO {
    Atleta crear(Atleta atleta) throws Exception;
    Optional<Atleta> buscarPorId(long id) throws Exception;
    List<Atleta> buscarTodos() throws Exception;
    void actualizar(Atleta atleta) throws Exception;
    void eliminar(long id) throws Exception;
}






