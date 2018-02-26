package cs3500.music.view.console;

import cs3500.music.model.Note;
import cs3500.music.model.Note.Pitch;
import cs3500.music.model.Song;
import cs3500.music.view.IView;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Prints out the textual view of the current state of the model.
 */
public class TextView implements IView {

  protected Song song;

  public TextView(Song song) {
    this.song = song;
  }

  /**
   * This method displays the current MIDI environment as a String Builder.
   */
  // 6/12 abstracted method using noteLabels
  public void initialize() {

    Appendable app = new StringBuffer();

    // initialize return string builder
    StringBuilder strb = new StringBuilder();

    if (song.getNotes().size() == 0) {
      try {
        app.append(strb.toString());
        System.out.print(app);
        return;
      } catch (IOException e) {
        // do nothing
      }
    }

    // SET THE DIMENSIONS OF THE STRING BUILDER //

    // distance between highest and lowest pitch in the Pitch enum (can be negative)
    int pitchDist = song.noteRange().get(3) - song.noteRange().get(1);
    // distance between highest and lowest pitch, considering octaves
    int width = (song.noteRange().get(2) - song.noteRange().get(0)) * 12 + pitchDist + 1;
    // height of screen
    int height = song.getNumBeats();

    // CREATE THE TOP LINE OF PITCH LABELS //
    strb.append(" ");

    strb.append(String.format("%" + String.valueOf(song.getNumBeats()).length() + "s", " "));

    // intialize pageWidth counter
    int pageWidth = String.valueOf(song.getNumBeats()).length();

    ArrayList<String> arr = song.noteLabels();
    // loop through each note starting with lowest note in environment
    for (String s : arr) {

      // add pitch name with 5 spaces after to the String Builder
      strb.append(s);
      strb.append("     ");

      // add to counter
      pageWidth += s.length() + 5;
    }

    // add a new line to finish the pitch labels
    strb.append("\n");
    strb.append(String.format("%" + String.valueOf(song.getNumBeats()).length() + "d", 1));

    // CREATE THE CANVAS SPACE //

    // loop through each space on canvas
    for (int i = 1; i < pageWidth * height; i++) {

      // if reached the end of line
      if (i % pageWidth == 0) {
        // next line number

        String numLabel = String.format("%" + String.valueOf(song.getNumBeats()).length()
            + "d", i / pageWidth + 1);
        // previous line number
        switch (Integer.toString(song.getNumBeats()).length()) {
          case 1:
            strb.append(" ");
            break;
          case 2:
            strb.append("");
            break;
          default:
            strb.delete(strb.length() + 1 - Integer.toString(song.getNumBeats()).length(),
                strb.length() - 1);
        }
        // new line
        strb.append("\n");
        // append next line number
        strb.append(numLabel);
      } else {
        // not end of line, add another space
        strb.append(" ");
      }
    }

    switch (Integer.toString(song.getNumBeats()).length()) {
      case 1:
        strb.append(" ");
        break;
      case 2:
        strb.append("");
        break;
      default:
        strb.delete(strb.length() + 1 - Integer.toString(song.getNumBeats()).length(),
            strb.length() - 1);
    }

    // PLACE NOTES //

    // find how far over to put the note //
    int lowOct = song.noteRange().get(0);
    int lowPitchOrd = song.noteRange().get(1);

    for (Map.Entry<String, Note> entry : song.getNotes().entrySet()) {
      // note must be at least two spaces into its line
      int count = Integer.toString(song.getNumBeats()).length() + 2;
      // loop through notes
      for (int i = 0; i < width; i++) {

        // octave number of note in current loop
        int oct = lowOct + ((i + lowPitchOrd) / 12);

        // String of oct variable
        String octString = Integer.toString(oct + 1);

        // ordinal of the pitch of note in current loop
        int pitchOrdinal = (lowPitchOrd + i) % 12;

        // String of pitchOrdinal variable
        String pitchNames = Pitch.values()[pitchOrdinal].toString();

        // String of pitch of note to be placed
        String thisPitchName = entry.getValue().getPitch().toString();
        // octave of note to be placed
        int thisOct = entry.getValue().getOctave();

        // current note has same octave and pitch as note to be placed
        if (thisPitchName.equals(pitchNames) && thisOct == oct) {

          // stop adding to counter
          break;
        }

        // otherwise, add length of spaces processed
        count += pitchNames.length() + octString.length() + 5;
      }

      // place note //

      // how many down to put the note
      int numLines = song.getBeatNum(entry.getKey());
      // how many spaces over to put the note
      int charPos = (pageWidth + 1) * (numLines + 1) + numLines + count;

      // if note is not the first beat (duration)
      if (entry.getKey().contains("-")) {
        // place '|' symbol
        strb.setCharAt(charPos, '|');
      } else {
        // place 'X' symbol
        strb.setCharAt(charPos, 'X');
      }
    }

    // the canvas is complete!

    try {
      app.append(strb.toString());
    } catch (IOException e) {
      // do nothing
    }

    System.out.print(app);
  }

  public Dimension getPreferredSize() {
    throw new IllegalArgumentException("Can't getPreferredSize of a console view");
  }

  // 6/19 added
  public void reDraw() {
    // do nothing
  }

  // 6/19 added
  public void resetFocus() {
    // do nothing
  }

  // 6/19 added
  public void addKeyListener(KeyListener kl) {
    // do nothing
  }

  // 6/19 added
  public void addMouseListener(MouseListener ml) {
    // do nothing
  }

  // 6/19 added
  public void addNote(int x, int y) {
    // do nothing
  }

  // 6/19 added
  public void keyP() {
    // do nothing
  }

  // 6/19 added
  public void keyRight() {
    // do nothing
  }

  // 6/19 added
  public void keyLeft() {
    // do nothing
  }

  // 6/19 added
  public void keyHome() {
    // do nothing
  }

  // 6/19 added
  public void keyEnd() {
    // do nothing
  }
}