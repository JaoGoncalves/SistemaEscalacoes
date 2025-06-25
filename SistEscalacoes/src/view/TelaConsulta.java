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

public class TelaConsulta extends JFrame {
    private JTextField txtBusca;
    private JTable tabelaTimes;
    private DefaultTableModel modeloTabelaTimes;
    private JTable tabelaJogadores;
    private DefaultTableModel modeloTabelaJogadores;
    private TimeDAO timeDAO;

    public TelaConsulta() {
        timeDAO = new TimeDAO();
        initComponents();
        //carregarTimes();
    }

    private void initComponents() {
        setTitle("Consultar Times e Escalações");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(60, 179, 113));
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        JLabel headerLabel = new JLabel("CONSULTAR TIMES E ESCALAÇÕES", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de busca
        JPanel buscaPanel = createBuscaPanel();

        // Painel das tabelas
        JPanel tabelasPanel = createTabelasPanel();

        // Painel de botões
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(buscaPanel, BorderLayout.NORTH);
        mainPanel.add(tabelasPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createBuscaPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar Time"));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("Nome do Time:"));
        txtBusca = new JTextField(20);
        txtBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(txtBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(this::buscarTime);
        panel.add(btnBuscar);

        JButton btnListarTodos = new JButton("Listar Todos");
        btnListarTodos.setBackground(new Color(255, 140, 0));
        btnListarTodos.setForeground(Color.WHITE);
        btnListarTodos.setBorderPainted(false);
        btnListarTodos.setFocusPainted(false);
        btnListarTodos.addActionListener(e -> carregarTimes());
        panel.add(btnListarTodos);

        return panel;
    }

    private JPanel createTabelasPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));

        // Tabela de times
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

        // Tabela de jogadores
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

        JButton btnVoltar = new JButton("VOLTAR");
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
                        time.getNome(),
                        time.getEscalacao().size()
                });
            }

            if (times.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nenhum time cadastrado!",
                        "Informação",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar times: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarTime(ActionEvent e) {
        String nomeBusca = txtBusca.getText().trim();
        if (nomeBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Digite o nome do time para buscar!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Time time = timeDAO.buscarTimePorNome(nomeBusca);
            modeloTabelaTimes.setRowCount(0);
            modeloTabelaJogadores.setRowCount(0);

            if (time != null) {
                modeloTabelaTimes.addRow(new Object[]{
                        time.getNome(),
                        time.getEscalacao().size()
                });
                carregarJogadoresDoTime(time.getNome());
            } else {
                JOptionPane.showMessageDialog(this,
                        "Time não encontrado!",
                        "Informação",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao buscar time: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarJogadoresDoTime(String nomeTime) {
        try {
            Time time = timeDAO.buscarTimePorNome(nomeTime);
            modeloTabelaJogadores.setRowCount(0);

            if (time != null) {
                for (Jogador jogador : time.getEscalacao()) {
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
}
