package net.asolidtime.jcenalauncher4j;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import net.asolidtime.jcenalauncher4j.downloader.DownloadManager;

import javax.swing.*;
import java.io.File;

public class AppMain {
    public static WindowMain windowMain;
    public static File gameDirLocation;
    public static String terminalRunCommand;
    public static String theme;
    public static DownloadManager downloadManager;

    public static void main(String[] args) {
        // use antialiasing
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        // use OpenGL-based hardware acceleration when available
        System.setProperty("sun.java2d.opengl", "true");
        // read config, creating if doesn't exist
        AppConfig.readConfig();
        // set theme (todo: do this by class name rather than string)
        try {
            switch (theme) {
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
        } catch (Exception ex){
            ex.printStackTrace();
        }
        downloadManager = new DownloadManager();
        // start main window
        windowMain = new WindowMain();
        windowMain.setup();
    }
}
