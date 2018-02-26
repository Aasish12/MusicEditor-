package cs3500.music.model;

/**
 * This class represents a single note, with octave number, pitch, and length of time in beats.
 */
public class Note {

  // This enum represents a single pitch, with 12 possible pitches
  // structure and methods of Pitch enum from ...
  // https://stackoverflow.com/questions/6667243/using-enum-values-as-string-literals
  public enum Pitch {

    C_NAT("C"), C_SHARP("C#"), D_NAT("D"), D_SHARP("D#"), E_NAT("E"), F_NAT("F"), F_SHARP("F#"),
    G_NAT("G"), G_SHARP("G#"), A_NAT("A"), A_SHARP("A#"), B_NAT("B");

    // the String format of the Pitch
    private final String name;

    // constructor (Pitch)
    Pitch(String name) {
      this.name = name;
    }

    /**
     * Returns the Pitch identifier of the given String identifier.
     *
     * @param noteName String representing the Pitch
     * @return Pitch identifier for the given String
     */
    public static Pitch reverseLookup(String noteName) {

      for (Pitch p : Pitch.values()) {
        if (p.name.equalsIgnoreCase(noteName)) {
          return p;
        }
      }
      return null;
    }

    /**
     * This method gives the String format of this Pitch.
     *
     * @return String format of this Pitch.
     */
    public String toString() {
      return this.name;
    }
  }

  // a Pitch enum
  protected Pitch pitch;
  // number of octaves note is from the lowest note
  protected int octave;
  // length of the note in beats
  protected int beats;

  // 6/15 added instrument and volume

  // instrument of the note
  protected int instrument;
  // volume of note
  protected int volume;

  /**
   * Constructor.
   *
   * @param pitch pitch of the note
   * @param octave octave index of the note
   * @param beats length of the notes in beats
   */
  public Note(Pitch pitch, int octave, int beats, int instrument, int volume) {
    if (pitch == null || octave < 0 || octave > 9 || beats < 0 || instrument < 0 || volume < 0 ) {
      throw new IllegalArgumentException("Invalid Note");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.beats = beats;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * This method gives the String formatted version of this Note.
   *
   * @return String formatted version of this Note
   */
  public String toString() {
    return String.format("%s%d", this.pitch.toString(), this.octave + 1);
  }

  /**
   * Returns true if the Object is equal to this.
   *
   * @param o the Object to be compared
   * @return boolean representing if the Object equals this
   */
  public boolean equalsName(Object o) {
    if (!(o instanceof Note)) {
      return false;
    }
    Note n = (Note) o;
    return this.pitch.equals(((Note) o).pitch)
        && this.octave == octave && this.beats == ((Note) o).beats;
  }

  /**
   * This methods gets the Pitch of the Note.
   *
   * @return String Pitch of the Note.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * This method finds the octave of the note.
   *
   * @return integer of the index of octave of the note.
   */
  public int getOctave() {
    return this.octave;
  }

  public int getBeats() {
    return this.beats;
  }

  public int getInstrument() {
    return this.instrument;
  }

  public int getVolume() {
    return this.volume;
  }
}