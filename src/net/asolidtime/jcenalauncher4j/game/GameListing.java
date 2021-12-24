package net.asolidtime.jcenalauncher4j.game;

import java.io.File;

public class GameListing {
    public File gamePath;
    public String gameName;
    public GameType gameType;
    public String gameRuntimeUpdateCommand;
    public String gameRunCommand;
    public GameListing(File gamePath, String gameName, GameType gameType, String gameRunCommand, String gameRuntimeUpdateCommand) {
        this.gamePath = gamePath;
        this.gameName = gameName;
        this.gameType = gameType;
        this.gameRunCommand = gameRunCommand;
        this.gameRuntimeUpdateCommand = gameRuntimeUpdateCommand;
    }
}
