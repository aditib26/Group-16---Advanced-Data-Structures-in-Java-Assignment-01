import java.io.*;
import java.util.*;

public class DatasetLoader {
    public static List<Song> loadSongs(String filename) {
        List<Song> allSongs = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 3) {
                    String artist = values[0].replace("\"", "").trim();
                    String track = values[1].replace("\"", "").trim();
                    String playlist = values[2].replace("\"", "").trim();
                    allSongs.add(new Song(artist, track, playlist));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allSongs;
    }
}
