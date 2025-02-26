public class Song {
    private String artistName;
    private String trackName;
    private String playlistName;

    public Song(String artistName, String trackName, String playlistName) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.playlistName = playlistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    @Override
    public String toString() {
        return trackName + " by " + artistName;
    }
}
