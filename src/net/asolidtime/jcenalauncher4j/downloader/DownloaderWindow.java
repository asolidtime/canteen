package net.asolidtime.jcenalauncher4j.downloader;

import net.asolidtime.jcenalauncher4j.AppMain;
import net.asolidtime.jcenalauncher4j.SearchWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DownloaderWindow {
    private JFrame frame;
    private JPanel panelMain;
    private JScrollPane scrollPane;
    private JButton addNewButton;
    private ArrayList<DownloadTrackerGui> downloadTrackerGuis = new ArrayList<>();
    private SearchWindow searchWindow;
    private void setupPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        addNewButton = new JButton("Add new...");
        addNewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        pane.add(addNewButton);
    }
    public void setup() {
        frame = new JFrame("Canteen Downloads");
        panelMain = new JPanel();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panelMain);
        setupPane(frame.getContentPane());
        frame.setMinimumSize(new Dimension(420, 400));
        frame.pack();
        frame.setVisible(true);
        Runnable rescanRunnable = new Runnable() {
            public void run() {
                rescan();
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(rescanRunnable, 0, 500, TimeUnit.MILLISECONDS);
        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchWindow = new SearchWindow();
                searchWindow.setup();
            }
        });
        // test
        //AppMain.downloadManager.addDownload("magnet:?xt=urn:btih:C5EF26923AC2AAB26205B31A0432CA27A5EDD804&dn=RimWorld+-+1.3.3076+%5BMULTi23%5D+%5BGOG%5D+%5BGNU%2FLinux+Native%5D+%5Bjohncena141%5D&tr=udp%3A%2F%2Fexplodie.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.zer0day.to%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Fcoppersurfer.tk%3A6969%2Fannounce", new File("/home/maxwell/Documents/Programming/java/1337scrapetest/test-dls/"), "Rimworld");
    }
    public void rescan() {
        if (AppMain.downloadManager.downloadManagers.size() > downloadTrackerGuis.size()) {
            int index = AppMain.downloadManager.downloadManagers.size();
            downloadTrackerGuis.add(new DownloadTrackerGui(AppMain.downloadManager.getDownloadStatus(index - 1), frame.getContentPane(), AppMain.downloadManager.downloadManagers.get(index - 1)));
        }
        for (DownloadTrackerGui downloadTrackerGui : downloadTrackerGuis) {
            downloadTrackerGui.update();
        }
    }
}
