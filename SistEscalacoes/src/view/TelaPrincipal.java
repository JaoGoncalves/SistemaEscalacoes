package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Escalações de Futebol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Configuração do layout principal
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setPreferredSize(new Dimension(800, 100));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("⚽ SISTEMA DE ESCALAÇÕES", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Menu principal
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Botões do menu
        JButton btnCadastrar = createMenuButton("CADASTRAR TIME", new Color(70, 130, 180));
        JButton btnConsultar = createMenuButton("CONSULTAR TIMES", new Color(60, 179, 113));
        JButton btnGerenciar = createMenuButton("GERENCIAR TIMES", new Color(255, 140, 0));
        JButton btnSair = createMenuButton("🚪 SAIR", new Color(220, 20, 60));

        // Posicionamento dos botões
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(btnCadastrar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        menuPanel.add(btnConsultar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        menuPanel.add(btnGerenciar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        menuPanel.add(btnSair, gbc);

        // Eventos dos botões
        btnCadastrar.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });

        btnConsultar.addActionListener(e -> {
            new TelaConsulta().setVisible(true);
            dispose();
        });

        btnGerenciar.addActionListener(e -> {
            new TelaGerenciamento().setVisible(true);
            dispose();
        });

        btnSair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente sair do sistema?",
                    "Confirmar Saída",
                    JOptionPane.YES_NO_OPTION
            );
            if (opcao == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(34, 139, 34));
        footerPanel.setPreferredSize(new Dimension(800, 50));
        JLabel footerLabel = new JLabel("Desenvolvido para Atividade Avaliativa II - Ciência da Computação", JLabel.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        // Adicionando componentes
        add(headerPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 80));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TelaPrincipal().setVisible(true);
        });
    }
}

