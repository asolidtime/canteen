package net.asolidtime.jcenalauncher4j;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SettingsWindow {
    private JTextField gameDirField;
    private JComboBox themeBox;
    private JTextField runCommand;
    private JButton saveAndCloseButton;
    private JPanel settingsPanel;
    private JFrame frame;

    public SettingsWindow() {
        gameDirField.setText(AppMain.gameDirLocation.toString());
        runCommand.setText(AppMain.terminalRunCommand);
        switch(AppMain.theme) {
            case "Dark":
                themeBox.setSelectedIndex(0);
                break;
            case "Light":
                themeBox.setSelectedIndex(1);
                break;
            case "GTK":
                themeBox.setSelectedIndex(2);
                break;
            case "ArcDark":
                themeBox.setSelectedIndex(3);
                break;
            case "Metal":
                themeBox.setSelectedIndex(4);
        }
        saveAndCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveConfig();
                frame.dispose();
            }
        });
        themeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    switch (themeBox.getItemAt(themeBox.getSelectedIndex()).toString()) {
                        case "GTK":
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                            break;
                        case "Dark":
                            UIManager.setLookAndFeel(new FlatDarkLaf());
                            break;
                        case "Light":
                            UIManager.setLookAndFeel(new FlatLightLaf());
                            break;
                        case "ArcDark":
                            UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
                            break;
                        case "Metal":
                            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Window w : Window.getWindows()) {
                    SwingUtilities.updateComponentTreeUI(w);
                }
            }
        });
    }

    public void setup() {
        frame = new JFrame("Canteen Settings");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(settingsPanel);
        frame.setMinimumSize(new Dimension(420, 400));
        frame.pack();
        frame.setVisible(true);
    }

    private void saveConfig() {
        AppMain.gameDirLocation = new File(gameDirField.getText());
        AppMain.terminalRunCommand = runCommand.getText();
        AppMain.theme = themeBox.getItemAt(themeBox.getSelectedIndex()).toString();
        AppConfig.writeConfig();
        AppMain.windowMain.updateGameListings();
        AppMain.windowMain.updateTable();
    }
}
