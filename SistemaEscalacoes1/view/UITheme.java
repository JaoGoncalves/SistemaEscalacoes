package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;
import com.formdev.flatlaf.FlatLightLaf;

public class UITheme {

    // Paleta de Cores Principal
    public static final Color PRIMARY_COLOR = new Color(0, 123, 255);
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    public static final Color PANEL_COLOR = new Color(255, 255, 255);
    public static final Color FONT_COLOR = new Color(33, 37, 41);
    public static final Color BORDER_COLOR = new Color(222, 226, 230);

    // Fontes
    public static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_CORPO = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOTAO = new Font("Segoe UI", Font.BOLD, 14);

    // Bordas e Espaçamento
    public static final Border BORDER_PANEL = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 15, 15, 15));
    public static final Insets PADDING = new Insets(10, 10, 10, 10);

    /**
     * Aplica o Look and Feel FlatLightLaf à aplicação.
     * Deve ser chamado no início do método main.
     */
    public static void aplicarTema() {
        try {
            FlatLightLaf.setup();
            // Configurações globais adicionais (opcional)
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback para o tema padrão do sistema se o FlatLaf falhar
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}