package com.anytrash;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main extends Component {
    private JPanel mainPanel;

    private JTextField encryptInputTextField;
    private JButton encryptInputButton;

    private JTextField encryptOutputTextField;
    private JButton encryptOutputButton;

    private JPasswordField encryptPasswordField;
    private JButton encryptButton;

    private JTextField decryptInputTextField;
    private JButton decryptInputButton;

    private JTextField decryptOutputTextField;
    private JButton decryptOutputButton;

    private JPasswordField decryptPasswordField;
    private JButton decryptButton;

    private final AesCrypt aesCrypt;

    public Main() {

        aesCrypt = new AesCrypt();

        encryptInputButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showOpenDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
                encryptInputTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        encryptOutputButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
                encryptOutputTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        encryptButton.addActionListener(e -> {
            String inputFilePath = encryptInputTextField.getText();
            String outputFilePath = encryptOutputTextField.getText();
            String password = String.valueOf(encryptPasswordField.getPassword());

            try {
                if (inputFilePath.isEmpty()) {
                    throw new IllegalArgumentException("Input file path is empty.");
                } else if (outputFilePath.isEmpty()) {
                    throw new IllegalArgumentException("Output file path is empty.");
                } else if (inputFilePath.equals(outputFilePath)) {
                    throw new IllegalArgumentException("Input and output files are the same.");
                } else if (password.isEmpty()) {
                    throw new IllegalArgumentException("Password is empty.");
                }

                aesCrypt.openFiles(inputFilePath, outputFilePath, password, AesCrypt.Mode.ENCRYPT);
                JOptionPane.showMessageDialog(Main.this, "Done.", "Encryption", JOptionPane.INFORMATION_MESSAGE);
            } catch (GeneralSecurityException exception) {
                JOptionPane.showMessageDialog(Main.this, exception.getMessage(), "Internal error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
                System.exit(-1);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(Main.this, exception.getMessage(), "Argument error", JOptionPane.WARNING_MESSAGE);
                exception.printStackTrace();
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(Main.this, exception.getMessage(), "Input/output error", JOptionPane.WARNING_MESSAGE);
                exception.printStackTrace();
            }
        });

        decryptInputButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showOpenDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
                decryptInputTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        decryptOutputButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
                decryptOutputTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        decryptButton.addActionListener(e -> {
            String inputFilePath = decryptInputTextField.getText();
            String outputFilePath = decryptOutputTextField.getText();
            String password = String.valueOf(decryptPasswordField.getPassword());

            try {
                if (inputFilePath.isEmpty()) {
                    throw new IllegalArgumentException("Input file path is empty.");
                } else if (outputFilePath.isEmpty()) {
                    throw new IllegalArgumentException("Output file path is empty.");
                } else if (inputFilePath.equals(outputFilePath)) {
                    throw new IllegalArgumentException("Input and output files are the same.");
                } else if (password.isEmpty()) {
                    throw new IllegalArgumentException("Password is empty.");
                }

                aesCrypt.openFiles(inputFilePath, outputFilePath, password, AesCrypt.Mode.DECRYPT);
                JOptionPane.showMessageDialog(Main.this, "Done.", "Decryption", JOptionPane.INFORMATION_MESSAGE);
            } catch (GeneralSecurityException exception) {
                JOptionPane.showMessageDialog(Main.this, exception.getMessage(), "Internal error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
                System.exit(-1);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(Main.this, exception.getMessage(), "Argument error", JOptionPane.WARNING_MESSAGE);
                exception.printStackTrace();
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(Main.this, exception.getMessage(), "Input/output error", JOptionPane.WARNING_MESSAGE);
                exception.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Internal error", JOptionPane.ERROR_MESSAGE);
        }

        JFrame mainFrame = new JFrame("AesCryptUtil");
        mainFrame.setContentPane(new Main().mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setMinimumSize(new Dimension(450, 300));
        mainFrame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(e -> System.exit(0));
        menuFile.add(menuItemExit);
        menuBar.add(menuFile);

        JMenu menuAbout = new JMenu("Help");
        JMenuItem menuItemAbout = new JMenuItem("About");
        menuItemAbout.addActionListener(e -> JOptionPane.showMessageDialog(null, getAbout(), "About", JOptionPane.INFORMATION_MESSAGE));
        menuAbout.add(menuItemAbout);
        menuBar.add(menuAbout);

        mainFrame.setJMenuBar(menuBar);

        mainFrame.setVisible(true);
    }

    private static String getAbout() {
        return "AesCryptUtil 1.0\n\n" +
                "Utility for encrypting files,\n" +
                "with the AES-256-CBC algorithm.\n\n" +
                "Created 16.05.2021.";
    }
}
