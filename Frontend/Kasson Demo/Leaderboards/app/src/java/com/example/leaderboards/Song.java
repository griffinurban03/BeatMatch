package com.example.leaderboards;

public class Song {
    private final long songID;
    private final String songTitle;
    private final int rank;

    // Constructor
    public Song(long songID, String songTitle, int rank) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.rank = rank;
    }

    // Getter methods
    public long getSongID() {
        return songID;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public int getRank() {
        return rank;
    }
}
