package net.asolidtime.jcenalauncher4j.downloader;

import java.io.*;
import java.util.Scanner;

public class Aria2Downloader extends Thread{
    public Process aria2cProcess = null;
    public DownloadStatus downloadStatus;
    private ProcessBuilder builder;
    public String name;
    public Aria2Downloader(String magnet, File downloadDirectory, String name) {
        this.name = name;
        downloadStatus = new DownloadStatus();
        downloadStatus.name = name;
        File pathToExecutable = new File("/usr/bin/aria2c");
        builder = new ProcessBuilder( pathToExecutable.getAbsolutePath(), "-d", downloadDirectory.toString(), "--download-result=full", magnet, "--console-log-level=info");
        builder.directory(downloadDirectory);
        builder.redirectErrorStream(true);
    }
    private void updateDownloadStatus(String aria2Output) {
        // todo: this assumes torrent isn't seeding, otherwise some of this would cause an indexoutofboundsexception
        // trycatchtrytrycatchcatchtrycatchtratch
        // ugh.
        // should've just tried to use the json rpc, but it's done now ¯\_(ツ)_/¯
        // System.out.println(aria2Output);
        String[] parts = aria2Output.split(" ");
        if (parts[1].startsWith("SEED") || downloadStatus.isSeeding == true) {
            downloadStatus.isSeeding = true;
        } else {
            downloadStatus.formattedProgress = parts[1];
            try {
                downloadStatus.eta = parts[5].substring(4, parts[5].length() - 1);
            } catch (IndexOutOfBoundsException ex) {
                downloadStatus.eta = "unknown";
            }
            String dlstatus = downloadStatus.formattedProgress;
            try {
                downloadStatus.progressPercentage = Integer.parseInt(dlstatus.substring(dlstatus.length() - 5, dlstatus.length() - 2));
            } catch (NumberFormatException ex) {
                try {
                    downloadStatus.progressPercentage = Integer.parseInt(dlstatus.substring(dlstatus.length() - 4, dlstatus.length() - 2));
                } catch (NumberFormatException exx) {
                    try {
                        downloadStatus.progressPercentage = Integer.parseInt(dlstatus.substring(dlstatus.length() - 3, dlstatus.length() - 2));
                    } catch (NumberFormatException sexxxy) {
                        downloadStatus.progressPercentage = 0;
                    }
                }
            }
            try {
                downloadStatus.downloadSpeed = parts[4].substring(3) + "/s";
                if (downloadStatus.downloadSpeed.equals("0B]/s")) {
                    downloadStatus.downloadSpeed = "unknown";
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                downloadStatus.downloadSpeed = "unknown";
            }
            downloadStatus.seedCount = Integer.parseInt(parts[2].substring(3));
        }
    }
    public void stopAria2() {
        aria2cProcess.destroy();
    }
    public void run() {
        try {
            aria2cProcess = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
            Scanner s = new Scanner(aria2cProcess.getInputStream());
            while (s.hasNextLine()) {
                String a = s.nextLine();
                if (a.startsWith("[#")) {
                    updateDownloadStatus(a);
                    // System.out.println(downloadStatus);
                }
            }
            s.close();

    }

}
