package net.asolidtime.jcenalauncher4j;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class SearchWindow {
    private JTextField searchField;
    private JButton searchButton;
    private JPanel panelMain;
    private JScrollPane tableScrollPane;
    private JTable listingTable;
    private AbstractTableModel listingTableModel;
    private final File downloadLocation = new File(AppMain.gameDirLocation.toString() + "/Canteen.Downloads"); // BIG TODO: make this configurable
    public SearchWindow() {
        downloadLocation.mkdirs();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!searchField.getText().equals("")) {
                    AppMain.downloadManager.search1337x(searchField.getText());
                    while (AppMain.downloadManager.parser.gameTorrentListings.isEmpty()) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception ignored) {}
                    }
                    listingTableModel.fireTableDataChanged();
                }
            }
        });


        listingTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {     // run on double click
                    JTable target = (JTable)me.getSource();
                    int index = target.getSelectedRow(); // select a row
                    String gameMagnet = AppMain.downloadManager.parser.gameTorrentListings.get(index).magnet;
                    String gameName = AppMain.downloadManager.parser.gameTorrentListings.get(index).nameUnformatted;
                    AppMain.downloadManager.addDownload(gameMagnet, downloadLocation, gameName);
                }
            }
        });

    }

    public void setup() {
        JFrame frame = new JFrame("Search 1337x...");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panelMain);
        frame.setMinimumSize(new Dimension(420, 400));
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        listingTableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return AppMain.downloadManager.parser != null && !AppMain.downloadManager.parser.gameTorrentListings.isEmpty() ? AppMain.downloadManager.parser.gameTorrentListings.size() : 0;
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int i, int i1) {
                return i1 == 0 ? AppMain.downloadManager.parser.gameTorrentListings.get(i).nameUnformatted : AppMain.downloadManager.parser.gameTorrentListings.get(i).gameDownloadType;
            }
        };
        listingTable = new JTable(listingTableModel);
        listingTable.getColumnModel().getColumn(0).setHeaderValue("Name");
        listingTable.getColumnModel().getColumn(1).setHeaderValue("Type");
    }
}
