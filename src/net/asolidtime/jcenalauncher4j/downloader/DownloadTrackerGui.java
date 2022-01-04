package net.asolidtime.jcenalauncher4j.downloader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DownloadTrackerGui { // as painful as the intellij gui designer is, this is objectively worse.
    public JButton stopButton;
    public JProgressBar progressBar;
    public JLabel nameLabel;
    public JLabel progressLabel;
    public JLabel newlineLabel;
    private DownloadStatus downloadStatus;

    public DownloadTrackerGui(DownloadStatus downloadStatus, Container pane, Aria2Downloader downloader) {
        this.downloadStatus = downloadStatus;
        stopButton = new JButton("⏹️");
        progressBar = new JProgressBar(downloadStatus.progressPercentage);
        nameLabel = new JLabel(downloadStatus.name);
        progressLabel = new JLabel(downloadStatus.formattedProgress + " - " + downloadStatus.downloadSpeed + " - " + downloadStatus.seedCount + " connections");
        newlineLabel = new JLabel("\n");
        stopButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pane.add(newlineLabel);
        pane.add(nameLabel);
        pane.add(progressLabel);
        pane.add(progressBar);
        pane.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                downloader.stopAria2();
                downloader.downloadStatus.isStopped = true;
            }
        });
    }

    public void update() {
        if (!downloadStatus.isSeeding) {
            progressBar.setValue(downloadStatus.progressPercentage);
            progressLabel.setText(downloadStatus.formattedProgress + " - " + downloadStatus.downloadSpeed + " - " + downloadStatus.seedCount + " connections");
        } else {
            progressLabel.setText("Seeding");
        }
        if (downloadStatus.isStopped) {
            progressLabel.setText("Stopped");
        }
    }
}