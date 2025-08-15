package retrocode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

public class EditorWindow {
    private JFrame frame;
    private RetroTextArea editor;

    public void showEditor() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("Could not load dark theme.");
        }

        frame = new JFrame("RetroCode - Untitled");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel titleBar = createTitleBar();
        mainPanel.add(titleBar, BorderLayout.NORTH);

        editor = new RetroTextArea();
        mainPanel.add(editor.getScrollPane(), BorderLayout.CENTER);

        // Folder panel setup
        FolderExplorerPanel folderPanel = new FolderExplorerPanel();
        folderPanel.setFileOpenListener(file -> {
            try (java.io.FileReader reader = new java.io.FileReader(file)) {
                editor.getTextArea().read(reader, null);
                frame.setTitle("RetroCode - " + file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to open file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JPanel folderPanelContainer = new JPanel(new BorderLayout());
        folderPanelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        folderPanelContainer.add(folderPanel, BorderLayout.CENTER);
        mainPanel.add(folderPanelContainer, BorderLayout.WEST);
        folderPanel.setPreferredSize(new Dimension(200, 600));

        frame.setJMenuBar(FileManager.buildMenu(frame, editor, folderPanel));
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createTitleBar() {
        JPanel bar = new JPanel();
        bar.setLayout(new BorderLayout());
        bar.setBackground(new Color(45, 45, 45));
        bar.setPreferredSize(new Dimension(0, 30));
        JLabel titleLabel = new JLabel("RetroCode");
        titleLabel.setForeground(Color.WHITE);
        bar.add(titleLabel, BorderLayout.WEST);
        return bar;
    }

    // Remove showEditorWithFolderPanel (now merged into showEditor)
}
