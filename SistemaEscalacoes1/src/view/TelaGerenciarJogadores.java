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

public class TelaGerenciarJogadores extends JFrame {
    private JogadorController jogadorController;
    private TimeController timeController;
    private JTextField txtNome;
    private JTextField txtNumero;
    private JComboBox<String> cbPosicao;
    private JComboBox<Time> cbTime;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnVoltar;

    public TelaGerenciarJogadores() {
        this.jogadorController = new JogadorController();
        this.timeController = new TimeController();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
        carregarTimes();
        atualizarTabela();
    }

    private void inicializarComponentes() {
        txtNome = new JTextField(15);
        txtNumero = new JTextField(5);
        cbPosicao = new JComboBox<>(jogadorController.getPosicoes());
        cbTime = new JComboBox<>();

        // Configurar tabela
        String[] colunas = { "ID", "Nome", "Número", "Posição", "Time" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnVoltar = new JButton("Voltar");
    }

    private void configurarLayout() {
        setLayout(new BorderLayout());

        // Painel superior - formulário
        JPanel painelSuperior = new JPanel(new GridBagLayout());
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Cadastro de Jogador"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Linha 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelSuperior.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painelSuperior.add(txtNome, gbc);
        gbc.gridx = 2;
        painelSuperior.add(new JLabel("Número:"), gbc);
        gbc.gridx = 3;
        painelSuperior.add(txtNumero, gbc);

        // Linha 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelSuperior.add(new JLabel("Posição:"), gbc);
        gbc.gridx = 1;
        painelSuperior.add(cbPosicao, gbc);
        gbc.gridx = 2;
        painelSuperior.add(new JLabel("Time:"), gbc);
        gbc.gridx = 3;
        painelSuperior.add(cbTime, gbc);

        // Linha 3 - botão
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        painelSuperior.add(btnAdicionar, gbc);

        // Painel central - tabela
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Jogadores Cadastrados"));

        // Painel inferior - botões
        JPanel painelInferior = new JPanel(new FlowLayout());
        painelInferior.add(btnEditar);
        painelInferior.add(btnExcluir);
        painelInferior.add(btnVoltar);

        add(painelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarJogador();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarJogador();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirJogador();
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

    private void adicionarJogador() {
        try {
            String nome = txtNome.getText().trim();
            int numero = Integer.parseInt(txtNumero.getText().trim());
            String posicao = (String) cbPosicao.getSelectedItem();
            Time time = (Time) cbTime.getSelectedItem();

            if (time == null) {
                JOptionPane.showMessageDialog(this, "Selecione um time!");
                return;
            }

            if (jogadorController.criarJogador(nome, numero, posicao, time.getId())) {
                limparCampos();
                atualizarTabela();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!");
        }
    }

    private void editarJogador() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para editar!");
            return;
        }

        // Criar diálogo de edição
        JDialog dialogoEdicao = new JDialog(this, "Editar Jogador", true);
        dialogoEdicao.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Obter dados atuais
        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        int numeroAtual = (Integer) modeloTabela.getValueAt(linhaSelecionada, 2);
        String posicaoAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 3);

        // Componentes do diálogo
        JTextField txtNomeEdit = new JTextField(nomeAtual, 15);
        JTextField txtNumeroEdit = new JTextField(String.valueOf(numeroAtual), 5);
        JComboBox<String> cbPosicaoEdit = new JComboBox<>(jogadorController.getPosicoes());
        cbPosicaoEdit.setSelectedItem(posicaoAtual);
        JComboBox<Time> cbTimeEdit = new JComboBox<>();

        // Carregar times no combo
        List<Time> times = timeController.listarTimes();
        if (times != null) {
            for (Time time : times) {
                cbTimeEdit.addItem(time);
            }
        }

        // Layout do diálogo
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogoEdicao.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        dialogoEdicao.add(txtNomeEdit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogoEdicao.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        dialogoEdicao.add(txtNumeroEdit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialogoEdicao.add(new JLabel("Posição:"), gbc);
        gbc.gridx = 1;
        dialogoEdicao.add(cbPosicaoEdit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        dialogoEdicao.add(new JLabel("Time:"), gbc);
        gbc.gridx = 1;
        dialogoEdicao.add(cbTimeEdit, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialogoEdicao.add(painelBotoes, gbc);

        // Eventos dos botões
        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNomeEdit.getText().trim();
                int numero = Integer.parseInt(txtNumeroEdit.getText().trim());
                String posicao = (String) cbPosicaoEdit.getSelectedItem();
                Time time = (Time) cbTimeEdit.getSelectedItem();

                if (time != null && jogadorController.atualizarJogador(id, nome, numero, posicao, time.getId())) {
                    atualizarTabela();
                    dialogoEdicao.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogoEdicao, "Digite um número válido!");
            }
        });

        btnCancelar.addActionListener(e -> dialogoEdicao.dispose());

        dialogoEdicao.pack();
        dialogoEdicao.setLocationRelativeTo(this);
        dialogoEdicao.setVisible(true);
    }

    private void excluirJogador() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para excluir!");
            return;
        }

        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        if (jogadorController.deletarJogador(id)) {
            atualizarTabela();
        }
    }

    private void carregarTimes() {
        cbTime.removeAllItems();
        List<Time> times = timeController.listarTimes();
        if (times != null) {
            for (Time time : times) {
                cbTime.addItem(time);
            }
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Jogador> jogadores = jogadorController.listarJogadores();
        if (jogadores != null) {
            List<Time> times = timeController.listarTimes();
            for (Jogador jogador : jogadores) {
                String nomeTime = "Time não encontrado";
                if (times != null) {
                    for (Time time : times) {
                        if (time.getId() == jogador.getTimeId()) {
                            nomeTime = time.getNome();
                            break;
                        }
                    }
                }
                Object[] linha = { jogador.getId(), jogador.getNome(), jogador.getNumero(),
                        jogador.getPosicao(), nomeTime };
                modeloTabela.addRow(linha);
            }
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtNumero.setText("");
        cbPosicao.setSelectedIndex(0);
        if (cbTime.getItemCount() > 0) {
            cbTime.setSelectedIndex(0);
        }
    }

    private void configurarJanela() {
        setTitle("Gerenciar Jogadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}
