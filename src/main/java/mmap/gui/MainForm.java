package mmap.gui;

import mmap.converter.Converter;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class MainForm extends JFrame {
    private JTextField fileTextField;
    private JButton browseButton;
    private JButton convertButton;
    private JPanel mainPanel;
    private JLabel fileLabel;

    public MainForm() {
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFile.getAbsolutePath());
            }
        });
        convertButton.addActionListener(e -> {
            Converter converter = new Converter();
            try {
                converter.convert(new File(fileTextField.getText()));
            } catch (IOException | JAXBException e1) {
                e1.printStackTrace();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
