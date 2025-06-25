package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:escalacoes.db";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(URL);
                createTables();
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver SQLite não encontrado", e);
            }
        }
        return connection;
    }

    private static void createTables() throws SQLException {
        String createTimeTable = """
            CREATE TABLE IF NOT EXISTS times (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL UNIQUE
            )
        """;

        String createJogadorTable = """
            CREATE TABLE IF NOT EXISTS jogadores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                numero INTEGER NOT NULL,
                posicao TEXT NOT NULL,
                time_id INTEGER,
                FOREIGN KEY (time_id) REFERENCES times(id),
                UNIQUE(numero, time_id)
            )
        """;

        connection.createStatement().execute(createTimeTable);
        connection.createStatement().execute(createJogadorTable);
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
