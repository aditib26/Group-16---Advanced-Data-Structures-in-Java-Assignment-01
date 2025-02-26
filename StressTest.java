
import java.util.*;
import java.io.*;

public class StressTest {

    // Global variables to store times for each test
    private static long addSongsTime;
    private static long prioritizeSongsTime;
    private static long shufflePlaylistTime;
    private static long printCurrentAndNextSongsTime;
    private static long skipSongsTime;

    public static void main(String[] args) {
        // Load songs from dataset
        List<Song> allSongs = DatasetLoader.loadSongs("spotify_playlists_20000_songs.csv");

        // Extract unique playlist names
        List<String> playlistNames = new ArrayList<>();
        for (Song song : allSongs) {
            if (!playlistNames.contains(song.getPlaylistName())) {
                playlistNames.add(song.getPlaylistName());
            }
        }

        if (playlistNames.isEmpty()) {
            System.out.println("No playlists found. Exiting...");
            return;
        }

        // Simulate stress testing for each playlist
        for (String playlistName : playlistNames) {
            System.out.println("\nStarting stress test for playlist: " + playlistName);

            List<Song> playlistSongs = new ArrayList<>();
            for (Song song : allSongs) {
                if (song.getPlaylistName().equalsIgnoreCase(playlistName)) {
                    playlistSongs.add(song);
                }
            }

            PlaylistManager playlistManager = new PlaylistManager(playlistSongs); 


            testAddingMultipleSongs(playlistManager);
            testPrioritizingSongs(playlistManager);
            testShufflingPlaylist(playlistManager);
            testPrintingCurrentAndNextSongs(playlistManager);
            testSkippingSongs(playlistManager);
            printTimeSummary();
        }
    }

    public static void testAddingMultipleSongs(PlaylistManager playlistManager) {
        System.out.println("\nTesting add 10,000 songs...");

        long startTime = System.nanoTime();

        for (int i = 0; i < 10000; i++) { 
            Song newSong = new Song("Artist " + (i + 1), "Track " + (i + 1), "Test Playlist");
            playlistManager.addSongToPlaylist(newSong);
        }

        long endTime = System.nanoTime();
        addSongsTime = endTime - startTime; 
        System.out.println("Time to add 10,000 songs: " + (addSongsTime / 1_000_000) + " ms");
    }

    public static void testPrioritizingSongs(PlaylistManager playlistManager) {
        System.out.println("\nTesting prioritize 100 songs...");

        long startTime = System.nanoTime();

        Random rand = new Random();
        List<Song> songs = new ArrayList<>(playlistManager.getPlaylist()); 

        for (int i = 0; i < 100; i++) {
            Song songToPrioritize = songs.get(rand.nextInt(songs.size()));
            playlistManager.prioritizeSong(songToPrioritize.getTrackName(), 10); 
        }

        long endTime = System.nanoTime();
        prioritizeSongsTime = endTime - startTime; 
        System.out.println("Time to prioritize 100 songs: " + (prioritizeSongsTime / 1_000_000) + " ms");
    }

    public static void testShufflingPlaylist(PlaylistManager playlistManager) {
        System.out.println("\nTesting shuffle playlist 100 times...");

        long startTime = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            playlistManager.shufflePlaylist();
        }

        long endTime = System.nanoTime();
        shufflePlaylistTime = endTime - startTime; 
        System.out.println("Time to shuffle 100 times: " + (shufflePlaylistTime / 1_000_000) + " ms");
    }

    public static void testPrintingCurrentAndNextSongs(PlaylistManager playlistManager) {
        System.out.println("\nTesting printing current song and next 5 songs 100 times randomly...");

        long startTime = System.nanoTime();

        Random rand = new Random();
        List<Song> songs = new ArrayList<>(playlistManager.getPlaylist()); 
        for (int i = 0; i < 100; i++) {
            int startIdx = rand.nextInt(songs.size() - 6); 
            Song currentSong = songs.get(startIdx);
            List<Song> nextSongs = songs.subList(startIdx + 1, startIdx + 6);

            System.out.println("Current Song: " + currentSong.getTrackName());
            System.out.println("Next 5 Songs: ");
            for (Song nextSong : nextSongs) {
                System.out.println(" - " + nextSong.getTrackName());
            }
        }

        long endTime = System.nanoTime();
        printCurrentAndNextSongsTime = endTime - startTime; 
        System.out.println("Time to print current song and next 5 songs 100 times: " + (printCurrentAndNextSongsTime / 1_000_000) + " ms");
    }

    public static void testSkippingSongs(PlaylistManager playlistManager) {
        System.out.println("\nTesting skip songs 500 times...");

        long startTime = System.nanoTime();

        for (int i = 0; i < 500; i++) { 
            playlistManager.skipSong();
        }

        long endTime = System.nanoTime();
        skipSongsTime = endTime - startTime; 
        System.out.println("Time to skip 500 songs: " + (skipSongsTime / 1_000_000) + " ms");
    }

    public static void printTimeSummary() {
        System.out.println("\nTest Summary for the Playlist:");
        System.out.println("Time to add 10,000 songs: " + (addSongsTime / 1_000_000) + " ms");
        System.out.println("Time to prioritize 100 songs: " + (prioritizeSongsTime / 1_000_000) + " ms");
        System.out.println("Time to shuffle playlist 100 times: " + (shufflePlaylistTime / 1_000_000) + " ms");
        System.out.println("Time to print current song and next 5 songs 100 times: " + (printCurrentAndNextSongsTime / 1_000_000) + " ms");
        System.out.println("Time to skip 500 songs: " + (skipSongsTime / 1_000_000) + " ms");
    }
}
