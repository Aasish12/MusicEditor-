//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cs3500.music.view.tests;

import cs3500.music.model.Note;
import cs3500.music.model.Song;
import cs3500.music.model.Note.Pitch;
import cs3500.music.view.console.TextView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the console view.
 */
public class TextViewTest {
  Song song2 = new Song();
  Note noteQ;
  Note noteH;
  Note noteW;
  TextView view;
  private final ByteArrayOutputStream outContent;

  /**
   * constructor.
   */
  public TextViewTest() {
    this.noteQ = new Note(Pitch.A_NAT, 0, 1, 0, 0);
    this.noteH = new Note(Pitch.C_NAT, 0, 2, 0 , 0);
    this.noteW = new Note(Pitch.G_SHARP, 0, 4,  0, 0);
    this.view = new TextView(this.song2);
    this.outContent = new ByteArrayOutputStream();
  }

  @Test
  public void showTrackEmpty() {
    System.setOut(new PrintStream(this.outContent));
    this.view.initialize();
    Assert.assertEquals("", this.outContent.toString());
  }

  @Test
  public void showTrackOneNoteOIndex() {
    this.song2.addNote(this.noteQ, 0);
    System.setOut(new PrintStream(this.outContent));
    this.view.initialize();
    Assert.assertEquals("  A1     \n1 X      ", this.outContent.toString());
  }

  @Test
  public void showTrackOneNoteNotOIndex() {
    this.song2.addNote(this.noteQ, 4);
    System.setOut(new PrintStream(this.outContent));
    this.view.initialize();
    Assert.assertEquals("  A1     \n1        \n2        \n3        \n4        \n5 X    "
        + "  ", this.outContent.toString());
  }

  @Test
  public void showTrackTwoNote() {
    this.song2.addNote(this.noteQ, 4);
    this.song2.addNote(this.noteH, 0);
    System.setOut(new PrintStream(this.outContent));
    this.view.initialize();
    Assert.assertEquals("  C1     C#1     D1     D#1     E1     F1     F#1     G1     G#1"
        + "     A1     \n1 X                                                                 "
        + "        \n2 |                                                                      "
        + "   \n3                                                                           \n4  "
        + "                                                                         \n5           "
        + "                                                         X      ",
        this.outContent.toString());
  }

  @Test
  public void showTrackThreeNote() {
    this.song2.addNote(this.noteQ, 4);
    this.song2.addNote(this.noteH, 0);
    this.song2.addNote(this.noteW, 1);
    this.song2.addNote(this.noteQ, 10);
    System.setOut(new PrintStream(this.outContent));
    this.view.initialize();
    Assert.assertEquals("   C1     C#1     D1     D#1     E1     F1     F#1     G1    "
        + " G#1     A1     \n 1 X                                                              "
        + "           \n 2 |                                                          X         "
        + "     \n 3                                                            |            "
        + "  \n 4                                                            |              \n 5 "
        + "                                                           |       X      \n 6        "
        + "                                                                   \n 7               "
        + "                                                            \n 8                     "
        + "                                                      \n 9                           "
        + "                                                \n10                                 "
        + "                                          \n11                                       "
        + "                             X      ", this.outContent.toString());
  }

  @Test
  public void showTrackTwoOctaves() {
    this.song2.addNote(this.noteQ, 4);
    this.song2.addNote(this.noteH, 0);
    this.song2.addNote(this.noteW, 1);
    this.song2.addNote(new Note(Pitch.C_SHARP, 1, 2,0, 0),
        2);
    System.setOut(new PrintStream(this.outContent));
    this.view.initialize();
    Assert.assertEquals("  C1     C#1     D1     D#1     E1     F1     F#1     G1   "
        + "  G#1     A1     A#1     B1     C2     C#2     \n1 X                               "
        + "                                                                        \n2 |        "
        + "                                                  X                                  "
        + "          \n3                                                            |           "
        + "                         X       \n4                                                "
        + "            |                                    |       \n5                         "
        + "                                   |       X                                    ",
        this.outContent.toString());
  }
}