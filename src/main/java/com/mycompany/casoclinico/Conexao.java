
package com.mycompany.casoclinico;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL      = "jdbc:postgresql://localhost:5432/caso_clinico";
    private static final String USUARIO  = "postgres";
    private static final String SENHA    = "root";

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conectado ao banco com sucesso!");
            return conn;
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}
