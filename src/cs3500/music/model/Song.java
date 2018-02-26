package cs3500.music.model;

import cs3500.music.model.Note.Pitch;
import cs3500.music.view.ITrackBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a single song, built from a number of measures.
 */
// 6/14 moved showTrack() method to TextView class, now called initialize()
public class Song implements ISong {

  // list of Measures in this Song
  protected HashMap<String, Note> notes;
  // number of beats in this song
  protected int numBeats;
  // beat that the song is currently playing
  protected int currentBeat;
  // tempo of the song
  protected int tempo;
  // 6/18 added for controller
  // input for the model to take action on
  protected String input;

  /**
   * constructor.
   */
  public Song() {
    this.notes = new HashMap<String, Note>();
    this.numBeats = 0;
    this.currentBeat = 0;
    this.tempo = 0;
    this.input = "";
  }

  public int getTempo() {
    return this.tempo;
  }

  /**
   * This method determines the key of the given note at the given beat.
   * Example: 3rd beat, 2nd octave, F# --> "3 2 F#"
   *
   * @param beatNum beat index of the Note
   * @param note Note of the key that is returned
   * @return String representation of the given Note's key
   */
  private String noteKey(int beatNum, Note note) {
    return String.format("%d %d %s", beatNum, note.octave, note.pitch.toString());
  }

  /**
   * Determines if there is a note at the given location.
   *
   * @param note Note at the given location
   * @param beatNum index of the Note's beat within its measure
   * @return boolean representing if the given Note
   */
  private boolean notePresent(Note note, int beatNum) {

    // is the first beat of the note there?
    boolean isBegin = this.notes.containsKey(noteKey(beatNum, note));

    // is the duration of the note there?
    boolean isDuration = this.notes.containsKey(noteKey(beatNum, note) + "-");

    // is there is a note?
    return isBegin || isDuration;
  }

  /**
   * This method determines if the addition of the given note is valid for this song.
   *
   * @param note Note being added
   * @param beatIndex index of the beat within the measure
   * @return String representing the validity of the addition ("Valid Add" = acceptable add)
   */
  // 6/11 changed to public
  public String validNoteChange(Note note, int beatIndex) {

    // beatNum is not a valid beat index, throw exception
    if (beatIndex < 0) {
      return "Beat Out of Bounds";
    }

    // the change is valid
    return "Valid Change";
  }

  /**
   * This method adds the given Note to the song at the given beat within the given measure.
   * Throws exception if there is already a note there.
   *
   * @param note Note to be added to the song
   * @param beatIndex index of the beat the Note will be added to within the measure
   */
  public void addNote(Note note, int beatIndex) throws IllegalArgumentException {

    // can the note be added?
    String valid = this.validNoteChange(note, beatIndex);

    // if no, throw exception
    if (!valid.equals("Valid Change")) {
      throw new IllegalArgumentException(valid);
    }

    // add Note to the Song
    this.notes.put(this.noteKey(beatIndex, note), note);

    // if the note is longer than one beat, add more beats (durations)
    for (int i = 1; i < note.beats; i++) {
      this.notes.put(this.noteKey(beatIndex + i, note) + " -", note);
    }
    if (numBeats < beatIndex + note.beats) {
      numBeats = beatIndex + note.beats;
    }
  }

  /**
   * This method deletes the given Note from the song at the given beat within the given measure.
   * Throws exception if there is no note at the given location
   *
   * @param note Note to be deleted
   * @param beatNum index of beat of Note within the Song
   */
  public void deleteNote(Note note, int beatNum) throws IllegalArgumentException {

    // key of the beginning of a note at the given location
    String keyStart = this.noteKey(beatNum, note);

    // key of the duration of a note at the given location
    String keyDuration = this.noteKey(beatNum, note) + " -";

    // key of the duration of a note after the given location
    String keyAfter = this.noteKey(beatNum + 1, note) + " -";

    // if the key is equal to key
    if (this.notes.containsKey(keyStart)) {

      // remove it
      this.notes.remove(keyStart);

      // if theres a duration of the same note after
      boolean durationAfter = this.notes.containsKey(keyAfter);
      if (durationAfter) {
        // rerun recur
        this.deleteNote(note, beatNum + 1);
      }
    }

    // if the key is equal to key2, remove it
    else if (this.notes.containsKey(keyDuration)) {
      this.notes.remove(keyDuration);

      // if there's a duration after, recur
      boolean durationAfter = this.notes.containsKey(keyAfter);
      if (durationAfter) {
        this.deleteNote(note, beatNum + 1);
      }

      // if there is a note before, recur //
      boolean durationBefore = this.notes
          .containsKey(this.noteKey(beatNum - 1, note) + " -");
      boolean startBefore = this.notes.containsKey(this.noteKey(beatNum - 1, note));
      if (durationBefore || startBefore) {
        this.deleteNote(note, beatNum - 1);
      }
    }

    // no note to delete, throw exception
    else {
      throw new IllegalArgumentException("No Note Present");
    }
  }

