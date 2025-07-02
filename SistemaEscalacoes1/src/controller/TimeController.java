package controller;

import model.*;
import dao.TimeDAO;
import dao.JogadorDAO;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class TimeController {
    private TimeDAO timeDAO;
    private JogadorDAO jogadorDAO;

    public TimeController() {
        this.timeDAO = new TimeDAO();
        this.jogadorDAO = new JogadorDAO();
    }

    public boolean criarTime(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do time não pode estar vazio!");
                return false;
            }
            Time time = new Time(nome.trim());
            timeDAO.inserir(time);
            JOptionPane.showMessageDialog(null, "Time criado com sucesso!");
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                JOptionPane.showMessageDialog(null, "Já existe um time com este nome!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao criar time: " + e.getMessage());
            }
            return false;
        }
    }

    public boolean atualizarTime(int id, String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do time não pode estar vazio!");
                return false;
            }

            Time time = new Time(id, nome.trim());
            timeDAO.atualizar(time);
            JOptionPane.showMessageDialog(null, "Time atualizado com sucesso!");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar time: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarTime(int id) {
        try {
            int resposta = JOptionPane.showConfirmDialog(null,
                    "Tem certeza que deseja deletar este time? Todos os jogadores serão removidos também.",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                timeDAO.deletar(id);
                JOptionPane.showMessageDialog(null, "Time deletado com sucesso!");
                return true;
            }
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar time " + e.getMessage());
            return false;
        }
    }

    public List<Time> listarTimes() {
        try {
            return timeDAO.listarTodos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar times: " + e.getMessage());
            return null;
        }
    }

    public Time buscarTimePorId(int id) {
        try {
            return timeDAO.buscarPorId(id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar time: " + e.getMessage());
            return null;
        }
    }
}
