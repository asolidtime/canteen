package net.asolidtime.jcenalauncher4j;

import net.asolidtime.jcenalauncher4j.game.GameListing;
import net.asolidtime.jcenalauncher4j.game.GameListingComparator;
import net.asolidtime.jcenalauncher4j.game.GameType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class WindowMain {
    private JFrame frame;
    private JList gameList;
    private JButton playButton;
    private JButton updateRuntimeButton;
    private JButton runInTerminalButton;
    private JPanel panelMain;
    private JLabel gameLabel;
    private JButton settingsButton;
    private SettingsWindow settingsWindow;
    private ArrayList<GameListing> gameListings;
    public WindowMain() {
        updateGameListings();

        gameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                updateToSelected();
            }
        });
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                    runCommand(gameListings.get(gameList.getSelectedIndex()).gameRunCommand);

            }
        });
        runInTerminalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runCommandInTerminal(gameListings.get(gameList.getSelectedIndex()).gameRunCommand);
            }
        });
        updateRuntimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runCommandInTerminal(gameListings.get(gameList.getSelectedIndex()).gameRuntimeUpdateCommand);
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                settingsWindow = new SettingsWindow();
                settingsWindow.setup();
            }
        });
        gameList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                updateToSelected();
            }
        });
    }

    private void updateToSelected() {
        GameListing gameListing = gameListings.get(gameList.getSelectedIndex());
        gameLabel.setText(gameListing.gameName);
        switch (gameListing.gameType) {
            case NATIVE:
                updateRuntimeButton.setText("Update jc141 runtime");
                playButton.setEnabled(true);
                runInTerminalButton.setEnabled(true);
                updateRuntimeButton.setEnabled(true);
                break;
            case WINE_GE:
                updateRuntimeButton.setText("Update Wine-GE");
                playButton.setEnabled(true);
                runInTerminalButton.setEnabled(true);
                updateRuntimeButton.setEnabled(true);
                break;
            case WINE_TKG:
                updateRuntimeButton.setText("Update Wine-TKG");
                playButton.setEnabled(true);
                runInTerminalButton.setEnabled(true);
                updateRuntimeButton.setEnabled(true);
                break;
            case SYSTEM_WINE:
                updateRuntimeButton.setText("(no update script)");
                playButton.setEnabled(true);
                runInTerminalButton.setEnabled(true);
                updateRuntimeButton.setEnabled(false);
                break;
            case NOT_A_GAME:
            default:
                updateRuntimeButton.setText("(not a game)");
                playButton.setEnabled(false);
                runInTerminalButton.setEnabled(false);
                updateRuntimeButton.setEnabled(false);
                break;
        }
    }

    public void setup() {
        frame = new JFrame("Canteen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panelMain);
        frame.setMinimumSize(new Dimension(420, 400));
        frame.pack();
        frame.setVisible(true);
    }
    private void runCommand(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void runCommandInTerminal(String command) {
        runCommand(AppMain.terminalRunCommand.replace("%command%", command));
    }
    private void createUIComponents() {
        gameList = new JList(new AbstractListModel() {
            @Override
            public int getSize() {
                return gameListings.size();
            }

            @Override
            public Object getElementAt(int i) {
                return gameListings.get(i).gameName;
            }
        });
    }
    public void updateTable() {
        gameList.repaint();
    }
    public void updateGameListings() {
        gameListings = new ArrayList<>();
        for (File gameDir : AppMain.gameDirLocation.listFiles()) {
            String gameName = gameDir.toString().substring(new String(AppMain.gameDirLocation.toString()).length() + 1) // take '/home/maxwell/Games/johncena141' from the game name
                    .replace('.', ' '); // replace '.'s with spaces
            boolean typeFound = false;
            boolean startShFound = false;
            if (!gameDir.isFile()) {
                for (File gameFile : gameDir.listFiles()) {
                    if (gameFile.isFile()) {
                        switch (gameFile.toString().substring(gameDir.toString().length() + 1)) {
                            case "update-johncena141-runtime.sh":
                                gameListings.add(new GameListing(gameDir, gameName, GameType.NATIVE, gameDir.toString() + "/start.sh", gameFile.toString()));
                                typeFound = true;
                                break;
                            case "update-wine-ge.sh":
                                gameListings.add(new GameListing(gameDir, gameName, GameType.WINE_GE, gameDir.toString() + "/start.sh", gameFile.toString()));
                                typeFound = true;
                                break;
                            case "update-wine-tkg.sh":
                                gameListings.add(new GameListing(gameDir, gameName, GameType.WINE_TKG, gameDir.toString() + "/start.sh", gameFile.toString()));
                                typeFound = true;
                                break;
                            case "start.sh":
                                startShFound = true;
                                break;
                        }
                    }
                }
                if (!typeFound && startShFound) {
                    gameListings.add(new GameListing(gameDir, gameName, GameType.SYSTEM_WINE, gameDir.toString() + "/start.sh", null));
                }

            }
        }
        gameListings.sort(new GameListingComparator());
        if (gameListings.isEmpty()) {
            gameListings.add(new GameListing(null, "(no johncena141 games found in " + AppMain.gameDirLocation.toString() + ")", GameType.NOT_A_GAME, null, null));
        }
    }
}
