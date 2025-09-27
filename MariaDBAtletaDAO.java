package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MariaDBAtletaDAO implements AtletaDAO {
    private final FabricaConexionMariaDB fabrica;

    public MariaDBAtletaDAO(FabricaConexionMariaDB fabrica) { this.fabrica = fabrica; }

    @Override
    public Atleta crear(Atleta atleta) throws Exception {
        String sql = "INSERT INTO atleta (nombre, edad, disciplina, departamento, nacionalidad, fecha_ingreso) VALUES (?,?,?,?,?,?)";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, atleta.getNombre());
            ps.setObject(2, atleta.getEdad());
            ps.setString(3, atleta.getDisciplina());
            ps.setString(4, atleta.getDepartamento());
            ps.setString(5, atleta.getNacionalidad());
            ps.setDate(6, Date.valueOf(atleta.getFechaIngreso()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) atleta.setId(rs.getLong(1));
            }
        }
        return atleta;
    }

    @Override
    public Optional<Atleta> buscarPorId(long id) throws Exception {
        String sql = "SELECT * FROM atleta WHERE id = ?";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapearFila(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Atleta> buscarTodos() throws Exception {
        List<Atleta> lista = new ArrayList<>();
        String sql = "SELECT * FROM atleta";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearFila(rs));
        }
        return lista;
    }

    @Override
    public void actualizar(Atleta atleta) throws Exception {
        String sql = "UPDATE atleta SET nombre=?, edad=?, disciplina=?, departamento=?, nacionalidad=?, fecha_ingreso=? WHERE id=?";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, atleta.getNombre());
            ps.setObject(2, atleta.getEdad());
            ps.setString(3, atleta.getDisciplina());
            ps.setString(4, atleta.getDepartamento());
            ps.setString(5, atleta.getNacionalidad());
            ps.setDate(6, Date.valueOf(atleta.getFechaIngreso()));
            ps.setLong(7, atleta.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id) throws Exception {
        String sql = "DELETE FROM atleta WHERE id = ?";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Atleta mapearFila(ResultSet rs) throws SQLException {
        Atleta a = new Atleta();
        a.setId(rs.getLong("id"));
        a.setNombre(rs.getString("nombre"));
        a.setEdad((Integer) rs.getObject("edad"));
        a.setDisciplina(rs.getString("disciplina"));
        a.setDepartamento(rs.getString("departamento"));
        a.setNacionalidad(rs.getString("nacionalidad"));
        Date d = rs.getDate("fecha_ingreso");
        if (d != null) a.setFechaIngreso(d.toLocalDate());
        return a;
    }
}
