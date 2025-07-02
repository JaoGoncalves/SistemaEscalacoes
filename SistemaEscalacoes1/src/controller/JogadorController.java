package controller;

import model.*;
import dao.JogadorDAO;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class JogadorController {
    private JogadorDAO jogadorDAO;

    public JogadorController() {
        this.jogadorDAO = new JogadorDAO();
    }

    public boolean criarJogador(String nome, int numero, String posicao, int timeId) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do jogador não pode estar vazio!");
                return false;
            }
            if (numero <= 0 || numero > 99) {
                JOptionPane.showMessageDialog(null, "Número deve estar entre 1 e 99!");
                return false;
            }
            Jogador jogador = criarJogadorPorPosicao(posicao);
            jogador.setNome(nome.trim());
            jogador.setNumero(numero);
            jogador.setTimeId(timeId);

            jogadorDAO.inserir(jogador);
            JOptionPane.showMessageDialog(null, "Jogador criado com sucesso!");
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                JOptionPane.showMessageDialog(null, "Já existe um jogador com este número neste time!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao criar jogador: " + e.getMessage());
            }
            return false;
        }
    }

    public boolean atualizarJogador(int id, String nome, int numero, String posicao, int timeId) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do jogador não pode estar vazio!");
                return false;
            }

            if (numero <= 0 || numero > 99) {
                JOptionPane.showMessageDialog(null, "Número deve estar entre 1 e 99!");
                return false;
            }

            Jogador jogador = criarJogadorPorPosicao(posicao);
            jogador.setId(id);
            jogador.setNome(nome.trim());
            jogador.setNumero(numero);
            jogador.setTimeId(timeId);

            jogadorDAO.atualizar(jogador);
            JOptionPane.showMessageDialog(null, "Jogador atualizado com sucesso!");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar jogador: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarJogador(int id) {
        try {
            int resposta = JOptionPane.showConfirmDialog(null,
                    "Tem certeza que deseja deletar este jogador?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                jogadorDAO.deletar(id);
                JOptionPane.showMessageDialog(null, "Jogador deletado com sucesso!");
                return true;
            }
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar jogador: " + e.getMessage());
            return false;
        }
    }

    public List<Jogador> listarJogadores() {
        try {
            return jogadorDAO.listarTodos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar jogadores: " + e.getMessage());
            return null;
        }
    }

    public List<Jogador> listarJogadoresPorTime(int timeId) {
        try {
            return jogadorDAO.buscarPorTimeId(timeId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar jogadores do time: " + e.getMessage());
            return null;
        }
    }

    private Jogador criarJogadorPorPosicao(String posicao) {
        switch (posicao) {
            case "Goleiro":
                return new Goleiro();
            case "Zagueiro":
                return new Zagueiro();
            case "Lateral":
                return new Lateral();
            case "Meio Campo":
                return new MeioCampo();
            case "Atacante":
                return new Atacante();
            default:
                throw new IllegalArgumentException("Posição inválida: " + posicao);
        }
    }

    public String[] getPosicoes() {
        return new String[] { "Goleiro", "Zagueiro", "Lateral", "Meio Campo", "Atacante" };
    }
}
