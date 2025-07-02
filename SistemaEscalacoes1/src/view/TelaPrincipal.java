package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private JButton btnTimes;
    private JButton btnJogadores;
    private JButton btnEscalacoes;
    private JButton btnSair;

    public TelaPrincipal() {
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
    }

    private void inicializarComponentes() {
        btnTimes = criarBotao("Gerenciar Times", "team.png");
        btnJogadores = criarBotao("Gerenciar Jogadores", "player.png");
        btnEscalacoes = criarBotao("Visualizar Escalações", "strategy.png");
        btnSair = criarBotao("Sair", "exit.png");
    }

    private JButton criarBotao(String texto, String nomeImagem) {
        JButton botao = new JButton(texto);
        botao.setUI(new BasicButtonUI()); // Remove visual padrão do Swing
        botao.setPreferredSize(new Dimension(240, 50));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setFocusPainted(false);
        botao.setBackground(new Color(70, 130, 180));
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Adiciona ícone
        try {
            ImageIcon icon = new ImageIcon("src/view/imagem/" + nomeImagem);
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            botao.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Erro ao carregar ícone: " + nomeImagem);
        }

        return botao;
    }

    private void configurarLayout() {
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(new Color(245, 250, 255));
        painelPrincipal.setBorder(new RoundedBorder(25)); // Adiciona borda arredondada

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("⚽ Sistema de Escalações de Futebol");
        titulo.setFont(new Font("Roboto", Font.BOLD, 22));
        titulo.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 0, 40, 0);
        painelPrincipal.add(titulo, gbc);

        gbc.insets = new Insets(15, 0, 15, 0);

        gbc.gridy = 1;
        painelPrincipal.add(btnTimes, gbc);

        gbc.gridy = 2;
        painelPrincipal.add(btnJogadores, gbc);

        gbc.gridy = 3;
        painelPrincipal.add(btnEscalacoes, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(30, 0, 20, 0);
        painelPrincipal.add(btnSair, gbc);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnTimes.addActionListener(e -> {
            new TelaGerenciarTimes().setVisible(true);
            dispose();
        });

        btnJogadores.addActionListener(e -> {
            new TelaGerenciarJogadores().setVisible(true);
            dispose();
        });

        btnEscalacoes.addActionListener(e -> {
            new TelaVisualizarEscalacoes().setVisible(true);
            dispose();
        });

        btnSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(
                    TelaPrincipal.this,
                    "Deseja realmente sair do sistema?",
                    "Confirmar Saída",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void configurarJanela() {
        setTitle("Sistema de Escalações");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignorar
        }

        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
