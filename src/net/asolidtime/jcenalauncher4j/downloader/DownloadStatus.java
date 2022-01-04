package net.asolidtime.jcenalauncher4j.downloader;

public class DownloadStatus {
    public int seedCount;
    public String downloadSpeed;
    public int progressPercentage;
    public String eta;
    public String formattedProgress;
    public String name;
    public boolean isSeeding = false;
    public boolean isStopped = false;
    public String toString() {
        if (!isSeeding) {
            return "name " + name + " seeds " + seedCount + " speed " + downloadSpeed + " percentage " + progressPercentage + " eta " + eta + " formatted progress " + formattedProgress;
        } else {
            return "seeding torrent " + name;
        }
    }
}
