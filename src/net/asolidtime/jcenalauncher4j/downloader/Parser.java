package net.asolidtime.jcenalauncher4j.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public static String instanceUrl = "https://www.1377x.to"; // todo: switch this back to the real one once it's back up
    public ArrayList<GameTorrentListing> gameTorrentListings = new ArrayList<>();

    public Parser(String query) {
        try {
            gameTorrentListings = getGameListings(getSearchUrl(query, 1));
            for (GameTorrentListing listing : gameTorrentListings) {
                listing.magnet = fetchGameMagnet(listing);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(gameTorrentListings.toString());
    }
    public static ArrayList<GameTorrentListing> getGameListings(String query) throws IOException {
        ArrayList<GameTorrentListing> gameListings = new ArrayList<>();
        Document searchDoc = Jsoup.connect(query).get();
        Elements rows = searchDoc.getElementsByTag("tr");
        Elements links = rows.select("a[href]");
        for (int i = 1; i < links.size(); i += 3) {
            gameListings.add(new GameTorrentListing(links.get(i).text(), createTorrentUrl(links.get(i).attr("href"))));
        }
        return gameListings;
    }
    public static String getSearchUrl(String query, int page) {
        return instanceUrl + "/search/" + query + "/" + page + "/";
    }

    public static String createTorrentUrl(String query) {
        return instanceUrl + query;
    }

    public static String fetchGameMagnet(GameTorrentListing game) throws IOException {
        Elements links = Jsoup.connect(game.gameUrl).get().select("a[href]");
        for (Element link : links) {
           if ((link.attr("href")).startsWith("magnet:")) {
               return link.attr("href");
           }
        }
        throw new IOException("Couldn't find a magnet link on the specified page");
    }
    public static void main(String[] args) {
        //new ParserMain("beat cop johncena141");
        // alternative main method best testing framework
        ArrayList<Aria2Downloader> downloaders = new ArrayList<>();
        downloaders.add(new Aria2Downloader("magnet:?xt=urn:btih:C5EF26923AC2AAB26205B31A0432CA27A5EDD804&dn=RimWorld+-+1.3.3076+%5BMULTi23%5D+%5BGOG%5D+%5BGNU%2FLinux+Native%5D+%5Bjohncena141%5D&tr=udp%3A%2F%2Fexplodie.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.zer0day.to%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Fcoppersurfer.tk%3A6969%2Fannounce", new File("/home/maxwell/Documents/Programming/java/1337scrapetest/test-dls/"), "Rimworld"));
        downloaders.add(new Aria2Downloader("magnet:?xt=urn:btih:AE766178BC9800BCDF75C3CB22765D99F71607CE&dn=Beat+Cop+-+1.1.744+%5BMULTi12%5D+%5BGOG%5D+%5BGNU%2FLinux+Native%5D+%5Bjohncena141%5D&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.zer0day.to%3A1337%2Fannounce&tr=udp%3A%2F%2Feddie4.nl%3A6969%2Fannounce&tr=udp%3A%2F%2F46.148.18.250%3A2710&tr=udp%3A%2F%2Fopentor.org%3A2710&tr=http%3A%2F%2Ftracker.dler.org%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.me%3A2730%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2770%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=http%3A%2F%2Fretracker.local%2Fannounce&tr=http%3A%2F%2Fretracker.ip.ncnet.ru%2Fannounce&tr=udp%3A%2F%2Fexodus.desync.com%3A6969%2Fannounce&tr=udp%3A%2F%2Fipv4.tracker.harry.lu%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.zer0day.to%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Fcoppersurfer.tk%3A6969%2Fannounce", new File("/home/maxwell/Documents/Programming/java/1337scrapetest/test-dls/"), "Beat Cop"));
        for (Aria2Downloader downloader : downloaders) {
            downloader.start();
        }
        while(true) {
            for (Aria2Downloader downloader : downloaders) {
                System.out.println(downloader.downloadStatus.toString());
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
