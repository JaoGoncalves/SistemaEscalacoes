package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistema_escalacoes"; // Verifique a porta e o nome
                                                                                           // do schema
    private static final String USER = "root";
    private static final String PASS = "";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            createTables();
        } catch (SQLException e) {
            // O erro original acontece aqui, então o catch abaixo é acionado
            throw new RuntimeException("Erro ao conectar ao banco de dados: ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Driver do MySQL não encontrado! Verifique se o .jar está nas Referenced Libraries.", e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() {
        String createTimeTable = "CREATE TABLE IF NOT EXISTS times ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(255) NOT NULL UNIQUE"
                + ")";

        String createJogadorTable = "CREATE TABLE IF NOT EXISTS jogadores("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(255) NOT NULL,"
                + "posicao VARCHAR(100) NOT NULL,"
                + "numero INT,"
                + "time_id INT,"
                + "FOREIGN KEY (time_id) REFERENCES times(id) ON DELETE CASCADE,"
                + "UNIQUE(numero, time_id)"
                + ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTimeTable);
            stmt.execute(createJogadorTable);
        } catch (SQLException e) {

            System.out.println("Aviso ao tentar criar tabelas: " + e.getMessage());
        }
    }
}