package retrocode;

import java.awt.Font;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class RetroTextArea {
    private RSyntaxTextArea textarea;
    private RTextScrollPane scrollPane;

    public RetroTextArea() {
        textarea = new RSyntaxTextArea(20, 60);
        textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT);
        textarea.setCodeFoldingEnabled(true);
        textarea.setFont(new Font("JetBrains Mono", Font.PLAIN, 15));
        textarea.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16));
        textarea.setBackground(new java.awt.Color(36, 40, 48));
        textarea.setForeground(new java.awt.Color(220, 220, 220));
        textarea.setCaretColor(new java.awt.Color(130, 180, 255));
        textarea.setSelectionColor(new java.awt.Color(60, 90, 140, 120));
        textarea.setSelectedTextColor(new java.awt.Color(255,255,255));
        // Rounded corners for scroll pane
        scrollPane = new RTextScrollPane(textarea) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                g2d.setColor(new java.awt.Color(30, 34, 42, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            }
        };
        scrollPane.setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
    }

    public RSyntaxTextArea getTextArea() {
        return textarea;
    }

    public RTextScrollPane getScrollPane() {
        return scrollPane;
    }
}
