package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class FabricaConexionMariaDB {
    private final String url;
    private final String usuario;
    private final String contrasena;

    public FabricaConexionMariaDB(String host, int puerto, String baseDatos, String usuario, String contrasena) {
        this.url = String.format("jdbc:mariadb://localhost:3306/atletas_db", host, puerto, baseDatos);
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public Connection obtenerConexion() throws Exception {
        Properties props = new Properties();
        props.setProperty("user", usuario);
        props.setProperty("password", contrasena);
        return DriverManager.getConnection(url, props);
    }
}
