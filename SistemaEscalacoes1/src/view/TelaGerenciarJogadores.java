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

public class TelaGerenciarJogadores extends JFrame {
    private JogadorController jogadorController;
    private TimeController timeController;

    // Componentes da UI
    private JTextField txtNome;
    private JComboBox<String> comboPosicao;
    private JTextField txtNumero;
    private JComboBox<String> comboTime;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnVoltar;

    // Estado da seleção
    private List<Jogador> listaDeJogadoresAtual;
    private Jogador jogadorSelecionado = null;
    private Map<String, Integer> timeNomesParaIds;

    public TelaGerenciarJogadores() {
        super("Gerenciar Jogadores");
        this.jogadorController = new JogadorController();
        this.timeController = new TimeController();
        carregarDadosIniciais();
        configurarLayout();
        configurarEventos();
        configurarJanela();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        List<Time> times = timeController.listarTimes();
        if (times != null) {
            timeNomesParaIds = times.stream()
                    .collect(Collectors.toMap(Time::getNome, Time::getId));
        } else {
            timeNomesParaIds = new HashMap<>();
            JOptionPane.showMessageDialog(this, "Erro ao carregar times!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(UITheme.BACKGROUND_COLOR);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de Ações (Esquerda)
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new BoxLayout(painelAcoes, BoxLayout.Y_AXIS));
        painelAcoes.setBackground(UITheme.PANEL_COLOR);
        painelAcoes.setBorder(UITheme.BORDER_PANEL);
        painelAcoes.setPreferredSize(new Dimension(280, 0));

        JLabel lblTituloForm = new JLabel("Novo Jogador");
        lblTituloForm.setFont(UITheme.FONT_SUBTITULO);
        lblTituloForm.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNome = new JTextField();
        comboPosicao = new JComboBox<>(new String[] { "Goleiro", "Zagueiro", "Lateral", "Meio Campo", "Atacante" });
        txtNumero = new JTextField();
        comboTime = new JComboBox<>();

        painelAcoes.add(lblTituloForm);
        painelAcoes.add(Box.createVerticalStrut(10));
        painelAcoes.add(criarCampoFormulario("Nome:", txtNome));
        painelAcoes.add(criarCampoFormulario("Posição:", comboPosicao));
        painelAcoes.add(criarCampoFormulario("Número:", txtNumero));
        painelAcoes.add(criarCampoFormulario("Time:", comboTime));

        DefaultComboBoxModel<String> comboModel = (DefaultComboBoxModel<String>) comboTime.getModel();
        timeNomesParaIds.keySet().stream().sorted().forEach(comboModel::addElement);

        painelAcoes.add(Box.createVerticalStrut(15));
        btnAdicionar = new JButton("Adicionar Jogador");
        btnAdicionar.setFont(UITheme.FONT_BOTAO);
        btnAdicionar.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelAcoes.add(btnAdicionar);

        painelAcoes.add(Box.createVerticalStrut(20));
        painelAcoes.add(new JSeparator());
        painelAcoes.add(Box.createVerticalStrut(20));

        btnEditar = new JButton("Editar Jogador Selecionado");
        btnEditar.setFont(UITheme.FONT_BOTAO);
        btnEditar.setEnabled(false);
        btnEditar.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelAcoes.add(btnEditar);

        painelAcoes.add(Box.createVerticalStrut(10));
        btnExcluir = new JButton("Excluir Jogador Selecionado");
        btnExcluir.setFont(UITheme.FONT_BOTAO);
        btnExcluir.setEnabled(false);
        btnExcluir.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelAcoes.add(btnExcluir);

        painelAcoes.add(Box.createVerticalGlue());
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setFont(UITheme.FONT_BOTAO);
        btnVoltar.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelAcoes.add(btnVoltar);

        add(painelAcoes, BorderLayout.WEST);

        // Painel da Tabela (Centro)
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(UITheme.PANEL_COLOR);
        painelTabela.setBorder(UITheme.BORDER_PANEL);

        JLabel lblTituloTabela = new JLabel("Jogadores Cadastrados");
        lblTituloTabela.setFont(UITheme.FONT_SUBTITULO);
        lblTituloTabela.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelTabela.add(lblTituloTabela, BorderLayout.NORTH);

        String[] colunas = { "ID", "Nome", "Posição", "Número", "Time" };
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

    private JPanel criarCampoFormulario(String label, JComponent componente) {
        JPanel painelCampo = new JPanel();
        painelCampo.setLayout(new BoxLayout(painelCampo, BoxLayout.Y_AXIS));
        painelCampo.setBackground(UITheme.PANEL_COLOR);
        painelCampo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.FONT_CORPO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        componente.setFont(UITheme.FONT_CORPO);
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, componente.getPreferredSize().height));

        painelCampo.add(lbl);
        painelCampo.add(Box.createVerticalStrut(5));
        painelCampo.add(componente);
        painelCampo.add(Box.createVerticalStrut(10));

        return painelCampo;
    }

