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
        textarea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        scrollPane = new RTextScrollPane(textarea);
    }

    public RSyntaxTextArea getTextArea() {
        return textarea;
    }

    public RTextScrollPane getScrollPane() {
        return scrollPane;
    }
}
