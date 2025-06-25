package view;

import classes.*;
import dao.TimeDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelaCadastro extends JFrame {
    private JTextField txtNomeTime;
    private JTextField txtNomeJogador;
    private JTextField txtNumeroJogador;
    private JComboBox<String> cbPosicao;
    private JTable tabelaJogadores;
    private DefaultTableModel modeloTabela;
    private Time timeAtual;
    private TimeDAO timeDAO;

    public TelaCadastro() {
        timeDAO = new TimeDAO();
        timeAtual = new Time();
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastrar Nova Escalação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(900, 80));
        JLabel headerLabel = new JLabel("📝 CADASTRAR NOVA ESCALAÇÃO", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de informações do time
        JPanel timePanel = createTimePanel();

        // Painel de cadastro de jogadores
        JPanel jogadorPanel = createJogadorPanel();

        // Painel da tabela
        JPanel tabelaPanel = createTabelaPanel();

        // Painel de botões
        JPanel buttonPanel = createButtonPanel();

        // Layout principal
        mainPanel.add(timePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(jogadorPanel, BorderLayout.NORTH);
        centerPanel.add(tabelaPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTimePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Informações do Time"));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("Nome do Time:"));
        txtNomeTime = new JTextField(20);
        txtNomeTime.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(txtNomeTime);

        return panel;
    }

    private JPanel createJogadorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Adicionar Jogador"));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome do jogador
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNomeJogador = new JTextField(15);
        txtNomeJogador.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(txtNomeJogador, gbc);

        // Número do jogador
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 3;
        txtNumeroJogador = new JTextField(8);
        txtNumeroJogador.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(txtNumeroJogador, gbc);

        // Posição
        gbc.gridx = 4; gbc.gridy = 0;
        panel.add(new JLabel("Posição:"), gbc);
        gbc.gridx = 5;
        cbPosicao = new JComboBox<>(new String[]{
                "Goleiro", "Zagueiro", "Lateral", "Meio Campo", "Atacante"
        });
        cbPosicao.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(cbPosicao, gbc);

        // Botão adicionar
        gbc.gridx = 6; gbc.gridy = 0;
        JButton btnAdicionar = new JButton("➕ Adicionar");
        btnAdicionar.setBackground(new Color(60, 179, 113));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setBorderPainted(false);
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.addActionListener(this::adicionarJogador);
        panel.add(btnAdicionar, gbc);

        return panel;
    }

    private JPanel createTabelaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Escalação Atual"));

        String[] colunas = {"Nome", "Número", "Posição"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaJogadores = new JTable(modeloTabela);
        tabelaJogadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaJogadores.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaJogadores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaJogadores.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabelaJogadores);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botão remover jogador selecionado
        JButton btnRemover = new JButton("🗑️ Remover Selecionado");
        btnRemover.setBackground(new Color(220, 20, 60));
        btnRemover.setForeground(Color.WHITE);
        btnRemover.setBorderPainted(false);
        btnRemover.setFocusPainted(false);
        btnRemover.addActionListener(this::removerJogador);

        JPanel buttonRemovePanel = new JPanel(new FlowLayout());
        buttonRemovePanel.add(btnRemover);
        panel.add(buttonRemovePanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnSalvar = new JButton("💾 SALVAR ESCALAÇÃO");
        btnSalvar.setPreferredSize(new Dimension(200, 40));
        btnSalvar.setBackground(new Color(34, 139, 34));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setBorderPainted(false);
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(this::salvarEscalacao);

        JButton btnLimpar = new JButton("🔄 LIMPAR TUDO");
        btnLimpar.setPreferredSize(new Dimension(150, 40));
        btnLimpar.setBackground(new Color(255, 140, 0));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpar.setBorderPainted(false);
        btnLimpar.setFocusPainted(false);
        btnLimpar.addActionListener(this::limparTudo);

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

        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnVoltar);

        return panel;
    }

    private void adicionarJogador(ActionEvent e) {
        try {
            String nome = txtNomeJogador.getText().trim();
            String numeroText = txtNumeroJogador.getText().trim();
            String posicao = (String) cbPosicao.getSelectedItem();

            if (nome.isEmpty() || numeroText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Preencha todos os campos!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numero = Integer.parseInt(numeroText);

            if (numero < 1 || numero > 99) {
                throw new NumeroInvalidoException("Número deve estar entre 1 e 99!");
            }

            // Verifica se o número já existe
            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                if ((Integer) modeloTabela.getValueAt(i, 1) == numero) {
                    JOptionPane.showMessageDialog(this,
                            "Número " + numero + " já está em uso!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Cria o jogador baseado na posição
            Jogador jogador = criarJogador(nome, numero, posicao);

            if (jogador != null) {
                timeAtual.escalarJogador(jogador);
                modeloTabela.addRow(new Object[]{nome, numero, posicao});

                // Limpa os campos
                txtNomeJogador.setText("");
                txtNumeroJogador.setText("");
                cbPosicao.setSelectedIndex(0);

                // Atualiza título com contador
                atualizarTitulo();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Número inválido!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumeroInvalidoException | ExceptionEscalacao ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerJogador(ActionEvent e) {
        int selectedRow = tabelaJogadores.getSelectedRow();
        if (selectedRow >= 0) {
            int numero = (Integer) modeloTabela.getValueAt(selectedRow, 1);
            try {
                timeAtual.removerJogador(numero);
                modeloTabela.removeRow(selectedRow);
                atualizarTitulo();
            } catch (ExceptionEscalacao ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecione um jogador para remover!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salvarEscalacao(ActionEvent e) {
        try {
            String nomeTime = txtNomeTime.getText().trim();
            if (nomeTime.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Digite o nome do time!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (modeloTabela.getRowCount() != 11) {
                JOptionPane.showMessageDialog(this,
                        "A escalação deve ter exatamente 11 jogadores!\nAtualmente: " + modeloTabela.getRowCount(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            timeAtual.setNome(nomeTime);
            timeDAO.salvarTime(timeAtual);

            JOptionPane.showMessageDialog(this,
                    "Escalação salva com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            limparTudo(null);

        } catch (SQLException ex) {
            String mensagem = ex.getMessage().contains("UNIQUE constraint failed") ?
                    "Já existe um time com este nome!" :
                    "Erro ao salvar no banco de dados: " + ex.getMessage();

            JOptionPane.showMessageDialog(this,
                    mensagem,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparTudo(ActionEvent e) {
        txtNomeTime.setText("");
        txtNomeJogador.setText("");
        txtNumeroJogador.setText("");
        cbPosicao.setSelectedIndex(0);
        modeloTabela.setRowCount(0);
        timeAtual = new Time();
        atualizarTitulo();
    }

    private void atualizarTitulo() {
        int count = modeloTabela.getRowCount();
        setTitle("Cadastrar Nova Escalação - Jogadores: " + count + "/11");
    }

    private Jogador criarJogador(String nome, int numero, String posicao) {
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