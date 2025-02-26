import java.util.*;
import java.io.*;

public class StressTest1 {

    // Global variables to store times for each test
    private static long addSongTime;
    private static long prioritizeSongTime;
    private static long shufflePlaylistTime;
    private static long printCurrentAndNextSongsTime;
    private static long skipSongTime;

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

            // Test single operations
            testAddingSingleSong(playlistManager);
            testPrioritizingSingleSong(playlistManager);
            testShufflingPlaylistOnce(playlistManager);
            testPrintingCurrentAndNextSongsOnce(playlistManager);
            testSkippingSingleSong(playlistManager);
            printTimeSummary();
        }
    }

    public static void testAddingSingleSong(PlaylistManager playlistManager) {
        System.out.println("\nTesting adding a single song...");

        long startTime = System.nanoTime();

        // Add a single song
        Song newSong = new Song("Test Artist", "Test Track", "Test Playlist");
        playlistManager.addSongToPlaylist(newSong);

        long endTime = System.nanoTime();
        addSongTime = endTime - startTime;
        System.out.println("Time to add a single song: " + (addSongTime / 1_000_000) + " ms");
    }

    public static void testPrioritizingSingleSong(PlaylistManager playlistManager) {
        System.out.println("\nTesting prioritizing a single song...");

        long startTime = System.nanoTime();

        // Prioritize a single song
        Random rand = new Random();
        List<Song> songs = new ArrayList<>(playlistManager.getPlaylist());
        if (!songs.isEmpty()) {
            Song songToPrioritize = songs.get(rand.nextInt(songs.size()));
            playlistManager.prioritizeSong(songToPrioritize.getTrackName(), 10);
        }

        long endTime = System.nanoTime();
        prioritizeSongTime = endTime - startTime;
        System.out.println("Time to prioritize a single song: " + (prioritizeSongTime / 1_000_000) + " ms");
    }

    public static void testShufflingPlaylistOnce(PlaylistManager playlistManager) {
        System.out.println("\nTesting shuffling the playlist once...");

        long startTime = System.nanoTime();

        // Shuffle the playlist once
        playlistManager.shufflePlaylist();

        long endTime = System.nanoTime();
        shufflePlaylistTime = endTime - startTime;
        System.out.println("Time to shuffle the playlist once: " + (shufflePlaylistTime / 1_000_000) + " ms");
    }

    public static void testPrintingCurrentAndNextSongsOnce(PlaylistManager playlistManager) {
        System.out.println("\nTesting printing current song and next 5 songs once...");

        long startTime = System.nanoTime();

        // Print current and next 5 songs once
        List<Song> songs = new ArrayList<>(playlistManager.getPlaylist());
        if (songs.size() >= 6) {
            Song currentSong = songs.get(0);
            List<Song> nextSongs = songs.subList(1, 6);

            System.out.println("Current Song: " + currentSong.getTrackName());
            System.out.println("Next 5 Songs: ");
            for (Song nextSong : nextSongs) {
                System.out.println(" - " + nextSong.getTrackName());
            }
        }

        long endTime = System.nanoTime();
        printCurrentAndNextSongsTime = endTime - startTime;
        System.out.println("Time to print current song and next 5 songs once: " + (printCurrentAndNextSongsTime / 1_000_000) + " ms");
    }

    public static void testSkippingSingleSong(PlaylistManager playlistManager) {
        System.out.println("\nTesting skipping a single song...");

        long startTime = System.nanoTime();

        // Skip a single song
        playlistManager.skipSong();

        long endTime = System.nanoTime();
        skipSongTime = endTime - startTime;
        System.out.println("Time to skip a single song: " + (skipSongTime / 1_000_000) + " ms");
    }

    public static void printTimeSummary() {
        System.out.println("\nTest Summary for the Playlist:");
        System.out.println("Time to add a single song: " + (addSongTime / 1_000) + " µs");
        System.out.println("Time to prioritize a single song: " + (prioritizeSongTime / 1_000) + " µs");
        System.out.println("Time to shuffle the playlist once: " + (shufflePlaylistTime / 1_000) + " µs");
        System.out.println("Time to print current song and next 5 songs once: " + (printCurrentAndNextSongsTime / 1_000) + " µs");
        System.out.println("Time to skip a single song: " + (skipSongTime / 1_000) + " µs");
    }
}