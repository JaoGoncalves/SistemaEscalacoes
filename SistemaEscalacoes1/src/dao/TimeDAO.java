package dao;

import model.*;
import model.Time;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeDAO {
    private Connection connection;

    public TimeDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void inserir(Time time) throws SQLException {
        String sql = "INSERT INTO times (nomes) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, time.getNome());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                time.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Time time) throws SQLException {
        String sql = "UPDATE times SET nome = ? WHERE(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, time.getNome());
            stmt.setInt(2, time.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        // Primeiro deletar jogadores do time
        String sqlJogadores = "DELETE FROM jogadores WHERE time_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlJogadores)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

        // Depois deletar o time
        String sql = "DELETE FROM times WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Time buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM times WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Time time = new Time(rs.getInt("id"), rs.getString("nome"));
                // Carregar jogadores do time
                JogadorDAO jogadorDAO = new JogadorDAO();
                List<Jogador> jogadores = jogadorDAO.buscarPorTimeId(id);
                time.setEscalacao(jogadores);
                return time;
            }
        }
        return null;
    }

    public List<Time> listarTodos() throws SQLException {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT * FROM times ORDER BY nome";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Time time = new Time(rs.getInt("id"), rs.getString("nome"));
                times.add(time);
            }
        }
        return times;
    }

}
