package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        super("Sistema de Escalações de Futebol");
        UITheme.aplicarTema();
        inicializarComponentes();
        configurarJanela();
    }

    private void inicializarComponentes() {
        // Painel de fundo
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(UITheme.BACKGROUND_COLOR);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Escalações", SwingConstants.CENTER);
        lblTitulo.setFont(UITheme.FONT_TITULO);
        lblTitulo.setForeground(UITheme.FONT_COLOR);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        backgroundPanel.add(lblTitulo, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 0, 15));
        menuPanel.setBackground(UITheme.BACKGROUND_COLOR);

        // Adicionar botões
        menuPanel.add(criarBotaoMenu("Gerenciar Times", "team.png", e -> abrirTela(new TelaGerenciarTimes())));
        menuPanel
                .add(criarBotaoMenu("Gerenciar Jogadores", "player.png", e -> abrirTela(new TelaGerenciarJogadores())));
        menuPanel.add(criarBotaoMenu("Visualizar Escalações", "strategy.png",
                e -> abrirTela(new TelaVisualizarEscalacoes())));
        menuPanel.add(criarBotaoMenu("Sair do Sistema", "exit.png", e -> confirmarSaida()));

        backgroundPanel.add(menuPanel, BorderLayout.CENTER);
        add(backgroundPanel);
    }

    private JButton criarBotaoMenu(String texto, String nomeIcone, ActionListener action) {
        JButton botao = new JButton(texto);
        botao.setFont(UITheme.FONT_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setBackground(UITheme.PRIMARY_COLOR);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(250, 50));
        botao.addActionListener(action);

        try {

            java.net.URL iconURL = getClass().getResource("imagem/" + nomeIcone);

            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                botao.setIcon(new ImageIcon(img));
            } else {
                // Mensagem de erro se o ícone não for encontrado
                System.err.println("Arquivo de ícone não encontrado: " + nomeIcone);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícone: " + nomeIcone);
            e.printStackTrace();
        }

        return botao;
    }

    private void abrirTela(JFrame novaTela) {
        novaTela.setVisible(true);
        dispose();
    }

    private void confirmarSaida() {
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair do sistema?",
                "Confirmar Saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}