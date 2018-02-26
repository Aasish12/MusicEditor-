package cs3500.music.model;

import java.util.ArrayList;

/**
 * Interface that holds all of the methods for a Song.
 */
public interface ISong {

  /**
   * This method adds the given Note to the song at the given beat within the given measure.
   * Throws exception if there is already a note there.
   *
   * @param note Note to be added to the song
   * @param beatIndex index of the beat the Note will be added to within the measure
   */
  public void addNote(Note note, int beatIndex) throws IllegalArgumentException;

  /**
   * This method deletes the given Note from the song at the given beat within the given measure.
   * Throws exception if there is no note at the given location
   *
   * @param note Note to be deleted
   * @param beatNum index of beat of Note within the Song
   */
  public void deleteNote(Note note, int beatNum) throws IllegalArgumentException;

  /**
   * This method removes the given note (if present) from its current position and moves it
   * to the given position. Does nothing if there is no Note at the given location.
   *
   * @param note Note to be moved
   * @param beatNum original beat index of the Note within its measure
   * @param destBeatNum ultimate beat index of the Note within its measure
   */
  public void editNote(Note note, int beatNum, int destBeatNum) throws IllegalArgumentException;

  /**
   * Returns an ArrayList of all the notes on the given beat.
   *
   * @param beatNum index of the beat within this song
   * @return ArrayList of all the Notes on the given beat
   */
  public ArrayList<Note> notesInBeat(int beatNum) throws IllegalArgumentException;

  public void setCurrentBeat(int beat);

  public int getCurrentBeat();

  public int getNumBeats();
}
