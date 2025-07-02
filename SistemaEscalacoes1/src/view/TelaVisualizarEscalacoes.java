package view;

import controller.JogadorController;
import controller.TimeController;
import model.Jogador;
import model.Time;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaVisualizarEscalacoes extends JFrame {
    private TimeController timeController;
    private JogadorController jogadorController;
    private JComboBox<Time> cbTimes;
    private JTable tabelaEscalacao;
    private DefaultTableModel modeloTabela;
    private JLabel lblTotalJogadores;
    private JLabel lblFormacao;
    private JButton btnVoltar;
    private JButton btnAtualizar;

    public TelaVisualizarEscalacoes() {
        this.timeController = new TimeController();
        this.jogadorController = new JogadorController();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
        carregarTimes();
    }

    private void inicializarComponentes() {
        cbTimes = new JComboBox<>();

        // Configurar tabela
        String[] colunas = { "Número", "Nome", "Posição" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEscalacao = new JTable(modeloTabela);
        tabelaEscalacao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        lblTotalJogadores = new JLabel("Total de jogadores: 0");
        lblFormacao = new JLabel("Formação: N/A");
        btnVoltar = new JButton("Voltar");
        btnAtualizar = new JButton("Atualizar");
    }

    private void configurarLayout() {
        setLayout(new BorderLayout());

        // Painel superior
        JPanel painelSuperior = new JPanel(new FlowLayout());
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Selecionar Time"));
        painelSuperior.add(new JLabel("Time:"));
        painelSuperior.add(cbTimes);
        painelSuperior.add(btnAtualizar);

        // Painel central - tabela
        JScrollPane scrollPane = new JScrollPane(tabelaEscalacao);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Escalação"));

        // Painel lateral - informações
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setBorder(BorderFactory.createTitledBorder("Informações"));
        painelLateral.add(lblTotalJogadores);
        painelLateral.add(Box.createVerticalStrut(10));
        painelLateral.add(lblFormacao);
        painelLateral.add(Box.createVerticalGlue());

        // Painel principal central
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(scrollPane, BorderLayout.CENTER);
        painelCentral.add(painelLateral, BorderLayout.EAST);

        // Painel inferior
        JPanel painelInferior = new JPanel(new FlowLayout());
        painelInferior.add(btnVoltar);

        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        cbTimes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarEscalacao();
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTimes();
                atualizarEscalacao();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaPrincipal().setVisible(true);
                dispose();
            }
        });
    }

    private void carregarTimes() {
        cbTimes.removeAllItems();
        List<Time> times = timeController.listarTimes();
        if (times != null) {
            for (Time time : times) {
                cbTimes.addItem(time);
            }
        }
    }

    private void atualizarEscalacao() {
        modeloTabela.setRowCount(0);
        Time timeSelecionado = (Time) cbTimes.getSelectedItem();

        if (timeSelecionado == null) {
            lblTotalJogadores.setText("Total de jogadores: 0");
            lblFormacao.setText("Formação: N/A");
            return;
        }

        List<Jogador> jogadores = jogadorController.listarJogadoresPorTime(timeSelecionado.getId());
        if (jogadores != null) {
            // Organizar jogadores por posição
            int goleiros = 0, zagueiros = 0, laterais = 0, meios = 0, atacantes = 0;

            for (Jogador jogador : jogadores) {
                Object[] linha = { jogador.getNumero(), jogador.getNome(), jogador.getPosicao() };
                modeloTabela.addRow(linha);

                // Contar posições
                switch (jogador.getPosicao()) {
                    case "Goleiro":
                        goleiros++;
                        break;
                    case "Zagueiro":
                        zagueiros++;
                        break;
                    case "Lateral":
                        laterais++;
                        break;
                    case "Meio Campo":
                        meios++;
                        break;
                    case "Atacante":
                        atacantes++;
                        break;
                }
            }

            lblTotalJogadores.setText("Total de jogadores: " + jogadores.size());
            lblFormacao.setText(String.format("Formação: %d-%d-%d-%d-%d",
                    goleiros, zagueiros, laterais, meios, atacantes));
        } else {
            lblTotalJogadores.setText("Total de jogadores: 0");
            lblFormacao.setText("Formação: N/A");
        }
    }

    private void configurarJanela() {
        setTitle("Visualizar Escalações");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
    }
}
