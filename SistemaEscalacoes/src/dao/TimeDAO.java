package dao;

import classes.Time;
import classes.Jogador;
import classes.Goleiro;
import classes.Zagueiro;
import classes.Lateral;
import classes.MeioCampo;
import classes.Atacante;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeDAO {

    public void salvarTime(Time time) throws SQLException {
        String sql = "INSERT INTO times (nome) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, time.getNomeEquipe());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int timeId = rs.getInt(1);
                salvarJogadores(time.getEscalacao(), timeId);
            }
        }
    }

    public void atualizarTime(Time time, int timeId) throws SQLException {
        String sql = "UPDATE times SET nome = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, time.getNomeEquipe());
            stmt.setInt(2, timeId);
            stmt.executeUpdate();

            // Remove jogadores antigos e adiciona os novos
            removerJogadoresDoTime(timeId);
            salvarJogadores(time.getEscalacao(), timeId);
        }
    }

    public void removerTime(int timeId) throws SQLException {
        String sql = "DELETE FROM times WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            removerJogadoresDoTime(timeId);
            stmt.setInt(1, timeId);
            stmt.executeUpdate();
        }
    }

    public List<Time> listarTimes() throws SQLException {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT * FROM times";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Time time = new Time();
                time.setNomeEquipe(rs.getString("nome"));

                List<Jogador> jogadores = buscarJogadoresDoTime(rs.getInt("id"));
                for (Jogador jogador : jogadores) {
                    try {
                        time.escalarJogador(jogador);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                times.add(time);
            }
        }
        return times;
    }

    public Time buscarTimePorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM times WHERE nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Time time = new Time();
                time.setNomeEquipe(rs.getString("nome"));

                List<Jogador> jogadores = buscarJogadoresDoTime(rs.getInt("id"));
                for (Jogador jogador : jogadores) {
                    try {
                        time.escalarJogador(jogador);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return time;
            }
        }
        return null;
    }

    private void salvarJogadores(List<Jogador> jogadores, int timeId) throws SQLException {
        String sql = "INSERT INTO jogadores (nome, numero, posicao, time_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Jogador jogador : jogadores) {
                stmt.setString(1, jogador.getNome());
                stmt.setInt(2, jogador.getNumero());
                stmt.setString(3, jogador.getPosicao());
                stmt.setInt(4, timeId);
                stmt.executeUpdate();
            }
        }
    }

    private List<Jogador> buscarJogadoresDoTime(int timeId) throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores WHERE time_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
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
        try (Connection conn = DatabaseConnection.getConnection();
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
