package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/escalacoes_db?useSSL=false&serverTimezone=UTC";

    // 2. Credenciais do seu banco de dados MySQL
    private static final String USER = "root"; //
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Nome da classe do Driver do MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                //  Obtem a conexão usando usuário e senha
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                // A criação das tabelas só deve ocorrer se a conexão for nova
                createTables();

            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL não encontrado. Verifique se o .jar está no projeto.", e);
            }
        }
        return connection;
    }

    private static void createTables() throws SQLException {
        // 5. Sintaxe SQL ajustada para o MySQL
        // AUTOINCREMENT do SQLite vira AUTO_INCREMENT no MySQL
        // TEXT vira VARCHAR para melhor desempenho em campos pequenos como nome/posição
        String createTimeTable = """
            CREATE TABLE IF NOT EXISTS times (
                id INT PRIMARY KEY AUTO_INCREMENT,
                nome VARCHAR(255) NOT NULL UNIQUE
            )
        """;

        String createJogadorTable = """
            CREATE TABLE IF NOT EXISTS jogadores (
                id INT PRIMARY KEY AUTO_INCREMENT,
                nome VARCHAR(255) NOT NULL,
                numero INT NOT NULL,
                posicao VARCHAR(50) NOT NULL,
                time_id INT,
                FOREIGN KEY (time_id) REFERENCES times(id) ON DELETE CASCADE,
                UNIQUE(numero, time_id)
            )
        """;

        // Usando try-with-resources para garantir que o Statement seja fechado
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTimeTable);
            stmt.execute(createJogadorTable);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
