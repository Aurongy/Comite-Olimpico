package org.example;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MariaDBEntrenamientoDAO implements EntrenamientoDAO {
    private final FabricaConexionMariaDB fabrica;

    public MariaDBEntrenamientoDAO(FabricaConexionMariaDB fabrica) { this.fabrica = fabrica; }

    @Override
    public SesionEntrenamiento crear(SesionEntrenamiento s) throws Exception {
        String sql = "INSERT INTO entrenamiento (atleta_id, fecha, tipo, valor, unidad, ubicacion, pais) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, s.getAtletaId());
            ps.setDate(2, Date.valueOf(s.getFecha()));
            ps.setString(3, s.getTipo());
            ps.setDouble(4, s.getValor());
            ps.setString(5, s.getUnidad());
            ps.setString(6, s.getUbicacion());
            ps.setString(7, s.getPais());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) s.setId(rs.getLong(1)); }
        }
        return s;
    }

    @Override
    public List<SesionEntrenamiento> buscarPorAtletaId(long atletaId) throws Exception {
        List<SesionEntrenamiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenamiento WHERE atleta_id = ? ORDER BY fecha";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, atletaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearFila(rs));
            }
        }
        return lista;
    }

    @Override
    public List<SesionEntrenamiento> buscarTodos() throws Exception {
        List<SesionEntrenamiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenamiento ORDER BY fecha";
        try (Connection c = fabrica.obtenerConexion(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearFila(rs));
        }
        return lista;
    }

    private SesionEntrenamiento mapearFila(ResultSet rs) throws SQLException {
        SesionEntrenamiento s = new SesionEntrenamiento();
        s.setId(rs.getLong("id"));
        s.setAtletaId(rs.getLong("atleta_id"));
        Date d = rs.getDate("fecha"); if (d != null) s.setFecha(d.toLocalDate());
        s.setTipo(rs.getString("tipo"));
        s.setValor(rs.getDouble("valor"));
        s.setUnidad(rs.getString("unidad"));
        s.setUbicacion(rs.getString("ubicacion"));
        s.setPais(rs.getString("pais"));
        return s;
    }

}
