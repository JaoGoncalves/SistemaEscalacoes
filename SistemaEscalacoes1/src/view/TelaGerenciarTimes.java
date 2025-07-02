package view;

import controller.TimeController;
import model.Time;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public TelaGerenciarTimes() {
        this.timeController = new TimeController();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
        atualizarTabela();
    }

    private void inicializarComponentes() {
        txtNome = new JTextField(20);

        // Configurar tabela
        String[] colunas = { "ID", "Nome do Time" };
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
        JPanel painelSuperior = new JPanel(new FlowLayout());
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Cadastro de Time"));
        painelSuperior.add(new JLabel("Nome:"));
        painelSuperior.add(txtNome);
        painelSuperior.add(btnAdicionar);

        // Painel central - tabela
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Times Cadastrados"));

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
                adicionarTime();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarTime();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirTime();
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

    private void adicionarTime() {
        String nome = txtNome.getText().trim();
        if (timeController.criarTime(nome)) {
            txtNome.setText("");
            atualizarTabela();
        }
    }

    private void editarTime() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um time para editar!");
            return;
        }

        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

        String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome:", nomeAtual);
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            if (timeController.atualizarTime(id, novoNome)) {
                atualizarTabela();
            }
        }
    }

    private void excluirTime() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um time para excluir!");
            return;
        }

        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        if (timeController.deletarTime(id)) {
            atualizarTabela();
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Time> times = timeController.listarTimes();
        if (times != null) {
            for (Time time : times) {
                Object[] linha = { time.getId(), time.getNome() };
                modeloTabela.addRow(linha);
            }
        }
    }

    private void configurarJanela() {
        setTitle("Gerenciar Times");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
