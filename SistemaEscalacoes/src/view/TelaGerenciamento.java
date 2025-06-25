package view;

import classes.Time;
import classes.Jogador;
import dao.TimeDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaGerenciamento extends JFrame {
    private JTable tabelaTimes;
    private DefaultTableModel modeloTabelaTimes;
    private JTable tabelaJogadores;
    private DefaultTableModel modeloTabelaJogadores;
    private TimeDAO timeDAO;
    private Time timeSelecionado;

    public TelaGerenciamento() {
        timeDAO = new TimeDAO();
        initComponents();
        carregarTimes();
    }

    private void initComponents() {
        setTitle("Gerenciar Times e Escalações");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        JLabel headerLabel = new JLabel("⚙️ GERENCIAR TIMES E ESCALAÇÕES", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel das tabelas
        JPanel tabelasPanel = createTabelasPanel();

        // Painel de botões
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(tabelasPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTabelasPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));

        // Painel de times
        JPanel timesPanel = new JPanel(new BorderLayout());
        timesPanel.setBorder(BorderFactory.createTitledBorder("Times Cadastrados"));

        String[] colunasTimes = {"Nome do Time", "Jogadores"};
        modeloTabelaTimes = new DefaultTableModel(colunasTimes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaTimes = new JTable(modeloTabelaTimes);
        tabelaTimes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaTimes.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaTimes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaTimes.setRowHeight(25);

        // Evento de seleção na tabela de times
        tabelaTimes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tabelaTimes.getSelectedRow();
                if (selectedRow >= 0) {
                    String nomeTime = (String) modeloTabelaTimes.getValueAt(selectedRow, 0);
                    carregarJogadoresDoTime(nomeTime);
                }
            }
        });

        JScrollPane scrollTimes = new JScrollPane(tabelaTimes);
        timesPanel.add(scrollTimes, BorderLayout.CENTER);

        // Botões para times
        JPanel buttonTimesPanel = new JPanel(new FlowLayout());

        JButton btnEditarTime = new JButton("✏️ Editar Nome");
        btnEditarTime.setBackground(new Color(70, 130, 180));
        btnEditarTime.setForeground(Color.WHITE);
        btnEditarTime.setBorderPainted(false);
        btnEditarTime.setFocusPainted(false);
        btnEditarTime.addActionListener(this::editarTime);

        JButton btnExcluirTime = new JButton("🗑️ Excluir Time");
        btnExcluirTime.setBackground(new Color(220, 20, 60));
        btnExcluirTime.setForeground(Color.WHITE);
        btnExcluirTime.setBorderPainted(false);
        btnExcluirTime.setFocusPainted(false);
        btnExcluirTime.addActionListener(this::excluirTime);

        buttonTimesPanel.add(btnEditarTime);
        buttonTimesPanel.add(btnExcluirTime);
        timesPanel.add(buttonTimesPanel, BorderLayout.SOUTH);

        // Painel de jogadores
        JPanel jogadoresPanel = new JPanel(new BorderLayout());
        jogadoresPanel.setBorder(BorderFactory.createTitledBorder("Escalação do Time Selecionado"));

        String[] colunasJogadores = {"Nome", "Número", "Posição"};
        modeloTabelaJogadores = new DefaultTableModel(colunasJogadores, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaJogadores = new JTable(modeloTabelaJogadores);
        tabelaJogadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaJogadores.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaJogadores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaJogadores.setRowHeight(25);

        JScrollPane scrollJogadores = new JScrollPane(tabelaJogadores);
        jogadoresPanel.add(scrollJogadores, BorderLayout.CENTER);

        panel.add(timesPanel);
        panel.add(jogadoresPanel);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnAtualizar = new JButton("🔄 ATUALIZAR LISTA");
        btnAtualizar.setPreferredSize(new Dimension(180, 40));
        btnAtualizar.setBackground(new Color(60, 179, 113));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAtualizar.setBorderPainted(false);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.addActionListener(e -> carregarTimes());

        JButton btnVoltar = new JButton("🏠 VOLTAR");
        btnVoltar.setPreferredSize(new Dimension(120, 40));
        btnVoltar.setBackground(new Color(128, 128, 128));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setBorderPainted(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal().setVisible(true);
            dispose();
        });

        panel.add(btnAtualizar);
        panel.add(btnVoltar);

        return panel;
    }

    private void carregarTimes() {
        try {
            List<Time> times = timeDAO.listarTimes();
            modeloTabelaTimes.setRowCount(0);
            modeloTabelaJogadores.setRowCount(0);

            for (Time time : times) {
                modeloTabelaTimes.addRow(new Object[]{
                        time.getNome(), // MUDANÇA: Usando getNome() padronizado
                        time.getEscalacao().size()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar times: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarJogadoresDoTime(String nomeTime) {
        try {
            timeSelecionado = timeDAO.buscarTimePorNome(nomeTime);
            modeloTabelaJogadores.setRowCount(0);

            if (timeSelecionado != null) {
                for (Jogador jogador : timeSelecionado.getEscalacao()) {
                    modeloTabelaJogadores.addRow(new Object[]{
                            jogador.getNome(),
                            jogador.getNumero(),
                            jogador.getPosicao()
                    });
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar jogadores: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MÉTODO EDITAR CORRIGIDO
    private void editarTime(ActionEvent e) {
        int selectedRow = tabelaTimes.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um time para editar!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomeAtual = (String) modeloTabelaTimes.getValueAt(selectedRow, 0);
        String novoNome = JOptionPane.showInputDialog(this,
                "Digite o novo nome do time:",
                nomeAtual);

        if (novoNome != null && !novoNome.trim().isEmpty() && !novoNome.equals(nomeAtual)) {
            try {
                Time time = timeDAO.buscarTimePorNome(nomeAtual);
                if (time != null) {
                    time.setNome(novoNome.trim());
                    // LÓGICA CORRETA: Chama o método de atualização do DAO
                    timeDAO.atualizarTime(time, time.getId());

                    JOptionPane.showMessageDialog(this,
                            "Nome do time atualizado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    carregarTimes();
                }

            } catch (SQLException ex) {
                String mensagem = ex.getMessage().contains("UNIQUE constraint failed") ?
                        "Já existe um time com este nome!" :
                        "Erro ao atualizar time: " + ex.getMessage();

                JOptionPane.showMessageDialog(this,
                        mensagem,
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MÉTODO EXCLUIR CORRIGIDO
    private void excluirTime(ActionEvent e) {
        int selectedRow = tabelaTimes.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um time para excluir!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomeTime = (String) modeloTabelaTimes.getValueAt(selectedRow, 0);

        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o time \"" + nomeTime + "\"?\n" +
                        "Esta ação não pode ser desfeita!",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                // LÓGICA CORRETA: Chama um método do DAO para fazer o trabalho
                timeDAO.removerTimePorNome(nomeTime);

                JOptionPane.showMessageDialog(this,
                        "Time excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                carregarTimes();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao excluir time: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}