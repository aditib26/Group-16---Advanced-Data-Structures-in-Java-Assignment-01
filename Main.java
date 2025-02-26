import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load all songs
        List<Song> allSongs = DatasetLoader.loadSongs("spotify_data.csv");

        if (allSongs.isEmpty()) {
            System.out.println("No songs found. Exiting...");
            return;
        }

        // Extract unique playlist names
        List<String> playlistNames = new ArrayList<>();
        for (Song song : allSongs) {
            if (!playlistNames.contains(song.getPlaylistName())) {
                playlistNames.add(song.getPlaylistName());
            }
        }

        // Main loop
        while (true) {
        
            System.out.println("\nAvailable Playlists:");
            for (int i = 0; i < playlistNames.size(); i++) {
                System.out.println((i + 1) + ". " + playlistNames.get(i));
            }

            // User selects a playlist or exits
            System.out.print("\nEnter playlist number (or 0 to exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 0) {
                System.out.println("Exiting... Enjoy your music!");
                break; 
            }

            if (choice < 1 || choice > playlistNames.size()) {
                System.out.println("Invalid choice, try again.");
                continue;
            }

            String selectedPlaylist = playlistNames.get(choice - 1);
            System.out.println("Selected Playlist: " + selectedPlaylist);

            List<Song> playlistSongs = new ArrayList<>();
            for (Song song : allSongs) {
                if (song.getPlaylistName().equalsIgnoreCase(selectedPlaylist)) {
                    playlistSongs.add(song);
                }
            }

            PlaylistManager playlistManager = new PlaylistManager(playlistSongs);

            // start the playlist manager options
            while (true) {
                System.out.println("\nOptions:");
                System.out.println("1. Play next song");
                System.out.println("2. Skip song");
                System.out.println("3. Prioritize a song at a specific position");
                System.out.println("4. Shuffle playlist");
                System.out.println("5. Show playlist");
                System.out.println("6. Show currently playing song and next 5 songs");
                System.out.println("7. Add a new song to this playlist");
                System.out.println("8. Return to playlist selection");
                System.out.println("9. Exit");

                System.out.print("Enter choice: ");
                int action = scanner.nextInt();
                scanner.nextLine(); 

                switch (action) {
                    case 1:
                        playlistManager.playNextSong();
                        break;
                    case 2:
                        playlistManager.skipSong();
                        break;
                    case 3:
                        System.out.print("Enter track name to prioritize: ");
                        String track = scanner.nextLine();
                        System.out.print("Enter new position (1-based index): ");
                        int position = scanner.nextInt() - 1;
                        scanner.nextLine(); 
                        playlistManager.prioritizeSong(track, position);
                        break;
                    case 4:
                        playlistManager.shufflePlaylist();
                        break;
                    case 5:
                        playlistManager.displayPlaylist();
                        break;
                    case 6:
                        playlistManager.displayCurrentlyPlaying();
                        break;
                    case 7:
                        System.out.print("Enter song name: ");
                        String songName = scanner.nextLine();
                        System.out.print("Enter artist name: ");
                        String artistName = scanner.nextLine();
                        Song newSong = new Song(artistName, songName, selectedPlaylist);
                        playlistManager.addSongToPlaylist(newSong);
                    break;
                    case 8:
                        System.out.println("Returning to playlist selection...");
                        break;
                    case 9:
                        System.out.println("Exiting... Enjoy your music!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice, try again.");
                }

                if (action == 8) {
                    break; 
                }
            }
        }
    }
}
