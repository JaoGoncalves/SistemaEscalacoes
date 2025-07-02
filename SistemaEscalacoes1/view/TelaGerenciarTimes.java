package view;

import controller.TimeController;
import model.Time;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaGerenciarTimes extends JFrame {
    private TimeController timeController;
    private JTextField txtNome;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnVoltar;
    private Time timeSelecionado = null;

    public TelaGerenciarTimes() {
        super("Gerenciar Times");
        this.timeController = new TimeController();
        configurarLayout();
        configurarEventos();
        configurarJanela();
        atualizarTabela();
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(UITheme.BACKGROUND_COLOR);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // PAINEL DE AÇÕES (ESQUERDA)
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new BoxLayout(painelAcoes, BoxLayout.Y_AXIS));
        painelAcoes.setBackground(UITheme.PANEL_COLOR);
        painelAcoes.setBorder(UITheme.BORDER_PANEL);
        painelAcoes.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));

        // Formulário de Adicionar/Editar
        JLabel lblTituloForm = new JLabel("Novo Time");
        lblTituloForm.setFont(UITheme.FONT_SUBTITULO);
        lblTituloForm.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNome = new JLabel("Nome do Time:");
        lblNome.setFont(UITheme.FONT_CORPO);
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNome = new JTextField();
        txtNome.setFont(UITheme.FONT_CORPO);
        txtNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtNome.getPreferredSize().height));

        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(UITheme.FONT_BOTAO);
        btnAdicionar.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Botões de Ação
        btnEditar = new JButton("Editar Time Selecionado");
        btnEditar.setFont(UITheme.FONT_BOTAO);
        btnEditar.setEnabled(false);
        btnEditar.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnExcluir = new JButton("Excluir Time Selecionado");
        btnExcluir.setFont(UITheme.FONT_BOTAO);
        btnExcluir.setEnabled(false);
        btnExcluir.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setFont(UITheme.FONT_BOTAO);
        btnVoltar.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelAcoes.add(lblTituloForm);
        painelAcoes.add(Box.createVerticalStrut(10));
        painelAcoes.add(lblNome);
        painelAcoes.add(Box.createVerticalStrut(5));
        painelAcoes.add(txtNome);
        painelAcoes.add(Box.createVerticalStrut(10));
        painelAcoes.add(btnAdicionar);
        painelAcoes.add(Box.createVerticalStrut(20));
        painelAcoes.add(new JSeparator());
        painelAcoes.add(Box.createVerticalStrut(20));
        painelAcoes.add(btnEditar);
        painelAcoes.add(Box.createVerticalStrut(10));
        painelAcoes.add(btnExcluir);
        painelAcoes.add(Box.createVerticalGlue());
        painelAcoes.add(btnVoltar);

        add(painelAcoes, BorderLayout.WEST);

        // PAINEL DA TABELA (CENTRO)
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(UITheme.PANEL_COLOR);
        painelTabela.setBorder(UITheme.BORDER_PANEL);

        JLabel lblTituloTabela = new JLabel("Times Cadastrados");
        lblTituloTabela.setFont(UITheme.FONT_SUBTITULO);
        lblTituloTabela.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelTabela.add(lblTituloTabela, BorderLayout.NORTH);

        String[] colunas = { "ID", "Nome do Time" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setFont(UITheme.FONT_CORPO);
        tabela.getTableHeader().setFont(UITheme.FONT_BOTAO);
        tabela.setRowHeight(25);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painelTabela.add(scrollPane, BorderLayout.CENTER);

        add(painelTabela, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnAdicionar.addActionListener(e -> adicionarTime());
        btnEditar.addActionListener(e -> editarTime());
        btnExcluir.addActionListener(e -> excluirTime());
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal().setVisible(true);
            dispose();
        });

        tabela.getSelectionModel().addListSelectionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            boolean selecionado = linhaSelecionada != -1;

            btnEditar.setEnabled(selecionado);
            btnExcluir.setEnabled(selecionado);

            if (selecionado) {
                int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
                String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
                timeSelecionado = new Time(id, nome);
            } else {
                timeSelecionado = null;
            }
        });
    }

    private void adicionarTime() {
        String nome = txtNome.getText().trim();
        if (timeController.criarTime(nome)) {
            txtNome.setText("");
            atualizarTabela();
        }
    }

    private void editarTime() {
        if (timeSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um time para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome para:", timeSelecionado.getNome());
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            if (timeController.atualizarTime(timeSelecionado.getId(), novoNome)) {
                atualizarTabela();
            }
        }
    }

    private void excluirTime() {
        if (timeSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um time para excluir!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja deletar o time '" + timeSelecionado.getNome()
                        + "'?\nTodos os jogadores do time também serão removidos.",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            if (timeController.deletarTime(timeSelecionado.getId())) {
                atualizarTabela();
            }
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Time> times = timeController.listarTimes();
        if (times != null) {
            for (Time time : times) {
                modeloTabela.addRow(new Object[] { time.getId(), time.getNome() });
            }
        }
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}