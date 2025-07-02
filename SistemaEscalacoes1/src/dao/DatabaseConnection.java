package dao;

// importando blibliotecas necessárias
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:src/escalacoes.db";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(URL);
            createTables();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: ", e);
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
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL UNIQUE"
                + ")";
        String createJogadorTable = "CREATE TABLE IF NOT EXISTS jogadores(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "posicao TEXT NOT NULL," +
                "numero INTEGER," +
                "time_id INTEGER," +
                "FOREIGN KEY (time_id) REFERENCES times(id)," +
                "UNIQUE(numero, time_id)" +
                ")";

        try {
            connection.createStatement().execute(createTimeTable);
            connection.createStatement().execute(createJogadorTable);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabelas", e);
        }
    }
}
