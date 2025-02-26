// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PlaylistManager {
   private Deque<Song> playlist;
   private Stack<Song> history;
   private Song currentlyPlaying;

   public PlaylistManager(List<Song> var1) {
      this.resetPlaylist(var1);
   }

   public void resetPlaylist(List<Song> var1) {
      this.playlist = new LinkedList(var1);
      this.history = new Stack();
      this.currentlyPlaying = null;
   }

   public void playNextSong() {
      if (!this.playlist.isEmpty()) {
         if (this.currentlyPlaying != null) {
            this.history.push(this.currentlyPlaying);
         }

         this.currentlyPlaying = (Song)this.playlist.poll();
         System.out.println("Now Playing: " + this.currentlyPlaying);
      } else {
         System.out.println("No more songs in the playlist.");
      }

   }

   public void skipSong() {
      if (!this.playlist.isEmpty()) {
         Song var1 = (Song)this.playlist.poll();
         this.playlist.offer(var1);
         System.out.println("Skipped: " + var1);
      }

   }

   public void prioritizeSong(String var1, int var2) {
      ArrayList var3 = new ArrayList(this.playlist);
      Song var4 = null;
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         Song var6 = (Song)var5.next();
         if (var6.getTrackName().equalsIgnoreCase(var1)) {
            var4 = var6;
            break;
         }
      }

      if (var4 != null) {
         this.playlist.remove(var4);
         var2 = Math.max(0, Math.min(var2, this.playlist.size()));
         ((LinkedList)this.playlist).add(var2, var4);
         System.out.println("Moved " + var4 + " to position " + (var2 + 1));
      } else {
         System.out.println("Song not found.");
      }

   }

   public void shufflePlaylist() {
      ArrayList var1 = new ArrayList(this.playlist);
      Collections.shuffle(var1);
      this.playlist.clear();
      this.playlist.addAll(var1);
      System.out.println("Playlist shuffled!");
   }

   public void displayCurrentlyPlaying() {
      if (this.currentlyPlaying != null) {
         System.out.println("Currently Playing: " + this.currentlyPlaying);
      } else {
         System.out.println("No song is currently playing.");
      }

      System.out.println("\nUp Next (Next 5 songs):");
      Iterator var1 = this.playlist.iterator();

      for(int var2 = 0; var1.hasNext() && var2 < 5; ++var2) {
         System.out.println(var2 + 1 + ". " + var1.next());
      }

   }

   public void displayPlaylist() {
      if (this.playlist.isEmpty()) {
         System.out.println("Playlist is empty.");
      } else {
         System.out.println("Current Playlist:");
         int var1 = 1;
         Iterator var2 = this.playlist.iterator();

         while(var2.hasNext()) {
            Song var3 = (Song)var2.next();
            int var10001 = var1++;
            System.out.println("" + var10001 + ". " + var3);
         }
      }

   }

   public void addSongToPlaylist(Song var1) {
      this.playlist.offer(var1);
      PrintStream var10000 = System.out;
      String var10001 = var1.getTrackName();
      var10000.println("Added new song: " + var10001 + " by " + var1.getArtistName());
   }

   public Deque<Song> getPlaylist() {
      return this.playlist;
   }
}