  /**
   * This method removes the given note (if present) from its current position and moves it
   * to the given position. Does nothing if there is no Note at the given location.
   *
   * @param note Note to be moved
   * @param beatNum original beat index of the Note within its measure
   * @param destBeatNum ultimate beat index of the Note within its measure
   */
  public void editNote(Note note, int beatNum, int destBeatNum) throws IllegalArgumentException {

    // string showing if the note change is valid
    String valid = this.validNoteChange(note, destBeatNum);

    // the note change is not valid, throw exception
    if (!valid.equals("Valid Change")) {
      throw new IllegalArgumentException(valid);
    }

    // the note is present
    if (notes.containsKey(this.noteKey(beatNum, note))) {
      // move the removed note to its new location
      this.notes
          .put(this.noteKey(destBeatNum, note),
              this.notes.remove(this.noteKey(beatNum, note)));

      // can't move a note, throw exception
    } else {
      throw new IllegalArgumentException("No Start of a Note Present");
    }
  }

  /**
   * This method determines the lowest and highest notes on the song.
   *
   * @return ArrayList of lowest and highest note on the song's octave and pitch ordinal
   */
  public ArrayList<Integer> noteRange() {

    // if there are no notes
    if (this.notes.isEmpty()) {
      return new ArrayList<Integer>();
    }

    // initialize lowest and highest notes
    Note oneEntry = this.notes.entrySet().iterator().next().getValue();
    Note lowest = oneEntry;
    Note highest = oneEntry;

    // there's one note present, the range is itself
    if (this.notes.size() == 1) {
      return new ArrayList<Integer>(Arrays.asList(oneEntry.octave, oneEntry.pitch.ordinal(),
          oneEntry.octave, oneEntry.pitch.ordinal()));
    }

    // find the notes
    for (Map.Entry<String, Note> entry : this.notes.entrySet()) {
      String key = entry.getKey();

      // initialize octave index
      int oct = this.getOctNum(key);
      // initialize pitch index
      int numPitch = getNumPitch(key);

      if (oct < lowest.octave) {
        lowest = entry.getValue();
        continue;
      } else if (oct == lowest.octave) {
        if (numPitch < lowest.pitch.ordinal()) {
          lowest = entry.getValue();
          continue;
        }
      }
      if (oct > highest.octave) {
        highest = entry.getValue();
        continue;
      } else if (oct == highest.octave) {
        if (numPitch > highest.pitch.ordinal()) {
          highest = entry.getValue();
        }
      }
    }

    // return lowest and highest notes in an arraylist
    return new ArrayList<Integer>(
        Arrays.asList(lowest.octave, lowest.pitch.ordinal(), highest.octave,
            highest.pitch.ordinal()));
  }

  /**
   * This method extracts the octave index of the given entry.
   */
  private int getOctNum(String next) {
    return Integer.parseInt(parseString(next, 1));
  }

  /**
   * Returns the beat index of the given note's key.
   *
   * @param next String representing the note's key
   * @return beat index of the note
   */
  public int getBeatNum(String next) {
    return Integer.parseInt(parseString(next, 0));
  }

  /**
   * Parses the key of the Note and returns the given indexed String.
   *
   * @param s String to be parsed
   * @param i index of the String needed
   * @return integer of the given index
   */
  private String parseString(String s, int i) {
    return (s.split(" ")[i]);
  }

  /**
   * Returns the pitch's ordinal of the given note's key.
   *
   * @param name String representing the note's key
   * @return pitch ordinal of the note
   */
  private int getNumPitch(String name) {
    return Pitch.reverseLookup(parseString(name, 2)).ordinal();
  }

