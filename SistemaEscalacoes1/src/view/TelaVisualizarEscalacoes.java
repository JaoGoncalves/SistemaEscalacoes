package view;

import controller.JogadorController;
import controller.TimeController;
import model.Jogador;
import model.Time;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TelaVisualizarEscalacoes extends JFrame {
    private TimeController timeController;
    private JogadorController jogadorController;
    private JComboBox<String> comboTimes;
    private JTable tabelaEscalacao;
    private DefaultTableModel modeloTabelaEscalacao;
    private JButton btnVoltar;
    private JButton btnVisualizar;

    // Mapa para associar o nome do time ao seu ID
    private Map<String, Integer> timeNomesParaIds;

    public TelaVisualizarEscalacoes() {
        super("Visualizar Escalações");
        this.timeController = new TimeController();
        this.jogadorController = new JogadorController();
        this.timeNomesParaIds = new HashMap<>();

        configurarLayout();
        carregarTimes();
        configurarEventos();
        configurarJanela();
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(UITheme.BACKGROUND_COLOR);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel Superior para Seleção de Time
        JPanel painelSelecao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelSelecao.setBackground(UITheme.PANEL_COLOR);
        painelSelecao.setBorder(UITheme.BORDER_PANEL);

        JLabel lblSelecionarTime = new JLabel("Selecionar Time:");
        lblSelecionarTime.setFont(UITheme.FONT_CORPO);
        comboTimes = new JComboBox<>();
        comboTimes.setFont(UITheme.FONT_CORPO);
        comboTimes.setPreferredSize(new Dimension(200, comboTimes.getPreferredSize().height));

        btnVisualizar = new JButton("Visualizar Escalação");
        btnVisualizar.setFont(UITheme.FONT_BOTAO);

        painelSelecao.add(lblSelecionarTime);
        painelSelecao.add(comboTimes);
        painelSelecao.add(btnVisualizar);

        add(painelSelecao, BorderLayout.NORTH);

        // Painel Central para a Tabela de Escalação
        JPanel painelEscalacao = new JPanel(new BorderLayout());
        painelEscalacao.setBackground(UITheme.PANEL_COLOR);
        painelEscalacao.setBorder(UITheme.BORDER_PANEL);

        JLabel lblTituloEscalacao = new JLabel("Escalação do Time", SwingConstants.CENTER);
        lblTituloEscalacao.setFont(UITheme.FONT_SUBTITULO);
        lblTituloEscalacao.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelEscalacao.add(lblTituloEscalacao, BorderLayout.NORTH);

        String[] colunas = { "Número", "Nome", "Posição" };
        modeloTabelaEscalacao = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEscalacao = new JTable(modeloTabelaEscalacao);
        tabelaEscalacao.setFont(UITheme.FONT_CORPO);
        tabelaEscalacao.getTableHeader().setFont(UITheme.FONT_BOTAO);
        tabelaEscalacao.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaEscalacao);
        painelEscalacao.add(scrollPane, BorderLayout.CENTER);

        add(painelEscalacao, BorderLayout.CENTER);

        // Painel Inferior para o Botão Voltar
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelVoltar.setBackground(UITheme.BACKGROUND_COLOR);
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setFont(UITheme.FONT_BOTAO);
        painelVoltar.add(btnVoltar);
        add(painelVoltar, BorderLayout.SOUTH);
    }

    private void carregarTimes() {
        List<Time> times = timeController.listarTimes();
        if (times != null) {
            // Limpa o mapa e o combo box antes de adicionar os novos dados
            timeNomesParaIds.clear();
            comboTimes.removeAllItems();

            // Preenche o mapa e o combo box
            for (Time time : times) {
                timeNomesParaIds.put(time.getNome(), time.getId());
                comboTimes.addItem(time.getNome());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao carregar times!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarEventos() {
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal().setVisible(true);
            dispose();
        });
        btnVisualizar.addActionListener(e -> {
            String nomeTimeSelecionado = (String) comboTimes.getSelectedItem();
            if (nomeTimeSelecionado != null) {
                // Obter o ID do time a partir do nome selecionado
                Integer timeId = timeNomesParaIds.get(nomeTimeSelecionado);

                if (timeId != null) {
                    // Usar o controller para buscar os jogadores daquele time
                    List<Jogador> jogadores = jogadorController.listarJogadoresPorTime(timeId);

                    // Limpar a tabela antes de adicionar os novos dados
                    modeloTabelaEscalacao.setRowCount(0);

                    // Preencher a tabela com os jogadores encontrados
                    if (jogadores != null && !jogadores.isEmpty()) {
                        for (Jogador jogador : jogadores) {
                            modeloTabelaEscalacao.addRow(new Object[] {
                                    jogador.getNumero(),
                                    jogador.getNome(),
                                    jogador.getPosicao()
                            });
                        }
                    } else {
                        // Mostra uma mensagem se o time não tiver jogadores
                        JOptionPane.showMessageDialog(this, "Este time ainda não possui jogadores cadastrados.",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um time para visualizar a escalação.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}