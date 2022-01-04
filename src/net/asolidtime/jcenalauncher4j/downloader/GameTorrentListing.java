package net.asolidtime.jcenalauncher4j.downloader;

public class GameTorrentListing {
    public String nameUnformatted;
    public String gameUrl;
    public String gameDownloadType;
    public String magnet;
    public GameTorrentListing(String nameUnformatted, String gameUrl) {
        this.nameUnformatted = nameUnformatted;
        this.gameUrl = gameUrl;

        if (nameUnformatted.contains("[GNU/Linux Native]")) {
            gameDownloadType = "Native";
        } else if (nameUnformatted.contains("[GNU/Linux Wine]")) {
            gameDownloadType = "Wine";
        } else if (nameUnformatted.contains("[GNU/Linux Yuzu]")) {
            gameDownloadType = "Yuzu";
        }
    }
    public String toString() {
        return gameDownloadType + " game; name: " + nameUnformatted + "; url: " + gameUrl + "; magnet: " + magnet;
    }
}
