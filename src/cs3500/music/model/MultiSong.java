package cs3500.music.model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class for playing multiple songs on top of one another.
 */
public class MultiSong extends Song {

  // list of all the songs to be combined
  protected ArrayList<Song> songs;
  private int totalBeatNum = 0;

  /**
   * constructor.
   *
   * @param songs list of songs to be used
   */
  public MultiSong(ArrayList<Song> songs) {

    // initialize this.songs
    this.songs = songs;
    for (Song t : this.songs) {
      for (Map.Entry<String, Note> entry : t.notes.entrySet()) {
        t.addNote(entry.getValue(), t.getBeatNum(entry.getKey()));
        totalBeatNum += t.getBeatNum(entry.getKey());
      }
    }
  }

  /**
   * This method determines if the addition of the given note is valid for this song.
   *
   * @param note Note being added
   * @param beatIndex index of the beat within the measure
   * @return String representing the validity of the addition ("Valid Add" = acceptable add)
   */

  public String validNoteChange(Note note, int beatIndex) {

    // beatNum is not a valid beat index, throw exception
    if (beatIndex < 0) {
      return "Beat Out of Bounds";
    }

    // the change is valid
    return "Valid Change";
  }

  /**
   * Adds a note to the specific song within this MultiSong.
   *
   * @param note note to be added
   * @param beatIndex index of the note to be added
   * @param song Song note to be added to
   */
  public void addNoteInMulti(Note note, int beatIndex, Song song)
      throws IllegalArgumentException {
    song.addNote(note, beatIndex);
  }

  /**
   * This method gets the notes field of the MultiSong and returns the list of songs as a copy of
   * the field.
   *
   * @return list of this MultiSong's songs
   */
  public ArrayList<Song> getSongs() {
    ArrayList<Song> arr = new ArrayList<Song>();
    for (Song t : songs) {
      Song song = new Song();
      song.notes = song.getNotes();
      arr.add(song);
    }
    return arr;
  }
}
