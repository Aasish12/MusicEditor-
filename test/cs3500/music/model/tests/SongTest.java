package cs3500.music.model.tests;

import static org.junit.Assert.assertEquals;

import cs3500.music.model.Note;
import cs3500.music.model.Note.Pitch;
import cs3500.music.model.Song;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Test class for all of the public methods of the Song class.
 */
public class SongTest {

  Song song1 = new Song();
  Song song2 = new Song();
  ArrayList<Song> songs = new ArrayList<Song>(Arrays.asList(song1, song2));
  Note noteTooLong = new Note(Pitch.A_NAT, 4, 121,0,0);
  Note noteQ = new Note(Pitch.A_NAT, 0, 1,0,0);
  Note noteH = new Note(Pitch.C_NAT, 0, 2,0,0);
  Note noteW = new Note(Pitch.G_SHARP, 0, 4,0,0);
  Note noteLong = new Note(Pitch.F_SHARP, 4, 100,0,0);

  @Test
  public void trackLength() {
    assertEquals(0,
        song1.getNotes().size());
  }

  @Test
  public void validNoteChangeBeatIndexNegative() {
    assertEquals("Beat Out of Bounds",
        song1.validNoteChange(noteW, -1));
  }

  @Test
  public void validNoteChangeNoteAlreadyPresent() {
    song1.addNote(noteW, 0);
    assertEquals("Note Already Present",
        song1.validNoteChange(noteW, 0));
  }

  @Test
  public void validNoteChangeValid() {
    assertEquals("Valid Change",
        song1.validNoteChange(noteW, 0));
  }

  @Test
  public void addNoteQuarterNote() {
    song1.addNote(noteQ, 0);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test
  public void addNoteHalfNote() {
    song1.addNote(noteH, 0);
    assertEquals(2,
        song1.getNotes().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNotePresent() {
    song1.addNote(noteH, 0);
    song1.addNote(noteH, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteNoteInvalidNegativeIndex() {
    song1.addNote(noteH, -1);
  }

  @Test
  public void addNoteWholeNote() {
    song1.addNote(noteLong, 0);
    assertEquals(100,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteQuarterNote() {
    song1.addNote(noteQ, 0);
    assertEquals(1,
        song1.getNotes().size());
    song1.deleteNote(noteQ, 0);
    assertEquals(0,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteHalfNote() {
    song1.addNote(noteH, 0);
    assertEquals(2,
        song1.getNotes().size());
    song1.deleteNote(noteH, 0);
    assertEquals(0,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteHalfNoteQuarterNote() {
    song1.addNote(noteQ, 0);
    song1.addNote(noteH, 1);
    assertEquals(3,
        song1.getNotes().size());
    song1.deleteNote(noteH, 1);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteHalfNoteQuarterNote2() {
    song1.addNote(noteQ, 0);
    song1.addNote(noteH, 1);
    assertEquals(3,
        song1.getNotes().size());
    song1.deleteNote(noteH, 2);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteQuarterNoteHalfNote() {
    song1.addNote(noteH, 0);
    song1.addNote(noteQ, 2);
    assertEquals(3,
        song1.getNotes().size());
    song1.deleteNote(noteH, 0);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteQuarterNoteHalfNote2() {
    song1.addNote(noteH, 0);
    song1.addNote(noteQ, 2);
    assertEquals(3,
        song1.getNotes().size());
    song1.deleteNote(noteH, 1);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteWholeNoteQuarterNote() {
    song1.addNote(noteW, 0);
    song1.addNote(noteQ, 4);
    assertEquals(5,
        song1.getNotes().size());
    song1.deleteNote(noteW, 0);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteWholeNoteQuarterNote2() {
    song1.addNote(noteW, 0);
    song1.addNote(noteQ, 4);
    assertEquals(5,
        song1.getNotes().size());
    song1.deleteNote(noteW, 1);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test
  public void deleteNoteWholeNoteQuarterNote3() {
    song1.addNote(noteW, 0);
    song1.addNote(noteQ, 4);
    assertEquals(5,
        song1.getNotes().size());
    song1.deleteNote(noteW, 3);
    assertEquals(1,
        song1.getNotes().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteNoteNoNote() {
    song1.deleteNote(noteW, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteNoNote() {
    song1.editNote(noteW, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteDurationInvalid() {
    song1.addNote(noteW, 0);
    song1.editNote(noteW, 1, 6);
  }

  @Test
  public void editNoteValid() {
    song1.addNote(noteW, 0);
    song1.editNote(noteW, 0, 1);
    assertEquals("G#",
        song1.getNotes().get("1 0 G#").getPitch().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void editNoteInvalid() {
    song1.addNote(noteW, 0);
    song1.editNote(noteW, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setCurrentBeat300() {
    song1.setCurrentBeat(300);
  }

  @Test
  public void noteLabels() {
    song1.addNote(noteQ,0);
    song1.addNote(noteH,0);
    assertEquals(10,
        song1.noteLabels().size());
  }

  @Test
  public void noteLabelsNoLabels() {
    assertEquals(0,
        song1.noteLabels().size());
  }

  @Test
  public void noteLabelsOneLabel() {
    song1.addNote(noteQ,0);
    assertEquals(1,
        song1.noteLabels().size());
  }
}