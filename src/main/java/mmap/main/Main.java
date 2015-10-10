package mmap.main;


import mmap.gui.MainForm;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        createForm();
    }

    private static void createForm() {
        JFrame frame = new JFrame("Main");
        MainForm main = new MainForm();
        frame.setContentPane(main.getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
