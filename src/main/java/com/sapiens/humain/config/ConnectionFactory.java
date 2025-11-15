package com.sapiens.humain.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String URL =
            System.getenv().getOrDefault("ORACLE_URL", "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl");
    private static final String USER =
            System.getenv().getOrDefault("ORACLE_USER", "rm555541");
    private static final String PASS =
            System.getenv().getOrDefault("ORACLE_PASSWORD", "050206");

    // Criar conexão
    public static Connection getConnection(){
        try {
            Properties props = new Properties();
            props.setProperty("user", USER);
            props.setProperty("password", PASS);
            return DriverManager.getConnection(URL, props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}