    private void configurarEventos() {
        btnAdicionar.addActionListener(e -> adicionarJogador());
        btnEditar.addActionListener(e -> editarJogador());
        btnExcluir.addActionListener(e -> excluirJogador());
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
                int idJogador = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
                this.jogadorSelecionado = listaDeJogadoresAtual.stream()
                        .filter(j -> j.getId() == idJogador)
                        .findFirst()
                        .orElse(null);
            } else {
                this.jogadorSelecionado = null;
            }
        });
    }

    private void adicionarJogador() {
        String nome = txtNome.getText().trim();
        String posicao = (String) comboPosicao.getSelectedItem();
        String numeroTexto = txtNumero.getText().trim();
        String nomeTimeSelecionado = (String) comboTime.getSelectedItem();

        if (nome.isEmpty() || posicao == null || numeroTexto.isEmpty() || nomeTimeSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int numero = Integer.parseInt(numeroTexto);
            Integer timeId = timeNomesParaIds.get(nomeTimeSelecionado);
            if (jogadorController.criarJogador(nome, numero, posicao, timeId)) {
                limparFormularioJogador();
                atualizarTabela();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O número do jogador deve ser um valor inteiro!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- MÉTODO EDITARJOGADOR TOTALMENTE REESCRITO ---
    private void editarJogador() {
        if (this.jogadorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para editar!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialogoEdicao = new JDialog(this, "Editar Jogador", true);
        dialogoEdicao.setLayout(new BorderLayout(10, 10));

        // Painel principal do formulário com GridBagLayout
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(UITheme.PANEL_COLOR);
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cria e preenche os campos do formulário de edição
        JTextField nomeField = new JTextField(jogadorSelecionado.getNome());
        JComboBox<String> posicaoCombo = new JComboBox<>(
                new String[] { "Goleiro", "Zagueiro", "Lateral", "Meio Campo", "Atacante" });
        posicaoCombo.setSelectedItem(jogadorSelecionado.getPosicao());
        JTextField numeroField = new JTextField(String.valueOf(jogadorSelecionado.getNumero()));
        JComboBox<String> timeCombo = new JComboBox<>();
        DefaultComboBoxModel<String> timeModel = (DefaultComboBoxModel<String>) timeCombo.getModel();
        timeNomesParaIds.keySet().stream().sorted().forEach(timeModel::addElement);

        String nomeTimeAtual = timeNomesParaIds.entrySet().stream()
                .filter(entry -> entry.getValue().equals(jogadorSelecionado.getTimeId()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        timeCombo.setSelectedItem(nomeTimeAtual);

        // Adiciona os componentes ao painel com GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        painelFormulario.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Posição:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        painelFormulario.add(posicaoCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        painelFormulario.add(numeroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Time:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        painelFormulario.add(timeCombo, gbc);

        // Painel de botões do diálogo
        JPanel painelBotoesDialogo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoesDialogo.setBackground(UITheme.PANEL_COLOR);
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoesDialogo.add(btnSalvar);
        painelBotoesDialogo.add(btnCancelar);

        btnSalvar.addActionListener(e -> {
            String novoNome = nomeField.getText().trim();
            String novaPosicao = (String) posicaoCombo.getSelectedItem();
            String novoNumeroTexto = numeroField.getText().trim();
            String novoNomeTime = (String) timeCombo.getSelectedItem();

            if (novoNome.isEmpty() || novaPosicao == null || novoNumeroTexto.isEmpty() || novoNomeTime == null) {
                JOptionPane.showMessageDialog(dialogoEdicao, "Todos os campos são obrigatórios!", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int novoNumero = Integer.parseInt(novoNumeroTexto);
                Integer novoTimeId = timeNomesParaIds.get(novoNomeTime);

                if (jogadorController.atualizarJogador(jogadorSelecionado.getId(), novoNome, novoNumero, novaPosicao,
                        novoTimeId)) {
                    dialogoEdicao.dispose();
                    atualizarTabela();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogoEdicao, "O número deve ser um valor numérico válido.",
                        "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogoEdicao.dispose());

        dialogoEdicao.add(painelFormulario, BorderLayout.CENTER);
        dialogoEdicao.add(painelBotoesDialogo, BorderLayout.SOUTH);
        dialogoEdicao.pack(); // Ajusta o tamanho do diálogo ao conteúdo
        dialogoEdicao.setLocationRelativeTo(this);
        dialogoEdicao.setVisible(true);
    }

    private void excluirJogador() {
        if (jogadorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para excluir!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja deletar o jogador '" + jogadorSelecionado.getNome() + "'?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            if (jogadorController.deletarJogador(jogadorSelecionado.getId())) {
                atualizarTabela();
            }
        }
    }

    private void atualizarTabela() {
        this.listaDeJogadoresAtual = jogadorController.listarJogadoresComNomesDeTimes();

        modeloTabela.setRowCount(0);
        if (listaDeJogadoresAtual != null) {
            for (Jogador jogador : listaDeJogadoresAtual) {
                modeloTabela.addRow(new Object[] {
                        jogador.getId(),
                        jogador.getNome(),
                        jogador.getPosicao(),
                        jogador.getNumero(),
                        jogador.getNomeTime()
                });
            }
        }
    }

    private void limparFormularioJogador() {
        txtNome.setText("");
        txtNumero.setText("");
        comboPosicao.setSelectedIndex(0);
        if (comboTime.getItemCount() > 0) {
            comboTime.setSelectedIndex(0);
        }
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
    }
}