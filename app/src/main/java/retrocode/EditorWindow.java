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
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;

public class EditorWindow {
    private JFrame frame;
    private RetroTextArea editor;
    private FolderExplorerPanel folderPanel;

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

        // Remove title bar - toolbar will be at the top
        // JPanel titleBar = createTitleBar();
        // mainPanel.add(titleBar, BorderLayout.NORTH);

        // Create a panel for the main content area
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        JPanel toolbar = createToolbar();
        contentPanel.add(toolbar, BorderLayout.NORTH);

        editor = new RetroTextArea();
        contentPanel.add(editor.getScrollPane(), BorderLayout.CENTER);

        // Folder panel setup
        folderPanel = new FolderExplorerPanel();
        folderPanel.setFileOpenListener(file -> {
            try (java.io.FileReader reader = new java.io.FileReader(file)) {
                editor.getTextArea().read(reader, null);
                frame.setTitle("RetroCode - " + file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to open file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Create a collapsible panel container
        JPanel folderPanelContainer = new JPanel(new BorderLayout()) {
            private boolean isCollapsed = false;
            private int originalWidth = 200;
            
            {
                // Add collapse button to the folder panel
                folderPanel.getCollapseButton().addActionListener(e -> {
                    isCollapsed = !isCollapsed;
                    if (isCollapsed) {
                        setPreferredSize(new Dimension(40, 600));
                        folderPanel.setVisible(false);
                    } else {
                        setPreferredSize(new Dimension(originalWidth, 600));
                        folderPanel.setVisible(true);
                    }
                    revalidate();
                    repaint();
                });
            }
        };
        folderPanelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        folderPanelContainer.add(folderPanel, BorderLayout.CENTER);
        contentPanel.add(folderPanelContainer, BorderLayout.WEST);
        folderPanelContainer.setPreferredSize(new Dimension(200, 600));

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        // Remove the old menu bar
        // frame.setJMenuBar(FileManager.buildMenu(frame, editor, folderPanel));
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                java.awt.GradientPaint gp = new java.awt.GradientPaint(0, 0, new Color(50, 55, 70), getWidth(), getHeight(), new Color(30, 32, 40));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        toolbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 6));
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        toolbar.setPreferredSize(new Dimension(0, 36));

        JButton fileButton = createToolbarButton("File");
        JButton openFolderButton = createToolbarButton("Open Folder");

        fileButton.addActionListener(e -> {
            JPopupMenu fileMenu = new JPopupMenu();
            JMenuItem newItem = new JMenuItem("New");
            JMenuItem openItem = new JMenuItem("Open");
            JMenuItem saveItem = new JMenuItem("Save");
            JMenuItem saveAsItem = new JMenuItem("Save As");
            
            newItem.addActionListener(evt -> FileManager.newFile(frame, editor.getTextArea()));
            openItem.addActionListener(evt -> FileManager.openFile(frame, editor.getTextArea()));
            saveItem.addActionListener(evt -> FileManager.saveFile(frame, editor.getTextArea()));
            saveAsItem.addActionListener(evt -> FileManager.saveFileAs(frame, editor.getTextArea()));
            
            fileMenu.add(newItem);
            fileMenu.add(openItem);
            fileMenu.add(saveItem);
            fileMenu.add(saveAsItem);
            fileMenu.show(fileButton, 0, fileButton.getHeight());
        });

        openFolderButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File folder = chooser.getSelectedFile();
                // Update folder panel
                folderPanel.setRootFolder(folder);
            }
        });

        toolbar.add(fileButton);
        toolbar.add(openFolderButton);
        
        return toolbar;
    }

    private JButton createToolbarButton(String text) {
        JButton button = new JButton(text) {
            @Override
            public void paintComponent(java.awt.Graphics g) {
                if (getModel().isRollover()) {
                    setBackground(new Color(70, 80, 100));
                } else {
                    setBackground(new Color(45, 50, 60));
                }
                super.paintComponent(g);
            }
        };
        button.setFont(new java.awt.Font("JetBrains Mono", java.awt.Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 80, 100));
                button.setOpaque(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(45, 50, 60));
                button.setOpaque(true);
            }
        });
        return button;
    }
}