  /**
   * Returns an ArrayList of all the notes on the given beat.
   *
   * @param beatNum index of the beat within this song
   * @return ArrayList of all the Notes on the given beat
   */
  // 6/11, updates so that method returns copy
  public ArrayList<Note> notesInBeat(int beatNum) throws IllegalArgumentException {

    // beatNum is out of bounds, throw exception
    if (beatNum > this.numBeats) {
      throw new IllegalArgumentException("beatNum Out of Bounds");
    }

    // calculate list
    ArrayList<Note> arr = new ArrayList<Note>();
    for (Map.Entry<String, Note> entry : this.notes.entrySet()) {
      // if note is on beatNum, add it
      if (this.getBeatNum(entry.getKey()) == beatNum) {
        Note note = entry.getValue();
        arr.add(new Note(note.pitch, note.octave, note.beats, note.instrument, note.volume));
      }
    }
    // return list
    return arr;
  }

  /**
   * This method gets a copy of the HashMap of notes.
   *
   * @return copy of Hashmap of Notes (notes)
   */
  // 6/11 added getter for notes hashmap
  public HashMap<String, Note> getNotes() {
    HashMap<String, Note> hash = new HashMap<String, Note>();
    for (Map.Entry<String, Note> entry : this.notes.entrySet()) {
      Note note = entry.getValue();
      hash.put(entry.getKey(), new Note(note.pitch, note.octave, note.beats, note.instrument,
          note.volume));
    }
    return hash;
  }

  /**
   * This methods gets the number of beats in the Song.
   *
   * @return number of beats in the Song.
   */
  public int getNumBeats() {
    int numBeats = this.numBeats;
    return numBeats;
  }

  /**
   * Returns a list of all the labels for the pitches.
   *
   * @return list of Strings representing pitch labels
   */
  // 6/12 created method as abstraction for showTrack
  public ArrayList<String> noteLabels() {

    ArrayList<String> arr = new ArrayList<String>();

    if (this.notes.size() == 0) {
      return arr;
    }

    // distance between highest and lowest pitch in the Pitch enum (can be negative)
    int pitchDist = this.noteRange().get(3) - this.noteRange().get(1);
    // distance between highest and lowest pitch, considering octaves
    int width = (this.noteRange().get(2) - this.noteRange().get(0)) * 12 + pitchDist + 1;

    int lowOct = this.noteRange().get(0);
    int lowPitchOrd = this.noteRange().get(1);
    // loop through each note starting with lowest note in environment
    for (int i = 0; i < width; i++) {

      // octave number of note in current loop
      int oct = lowOct + ((i + lowPitchOrd) / 12);

      // String of oct variable
      String octString = Integer.toString(oct + 1);

      // ordinal of the pitch of note in current loop
      int pitchOrdinal = (lowPitchOrd + i) % 12;

      // String of pitchOrdinal variable
      String pitchNames = Pitch.values()[pitchOrdinal].toString();

      // add pitch name with 5 spaces after to the String Builder
      arr.add(pitchNames + octString);
    }
    return arr;
  }

  /**
   * This method finds the current beat being played. If the song has not started,
   * the current beat is 0.
   *
   * @return integer representing the current beat being played
   */
  public int getCurrentBeat() {
    int current = this.currentBeat;
    return current;
  }

  /**
   * This method sets the current beat to the given beat.
   *
   * @param beat index of beat user wishes to change to
   */
  public void setCurrentBeat(int beat) {
    if (beat < 0 || beat > this.numBeats) {
      throw new IllegalArgumentException("Index out of Bounds");
    } else {
      this.currentBeat = beat;
    }
  }

  /**
   * This class is used to build an instance of a song.
   */
  // 6/14 added to model
  public static class TrackBuilder implements ITrackBuilder<Song> {

    Song song;

    public TrackBuilder() {
      this.song = new Song();
    }

    public Song build() {
      return this.song;
    }

    public ITrackBuilder setTempo(int tempo) {
      this.song.tempo = tempo;
      return this;
    }

    /**
     * Add a note to the song.
     * @param start The start time of the note, in beats
     * @param end The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
     *     piano)
     * @param volume The volume (in the range [0, 127])
     * @return ITrackBuilder with the new note included in it
     */
    public ITrackBuilder addNote(int start, int end, int instrument, int pitch, int volume) {
      this.song.addNote(new Note(Pitch.values()[pitch % 12], pitch / 12, end - start,
              instrument, volume),
          start);
      return this;
    }
  }
}