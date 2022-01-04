package net.asolidtime.jcenalauncher4j.downloader;

import java.io.File;
import java.util.ArrayList;

public class DownloadManager extends Thread {
    public Parser parser;
    public ArrayList<Aria2Downloader> downloadManagers = new ArrayList<>();
    public void addDownload(String magnet, File downloadDirectory, String name) {
        downloadManagers.add(new Aria2Downloader(magnet, downloadDirectory, name));
        downloadManagers.get(downloadManagers.size() - 1).start();
    }
    public DownloadStatus getDownloadStatus(int index) {
        return downloadManagers.get(index).downloadStatus;
    }
    public void search1337x(String query) {
        parser = new Parser(query);
    }
    public ArrayList<GameTorrentListing> getGameListings() {
        return parser.gameTorrentListings;
    }
}
