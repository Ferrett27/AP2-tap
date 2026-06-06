package com.example.lojaJogos.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static ConexaoBanco instancia;

    private final String jdbcUrl = "jdbc:mysql://b8bgem4sg8rrxbqnapq6-mysql.services.clever-cloud.com:3306/b8bgem4sg8rrxbqnapq6";
    private final String user = "uhko8qr3h3a0yr6w";
    private final String password = "WhSxdSXoz8CEZjWdVH9R";

    private ConexaoBanco() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do MySQL não encontrado!", e);
        }
    }

    // Utilização do Singleton aqui
    public static ConexaoBanco getInstancia() {
        if (instancia == null) {
            instancia = new ConexaoBanco();
        }
        return instancia;
    }

    public Connection getConexao() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }
}
