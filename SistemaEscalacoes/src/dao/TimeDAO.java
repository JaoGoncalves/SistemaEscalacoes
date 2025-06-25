package dao;

import classes.*;
import database.DataBaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import classes.Time;

public class TimeDAO {

    public void salvarTime(Time time) throws SQLException {
        String sql = "INSERT INTO times (nome) VALUES (?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, time.getNome()); // MUDANÇA: Usando getNome()
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int timeId = rs.getInt(1);
                time.setId(timeId); // Boa prática: atualizar o ID no objeto
                salvarJogadores(time.getEscalacao(), timeId);
            }
        }
    }

    public void atualizarTime(Time time, int timeId) throws SQLException {
        String sql = "UPDATE times SET nome = ? WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, time.getNome()); // MUDANÇA: Usando getNome()
            stmt.setInt(2, timeId);
            stmt.executeUpdate();

            // Remove jogadores antigos e adiciona os novos
            removerJogadoresDoTime(timeId);
            salvarJogadores(time.getEscalacao(), timeId);
        }
    }

    public void removerTime(int timeId) throws SQLException {
        // A chamada removerJogadoresDoTime(timeId) foi removida daqui
        // pois o "ON DELETE CASCADE" no MySQL já faz isso automaticamente.
        String sql = "DELETE FROM times WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, timeId);
            stmt.executeUpdate();
        }
    }

    // NOVO MÉTODO: Necessário para a TelaGerenciamento corrigida
    public void removerTimePorNome(String nome) throws SQLException {
        Time time = buscarTimePorNome(nome);
        if (time != null) {
            removerTime(time.getId());
        }
    }

    public List<Time> listarTimes() throws SQLException {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT * FROM times ORDER BY nome"; // Adicionado ORDER BY para consistência

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int timeId = rs.getInt("id");
                String nomeTime = rs.getString("nome");

                Time time = new Time(timeId, nomeTime); // Cria o time com ID e nome

                List<Jogador> jogadores = buscarJogadoresDoTime(timeId);
                time.setEscalacao(jogadores); // Define a lista de jogadores no objeto time

                times.add(time);
            }
        }
        return times;
    }

    public Time buscarTimePorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM times WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int timeId = rs.getInt("id");
                String nomeTime = rs.getString("nome");

                Time time = new Time(timeId, nomeTime);

                List<Jogador> jogadores = buscarJogadoresDoTime(timeId);
                time.setEscalacao(jogadores); // Define a lista de jogadores

                return time;
            }
        }
        return null;
    }

    private void salvarJogadores(List<Jogador> jogadores, int timeId) throws SQLException {
        String sql = "INSERT INTO jogadores (nome, numero, posicao, time_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Jogador jogador : jogadores) {
                stmt.setString(1, jogador.getNome());
                stmt.setInt(2, jogador.getNumero());
                stmt.setString(3, jogador.getPosicao());
                stmt.setInt(4, timeId);
                stmt.addBatch(); // Usar batch para melhor performance
            }
            stmt.executeBatch(); // Executa todas as inserções de uma vez
        }
    }

    private List<Jogador> buscarJogadoresDoTime(int timeId) throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores WHERE time_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, timeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                int numero = rs.getInt("numero");
                String posicao = rs.getString("posicao");

                Jogador jogador = criarJogadorPorPosicao(nome, numero, posicao);
                if (jogador != null) {
                    jogadores.add(jogador);
                }
            }
        }
        return jogadores;
    }

    private void removerJogadoresDoTime(int timeId) throws SQLException {
        String sql = "DELETE FROM jogadores WHERE time_id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, timeId);
            stmt.executeUpdate();
        }
    }

    private Jogador criarJogadorPorPosicao(String nome, int numero, String posicao) {
        switch (posicao.toLowerCase()) {
            case "goleiro":
                return new Goleiro(nome, numero);
            case "zagueiro":
                return new Zagueiro(nome, numero);
            case "lateral":
                return new Lateral(nome, numero);
            case "meio campo":
                return new MeioCampo(nome, numero);
            case "atacante":
                return new Atacante(nome, numero);
            default:
                return null;
        }
    }
}