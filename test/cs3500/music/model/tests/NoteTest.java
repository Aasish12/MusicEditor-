package cs3500.music.model.tests;

import static org.junit.Assert.assertEquals;

import cs3500.music.model.Note;
import cs3500.music.model.Note.Pitch;
import org.junit.Test;

/**
 * Test class for all of the public methods of the Note class.
 */
public class NoteTest {

  Note a = new Note(Pitch.A_NAT, 0, 1,0,0);
  Note aSharp = new Note(Pitch.A_SHARP, 1, 1,0,0);

  @Test
  public void reverseLookupA() {
    assertEquals(Pitch.A_NAT,
        Pitch.A_NAT.reverseLookup("A"));
  }

  @Test
  public void reverseLookupASharp() {
    assertEquals(Pitch.A_SHARP,
        Pitch.A_SHARP.reverseLookup("A#"));
  }

  @Test
  public void toStringPitchA() {
    assertEquals("A",
        Pitch.A_NAT.toString());
  }

  @Test
  public void toStringPitchASharp() {
    assertEquals("A#",
        Pitch.A_SHARP.toString());
  }

  @Test
  public void toStringNoteA() {
    assertEquals("A1",
        a.toString());
  }

  @Test
  public void toStringNoteASharp() {
    assertEquals("A#2",
        aSharp.toString());
  }

  @Test
  public void equalsName() {
    assertEquals(true,
        a.equalsName(new Note(Pitch.A_NAT, 0, 1,0,0)));
  }

  @Test
  public void equalsNameNotEquals() {
    assertEquals(false,
        aSharp.equalsName(new Note(Pitch.A_NAT, 0, 1,0,0)));
  }

  @Test
  public void getPitchA() {
    assertEquals(Pitch.A_NAT,
        a.getPitch());
  }

  @Test
  public void getPitchASharp() {
    assertEquals(Pitch.A_SHARP,
        aSharp.getPitch());
  }

  @Test
  public void getOctaveA() {
    assertEquals(0,
        a.getOctave());
  }

  @Test
  public void getOctaveASharp() {
    assertEquals(1,
        aSharp.getOctave());
  }
}