package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {
    private Connection connection;

    public JogadorDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void inserir(Jogador jogador) throws SQLException {
        String sql = "INSERT INTO jogadores (nome, numero, posicao, time_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, jogador.getNome());
            stmt.setInt(2, jogador.getNumero());
            stmt.setString(3, jogador.getPosicao());
            stmt.setInt(4, jogador.getTimeId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                jogador.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Jogador jogador) throws SQLException {
        String sql = "UPDATE jogadores SET nome = ?, numero = ?, posicao = ?, time_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, jogador.getNome());
            stmt.setInt(2, jogador.getNumero());
            stmt.setString(3, jogador.getPosicao());
            stmt.setInt(4, jogador.getTimeId());
            stmt.setInt(5, jogador.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM jogadores WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Jogador buscarPorId(int id) throws SQLException {
        String sql = "SELECT * from jogadores WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return criarJogadorPorPosicao(rs);
            }
        }
        return null;
    }

    public List<Jogador> buscarPorTimeId(int timeId) throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores WHERE time_id = ? ORDER BY numero";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, timeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                jogadores.add(criarJogadorPorPosicao(rs));
            }
        }
        return jogadores;
    }

    public List<Jogador> listarTodos() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores ORDER BY nome";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                jogadores.add(criarJogadorPorPosicao(rs));
            }
        }
        return jogadores;
    }

    private Jogador criarJogadorPorPosicao(ResultSet rs) throws SQLException {
        String posicao = rs.getString("posicao");
        Jogador jogador;

        switch (posicao) {
            case "Goleiro":
                jogador = new Goleiro();
                break;
            case "Zagueiro":
                jogador = new Zagueiro();
                break;
            case "Lateral":
                jogador = new Lateral();
                break;
            case "Meio Campo":
                jogador = new MeioCampo();
                break;
            case "Atacante":
                jogador = new Atacante();
                break;
            default:
                throw new SQLException("Posição Inválida " + posicao);
        }

        jogador.setId(rs.getInt("id"));
        jogador.setNome(rs.getString("nome"));
        jogador.setNumero(rs.getInt("numero"));
        jogador.setTimeId(rs.getInt("time_id"));

        return jogador;
    }
}
