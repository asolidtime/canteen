package net.asolidtime.jcenalauncher4j;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class AppConfig {
    public static void writeConfig() {
        try (OutputStream output = new FileOutputStream(System.getProperty("user.home") + "/.config/canteen.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("terminalRunCommand", AppMain.terminalRunCommand);
            prop.setProperty("gameDirLocation", AppMain.gameDirLocation.toString());
            prop.setProperty("theme", AppMain.theme);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    public static void readConfig() {
        try (InputStream input = new FileInputStream(System.getProperty("user.home") + "/.config/canteen.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            AppMain.terminalRunCommand = prop.getProperty("terminalRunCommand");
            AppMain.gameDirLocation = new File(prop.getProperty("gameDirLocation"));
            AppMain.theme = prop.getProperty("theme");

        } catch (IOException ex) {
            File createdConfig = new File(System.getProperty("user.home") + "/.config/canteen.properties");
            try {
                createdConfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Couldn't create the config file!");
            }
            try {
                OutputStream output = new FileOutputStream(createdConfig);
                Properties prop = new Properties();

                // set the properties value
                prop.setProperty("terminalRunCommand", "xterm %command%");
                prop.setProperty("gameDirLocation", System.getProperty("user.home") + "/Games/");
                prop.setProperty("theme", "Dark");
                AppMain.theme = "Dark";
                AppMain.gameDirLocation = new File(System.getProperty("user.home") + "/Games/");
                AppMain.terminalRunCommand = "xterm %command%";

                // save properties to project root folder
                prop.store(output, null);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Couldn't write to the config file!");
            }
        }
    }
}
