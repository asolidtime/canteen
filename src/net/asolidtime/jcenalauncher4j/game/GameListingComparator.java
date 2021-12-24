package net.asolidtime.jcenalauncher4j.game;

import java.util.Comparator;

public class GameListingComparator implements Comparator<GameListing> {
    @Override
    public int compare(GameListing gameListing, GameListing t1) {
        return gameListing.gameName.compareTo(t1.gameName);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
